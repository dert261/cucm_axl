package ru.obelisk.cucmaxl.ftp;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.idempotent.FileIdempotentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import ru.obelisk.database.models.entity.CallDetailRecord;
import ru.obelisk.database.models.entity.CallManagementRecord;
import ru.obelisk.database.models.entity.Collector;
import ru.obelisk.database.models.entity.CollectorFtpConfig;
import ru.obelisk.database.models.entity.enums.CollectorResourceSourceType;
import ru.obelisk.database.models.service.CallDetailRecordService;
import ru.obelisk.database.models.service.CallManagementRecordService;
import ru.obelisk.database.models.service.CollectorService;
import ru.obelisk.cucmaxl.ftp.converter.CsvToCdrConverter;
import ru.obelisk.cucmaxl.ftp.converter.CsvToCmrConverter;
import ru.obelisk.cucmaxl.ftp.csv.CsvCucmCdr;
import ru.obelisk.cucmaxl.ftp.csv.CsvCucmCmr;
import ru.obelisk.cucmaxl.utils.ObeliskStringUtils;

@Component
@Log4j2 
public class FtpCamel {
	
	@Autowired private CallDetailRecordService callDetailRecordService;
	@Autowired private CallManagementRecordService callManagementRecordService;
	
	@Autowired private CsvToCdrConverter cdrConverter;
	@Autowired private CsvToCmrConverter cmrConverter;
	
	@Autowired private CollectorService collectorService;
			
	@Autowired private CamelContext camelContext;
	
	@PostConstruct
	public void init(){
		
		for (Collector collector : collectorService.findAllWithRelations()){
			if(collector.getCollectorFtpConfig()!=null){
				try {
					camelContext.addRoutes(createFtpRoute(collector));
				//	camelContext.start();
				} catch (Exception e) {
					log.warn(ObeliskStringUtils.getTraceToLog(e));
				}
			}
		}
	}
		
	@PreDestroy
	public void destroy(){
		if(camelContext!=null){
			try {
				camelContext.stop();
			} catch (Exception e) {
				log.warn(ObeliskStringUtils.getTraceToLog(e));
			}
		}	
	}
	
	private RouteBuilder createFtpRoute(Collector collector){
		CollectorFtpConfig ftpConf = collector.getCollectorFtpConfig();
		StringBuilder routeBuilder = new StringBuilder();
		routeBuilder.append( "ftp://");
		routeBuilder.append( (ftpConf.getUsername()!=null && ftpConf.getUsername().length()>0) ? ftpConf.getUsername()+"@" : "" );
		routeBuilder.append( ftpConf.getHost() );
		routeBuilder.append( (ftpConf.getPort()>0 && ftpConf.getPort()!=21) ? ":"+ftpConf.getPort() : "" );
		routeBuilder.append( (ftpConf.getDirectory()!=null && ftpConf.getDirectory().length()>0) ? ftpConf.getDirectory() : "/" );
		routeBuilder.append( "?noop=true&stepwise=false&binary=true" );
		routeBuilder.append( ((ftpConf.getUsername()!=null && ftpConf.getUsername().length()>0) && (ftpConf.getPassword()!=null && ftpConf.getPassword().length()>0)) ? "&password="+ftpConf.getPassword() : "" );
		routeBuilder.append( ftpConf.getConsumerDelay()>0 ? "&consumer.delay="+ftpConf.getConsumerDelay() : "" );
		routeBuilder.append( "&recursive="+ftpConf.isRecursive() );
		routeBuilder.append( (ftpConf.getIncludePattern()!=null && ftpConf.getIncludePattern().length()>0) ? "&include="+ftpConf.getIncludePattern() : "" );
		routeBuilder.append( (ftpConf.getExcludePattern()!=null && ftpConf.getExcludePattern().length()>0) ? "&exclude="+ftpConf.getExcludePattern() : "" );
						
		final FtpProcessor<?, ?> ftpProcessorCdr = new FtpProcessor<CsvCucmCdr, CallDetailRecord>(CsvCucmCdr.class, "ftpdata/cdr", true, callDetailRecordService, cdrConverter);
		final FtpProcessor<?, ?> ftpProcessorCmr = new FtpProcessor<CsvCucmCmr, CallManagementRecord>(CsvCucmCmr.class, "ftpdata/cmr", true, callManagementRecordService, cmrConverter);
		
		File cdrIdempotentFile = new File("ftpdata", "repo_cdr.dat");
		File cmrIdempotentFile = new File("ftpdata", "repo_cmr.dat");
		
		return new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from(routeBuilder.toString())
				.routeId(String.valueOf(ftpConf.getId().hashCode()))
				.idempotentConsumer(header("CamelFileName"), FileIdempotentRepository.fileIdempotentRepository( collector.getResourceSourceType()==CollectorResourceSourceType.CDR ? cdrIdempotentFile : cmrIdempotentFile ))
				.threads(ftpConf.getThreadpoolSize(), ftpConf.getThreadpoolMaxSize())
				.process(collector.getResourceSourceType()==CollectorResourceSourceType.CDR ? ftpProcessorCdr : ftpProcessorCmr);
			}
		};
	}
}

package ru.obelisk.cucmaxl.ftp;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ServiceStatus;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.idempotent.jdbc.JdbcMessageIdRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.extern.log4j.Log4j2;
import ru.obelisk.cucmaxl.ftp.csv.CsvCucmCdr;
import ru.obelisk.cucmaxl.ftp.csv.CsvCucmCmr;
import ru.obelisk.cucmaxl.ftp.database.CdrJDBCService;
import ru.obelisk.cucmaxl.ftp.database.CmrJDBCService;
import ru.obelisk.cucmaxl.statistics.EventType;
import ru.obelisk.cucmaxl.statistics.ModuleEvent;
import ru.obelisk.cucmaxl.statistics.ModuleHeartbeat;
import ru.obelisk.cucmaxl.statistics.metrics.FtpMetrics;
import ru.obelisk.cucmaxl.utils.ObeliskStringUtils;
import ru.obelisk.database.models.entity.Collector;
import ru.obelisk.database.models.entity.CollectorFtpConfig;
import ru.obelisk.database.models.entity.enums.CollectorType;
import ru.obelisk.database.models.entity.enums.CurrentRunStatus;
import ru.obelisk.database.models.entity.enums.LaunchModeType;
import ru.obelisk.database.models.service.CollectorService;

@Component
@Log4j2 
public class FtpCamelRun {
	
	@Autowired private CamelContext camelContext;
	@Autowired private CollectorService collectorService;
		
	@Autowired private CdrJDBCService cdrService;
	@Autowired private CmrJDBCService cmrService;
	
	@Autowired private FtpMetrics statisticModule;
	@Autowired private ModuleHeartbeat heartbeatModule;
	
	@Autowired private DataSource dataSource;
	@Autowired private TransactionTemplate transactionTemplate;
	
	private final String COLLECTOR_PREFIX_NAME = "collector_";
	
	@PostConstruct
	private void init(){
		collectorService.findAll().stream().filter((n)->n.getType()==CollectorType.FTP).forEach((n)->{
			n.setCurrentRunStatus(CurrentRunStatus.STOP);
			collectorService.edit(n);
			
		});
	  	for (Collector collector : collectorService.findAllWithRelations()){
	  		if(collector.getType()==CollectorType.FTP && collector.getLaunchModeType()==LaunchModeType.AUTORUN){
				if(collector.getCollectorFtpConfig()!=null){
					try {
						runCollectorRoute(collector);
					} catch (Exception e) {
						errorToLog(e);
					}
				}
			}
		}
	}
		
	@PreDestroy
	private void destroy(){
		if(camelContext!=null){
			try {
				camelContext.stop();
			} catch (Exception e) {
				errorToLog(e);
			}
		}	
	}
	
	public boolean runCollectorRoute(Collector collector){

		if(camelContext==null) return false;
		if(camelContext.getRoute(COLLECTOR_PREFIX_NAME+collector.getCollectorFtpConfig().getId())==null){
			try {
				camelContext.addRoutes(createFtpRoute(collector));
			} catch (Exception e) { errorToLog(e); }
		}
		
		ServiceStatus camelStatus = camelContext.getStatus();
		if(!camelStatus.isStarting() && !camelStatus.isStarted()){
			try {
				camelContext.start();
			} catch (Exception e) { errorToLog(e); }
		}
			
		try {
			ServiceStatus status = camelContext.getRouteStatus(COLLECTOR_PREFIX_NAME+collector.getCollectorFtpConfig().getId());
			log.info("Get route status for collector: {}",COLLECTOR_PREFIX_NAME+collector.getCollectorFtpConfig().getId());
			if(status==null || (!status.isStarting() && !status.isStarted())){
				log.info("Try to start route");
				camelContext.startRoute(COLLECTOR_PREFIX_NAME+collector.getCollectorFtpConfig().getId());
				log.info("After try to start route");
			}	
		} catch (Exception e) { errorToLog(e); }
		
		ServiceStatus status = camelContext.getRouteStatus(COLLECTOR_PREFIX_NAME+collector.getCollectorFtpConfig().getId());
		if(status!=null){
			switch (status) {
				case Started: 		collector.setCurrentRunStatus(CurrentRunStatus.RUN);				break;
				case Starting: 		collector.setCurrentRunStatus(CurrentRunStatus.RUNNING);			break;
				case Stopped: 		collector.setCurrentRunStatus(CurrentRunStatus.STOP);				break;
				case Stopping: 		collector.setCurrentRunStatus(CurrentRunStatus.STOPING);			break;
				case Suspended: 	collector.setCurrentRunStatus(CurrentRunStatus.STOP);				break;
				case Suspending:	collector.setCurrentRunStatus(CurrentRunStatus.STOPING);			break;
				default: 			collector.setCurrentRunStatus(CurrentRunStatus.STOP);				break;
			}
		} else {
			collector.setCurrentRunStatus(CurrentRunStatus.STOP);
		}	
	
		collectorService.edit(collector);
		return (status!=null && (status.isStarting() || status.isStarted()));
	}
	
	public boolean stopCollectorRoute(Collector collector){
		if(camelContext==null || camelContext.getRoute(COLLECTOR_PREFIX_NAME+collector.getCollectorFtpConfig().getId())==null){
			collector.setCurrentRunStatus(CurrentRunStatus.STOP);
			collectorService.edit(collector);
			return true; 
		};
		collector.setCurrentRunStatus(CurrentRunStatus.STOPING);
		collectorService.edit(collector);
		try {
			ServiceStatus status = camelContext.getRouteStatus(COLLECTOR_PREFIX_NAME+collector.getCollectorFtpConfig().getId());
			if(status.isStarting() || status.isStarted())
				camelContext.stopRoute(COLLECTOR_PREFIX_NAME+collector.getCollectorFtpConfig().getId());
				camelContext.removeRoute(COLLECTOR_PREFIX_NAME+collector.getCollectorFtpConfig().getId());
			
		} catch (Exception e) { errorToLog(e); }
			
		collector.setCurrentRunStatus(CurrentRunStatus.STOP);
		collectorService.edit(collector);
		return true;
	}
	
	private void errorToLog(Exception e){
		log.warn(ObeliskStringUtils.getTraceToLog(e));
		ModuleEvent event = new ModuleEvent();
		event.setEventType(EventType.WARNING);
		event.setEventMessage(ObeliskStringUtils.getTraceToLog(e));
		heartbeatModule.getEvents().add(event);
	}
	
	private RouteBuilder createFtpRoute(Collector collector){
		CollectorFtpConfig ftpConf = collector.getCollectorFtpConfig();
		
		heartbeatModule.setEventCount(5);
				
		StringBuilder routeBuilder = new StringBuilder();
		routeBuilder.append( "ftp://");
		routeBuilder.append( (ftpConf.getUsername()!=null && ftpConf.getUsername().length()>0) ? ftpConf.getUsername()+"@" : "" );
		routeBuilder.append( ftpConf.getHost() );
		routeBuilder.append( (ftpConf.getPort()>0 && ftpConf.getPort()!=21) ? ":"+ftpConf.getPort() : "" );
		routeBuilder.append( (ftpConf.getDirectory()!=null && ftpConf.getDirectory().length()>0) ? ftpConf.getDirectory() : "/" );
		routeBuilder.append( "?noop=false&ftpClient.dataTimeout=30000&stepwise=false&binary=true&disconnect=true&passiveMode=true" );
		routeBuilder.append( ftpConf.isDeleteFile() ? "&delete=true" : (!ftpConf.getDirectoryToMove().isEmpty() ? "&move="+ftpConf.getDirectoryToMove() : "") );
		routeBuilder.append( ((ftpConf.getUsername()!=null && ftpConf.getUsername().length()>0) && (ftpConf.getPassword()!=null && ftpConf.getPassword().length()>0)) ? "&password="+ftpConf.getPassword() : "" );
		routeBuilder.append( ftpConf.getConsumerDelay()>0 ? "&consumer.delay="+ftpConf.getConsumerDelay() : "" );
		routeBuilder.append( "&recursive="+ftpConf.isRecursive() );
		routeBuilder.append( (ftpConf.getIncludePattern()!=null && ftpConf.getIncludePattern().length()>0) ? "&include="+ftpConf.getIncludePattern() : "" );
		routeBuilder.append( (ftpConf.getExcludePattern()!=null && ftpConf.getExcludePattern().length()>0) ? "&exclude="+ftpConf.getExcludePattern() : "" );
		
		final FtpProcessor<?> ftpProcessorCdr = new FtpProcessor<CsvCucmCdr>(CsvCucmCdr.class, cdrService, "ftpdata/cdr", true, statisticModule);
		final FtpProcessor<?> ftpProcessorCmr = new FtpProcessor<CsvCucmCmr>(CsvCucmCmr.class, cmrService, "ftpdata/cmr", true, statisticModule);
		
		FtpProcessor<?> processor = null;
		switch(collector.getResourceSourceType()){
			case AVAYA_CDR: 
				break;
			case CISCO_CUCM_CDR: processor = ftpProcessorCdr;
				break;
			case CISCO_CUCM_CMR: processor = ftpProcessorCmr;
				break;
			default:
				break;
		}
		
		final FtpProcessor<?> finalProcessor = processor;
		
		return new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				onException(Exception.class)
				.handled(true)
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						Exception exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
						ModuleEvent event = new ModuleEvent();
						event.setEventType(EventType.WARNING);
						event.setEventMessage(exception.toString());
						heartbeatModule.addEvent(event);
						log.warn(ObeliskStringUtils.getTraceToLog(exception));
					}
				});		
								
				from(routeBuilder.toString())
				.routeId(COLLECTOR_PREFIX_NAME+String.valueOf(ftpConf.getId()))
				.idempotentConsumer(header("CamelFileName"), new JdbcMessageIdRepository(dataSource, transactionTemplate, "collector_"+String.valueOf(ftpConf.getId())))
				.process(finalProcessor);
			}
		};
	}
	
}

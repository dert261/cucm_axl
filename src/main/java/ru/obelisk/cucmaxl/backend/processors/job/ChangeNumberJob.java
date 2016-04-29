package ru.obelisk.cucmaxl.backend.processors.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import lombok.extern.log4j.Log4j2;
import ru.obelisk.cucmaxl.cucm.service.CucmWithDBService;
import ru.obelisk.database.models.entity.Job;
import ru.obelisk.database.models.entity.JobResult;
import ru.obelisk.database.models.entity.enums.JobStatus;
import ru.obelisk.database.models.service.JobResultService;
import ru.obelisk.database.models.service.JobService;
import ru.obelisk.cucmaxl.utils.ObeliskStringUtils;
import ru.obelisk.cucmaxl.web.controllers.utils.CsvChangeNumber;

@Component
@Log4j2
public class ChangeNumberJob {
	@Autowired private JobService jobService;
	@Autowired private JobResultService jobResultService;
	@Autowired private CucmWithDBService cucmWithDBService;
	@Autowired private JobRepo jobRepo;
		
	@Value("${download.operations.logs.directory}") private String LOGSDIR="operations/logs";
	
	@Async
	public void changenumberJob(Job job){
		jobRepo.addJobInRepo(job);
		JobStatus resultingJobStatus = JobStatus.COMPLETED;
		
		JobResult jobResult = new JobResult();
		jobResult.setLaunchDate(LocalDateTime.now());
		jobResult.setJob(job);
		
		//String fileName=job.getChangeNumberDetail().getDatasource().getFilename()+"_log.txt";
		String fileName=DigestUtils.md5Hex(job.getChangeNumberDetail().getDatasource().getFilename()+(LocalDateTime.now()))+"_log.txt";
		try( PrintWriter out = new PrintWriter(LOGSDIR+"/"+fileName)  ){
			jobResult.setLogfilename(fileName);
			jobResult.setLogfilepath(LOGSDIR);
        	
			out.println("***** RESULT OF THE OPERATION: \"CHANGE NUMBER\" *****");
        	
        	HeaderColumnNameMappingStrategy<CsvChangeNumber> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(CsvChangeNumber.class);
            CsvToBean<CsvChangeNumber> csvToBean = new CsvToBean<CsvChangeNumber>();
            List<CsvChangeNumber> beanList = new ArrayList<CsvChangeNumber>();
            
            File file = new File(job.getChangeNumberDetail().getDatasource().getFilepath()+"/"+job.getChangeNumberDetail().getDatasource().getFilename());
            boolean error = false;
            try (FileReader reader = new FileReader(file)) {
            	
    			beanList = csvToBean.parse(strategy, new CSVReader(reader));
    		} catch (FileNotFoundException e) {
    			out.println("Operation failed. Failed read csv file: "+e.getMessage());
    			error = true;
    			log.trace(ObeliskStringUtils.getTraceToLog(e));
    			resultingJobStatus = JobStatus.FAILED;
    		} catch (IOException e1) {
    			log.trace(ObeliskStringUtils.getTraceToLog(e1));
			}
        	
            if(!error){
	            jobResult.setNumberRecordsTotal(beanList.size());
	            jobResultService.add(jobResult);
	            
	            Iterator<CsvChangeNumber> chNumIter = beanList.iterator();
	    		while(chNumIter.hasNext() && !job.isChancel()){
	    			log.info(job);
	    			CsvChangeNumber number = chNumIter.next();
		    		number = cucmWithDBService.changeDirectoryNumber(job.getChangeNumberDetail().getDestination(), number, job.getChangeNumberDetail().getPartition());
		    		out.println(number);
		    		if(number.getState()==-1){
		    			jobResult.setNumberRecordsFailed(jobResult.getNumberRecordsFailed()+1);
		    		}
		    		jobResult.setNumberRecordsProcessed(jobResult.getNumberRecordsProcessed()+1);
		    		
		    		jobResultService.edit(jobResult);
		    		out.flush();
	    		}
	    		
	    		if(job.isChancel()){
	    			out.println("*****JOB IS CHANCEL!!!*****");
	    			resultingJobStatus = JobStatus.STOPPED;
	    			job.setChancel(false);
	    		} else {
	    			out.println("*****JOB IS COMPLETED!!!*****");
	    		}
	    	}
        } catch (FileNotFoundException e) {
			log.trace(ObeliskStringUtils.getTraceToLog(e));
			resultingJobStatus = JobStatus.FAILED;
		}
		job.setStatus(resultingJobStatus);
		jobService.edit(job);
		jobRepo.removeJobFromRepo(job);
	}
	
}

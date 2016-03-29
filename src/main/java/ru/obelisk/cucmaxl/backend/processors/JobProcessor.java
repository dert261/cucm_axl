package ru.obelisk.cucmaxl.backend.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import ru.obelisk.cucmaxl.database.models.entity.Job;
import ru.obelisk.cucmaxl.database.models.entity.enums.JobStatus;
import ru.obelisk.cucmaxl.database.models.service.JobService;
import ru.obelisk.cucmaxl.web.databinding.AjaxOperationResult;

@Component
@Log4j2
public class JobProcessor {
	@Autowired private ChangeNumberJob changeNumberJob;
	@Autowired private JobRepo jobRepo;
	@Autowired private JobService jobsService;
		
	public AjaxOperationResult processJob(Job job){
		AjaxOperationResult result = new AjaxOperationResult();
		if(job==null){ 
			result.setStatus(-1);
			result.setMessage("Job must be not null!!!");
			return result;
		}
		Job runingJob = jobRepo.getJobFromRepo(job.getId());
		if(runingJob==null){
			job = jobsService.findById(job.getId());
			job.setStatus(JobStatus.RUN);
			jobsService.edit(job);
			
			switch(job.getJobFunction()){
				case CHANGENUMBER: changeNumberJob.changenumberJob(job); break;
				default: break;
			}
			result.setStatus(0);
			result.setMessage("Job put to processing queue");
		} else {
			result.setStatus(-1);
			result.setMessage("Job now is running");
		}	
		return result;
	}
	
	public AjaxOperationResult stopJob(Job job){
		log.info("Try to stop process");
		AjaxOperationResult result = new AjaxOperationResult();
		if(job==null){ 
			result.setStatus(-1);
			result.setMessage("Job must be not null!!!");
			return result;
		}
		Job runingJob = jobRepo.getJobFromRepo(job.getId());
		if(runingJob!=null){
			runingJob.setChancel(true);
			result.setStatus(0);
			result.setMessage("Job set chancel flag");
			while(runingJob.getStatus()==JobStatus.RUN){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			result.setStatus(-1);
			result.setMessage("Job now is stopped");
		}	
				
		return result;
	}
	
	
}

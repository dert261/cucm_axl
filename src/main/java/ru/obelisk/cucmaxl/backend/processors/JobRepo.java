package ru.obelisk.cucmaxl.backend.processors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import ru.obelisk.database.models.entity.Job;

@Component
public class JobRepo {
	private Map<Integer, Job> runJobs = new HashMap<Integer, Job>(0);
	
	public void addJobInRepo(Job job){
		 synchronized(runJobs){
			 if(!runJobs.containsKey(job.getId())){
				 runJobs.put(job.getId(), job);
			 }
		 }
	}
	
	public void removeJobFromRepo(Job job){
		synchronized(runJobs){
			 if(runJobs.containsKey(job.getId())){
				 runJobs.remove(job.getId());
			 }
		 }
	}
	
	public Job getJobFromRepo(int id){
		synchronized(runJobs){
			return runJobs.get(id);
		}
	}
}

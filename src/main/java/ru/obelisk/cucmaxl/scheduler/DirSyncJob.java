package ru.obelisk.cucmaxl.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class DirSyncJob implements Job{
	private static Logger logger = LogManager.getLogger(DirSyncJob.class);
	
	@Autowired private DirSyncUtils dirSyncUtils;
	public static Object lock = new Object();
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("DirSyncJob: Name:{}, Group:{}. Start at {}. Next start in {}", context.getJobDetail().getKey().getName(), context.getJobDetail().getKey().getGroup(), context.getScheduledFireTime(), context.getNextFireTime());
		synchronized(lock) {
			String [] arr = context.getJobDetail().getKey().getName().split("dirSyncJob");
			if(arr[1].length()>0){
				int id = Integer.parseInt(arr[1]);
				dirSyncUtils.refreshUsersFromLdap(id);
			}
		}
	}
}

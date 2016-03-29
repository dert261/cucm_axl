package ru.obelisk.cucmaxl.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class CucmAxlPortSyncJob implements Job{
	private static Logger logger = LogManager.getLogger(CucmAxlPortSyncJob.class);
	
	@Autowired CucmAxlPortSyncUtils cucmAxlPortSyncUtils;
	public static Object lock = new Object();
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		logger.info("CucmAxlPortSyncJob: \r\nName:{}, Group:{}\r\n  start at {}. Next start in {}", context.getJobDetail().getKey().getName(), context.getJobDetail().getKey().getGroup(), context.getScheduledFireTime(), context.getNextFireTime());
		synchronized(lock) {
			String [] arr = context.getJobDetail().getKey().getName().split("cucmAxlPortSyncJob");
					
			if(arr[1].length()>0){
				int axlPortId = Integer.parseInt(arr[1]);
				cucmAxlPortSyncUtils.refreshCucmAxlPort(axlPortId);
			}
		}	
	}
}

package ru.obelisk.cucmaxl.scheduler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.CronScheduleBuilder.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import ru.obelisk.database.models.entity.CucmAxlPort;
import ru.obelisk.database.models.entity.LdapDirSyncParameters;
import ru.obelisk.database.models.entity.enums.ResyncUnit;
import ru.obelisk.database.models.service.CucmAxlPortService;
import ru.obelisk.database.models.service.LdapDirSyncParametersService;

@Component
public class JobScheduler {
	private static Logger logger = LogManager.getLogger(JobScheduler.class);
	
	@Autowired private Scheduler quartzScheduler; 
	@Autowired private LdapDirSyncParametersService ldapDirSyncParametersService;
	@Autowired private CucmAxlPortService cucmAxlPortService;
		
	@PostConstruct
	public void shedulerInit(){
		try {
			if(!quartzScheduler.isStarted())
				quartzScheduler.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn(e);
		}
		ldapDirSyncShedulerInit();
		cucmAxlPortSyncShedulerInit();
	}
	
	private void ldapDirSyncShedulerInit(){
		List<LdapDirSyncParameters> ldapDirs = ldapDirSyncParametersService.findAll();
		Iterator<LdapDirSyncParameters> ldapDirsIterator = ldapDirs.iterator();
		while(ldapDirsIterator.hasNext()){
			LdapDirSyncParameters ldapDir = ldapDirsIterator.next();
			if(ldapDir.isResyncFlag()){
				logger.info("Schedule job LdapDirSync with name: {}", "dirSyncJob"+ldapDir.getId());
				createJobAndTrigger("dirSyncJob"+ldapDir.getId(), "dirSyncJobGroup", 
									"dirSyncTrigger"+ldapDir.getId(), "dirSyncTriggerGroup", 
									DirSyncJob.class, 
									createCronRule(ldapDir.getResyncInterval(), ldapDir.getResyncUnit()));
			}
		}
	}
	
	private void cucmAxlPortSyncShedulerInit(){
		List<CucmAxlPort> cucmAxlPorts = cucmAxlPortService.findAll();
		Iterator<CucmAxlPort> cucmAxlPortsIterator = cucmAxlPorts.iterator();
		while(cucmAxlPortsIterator.hasNext()){
			CucmAxlPort cucmAxlPort = cucmAxlPortsIterator.next();
			if(cucmAxlPort.isResyncFlag()){
				logger.info("Schedule job CucmAxlPort with name: {}", "cucmAxlPortSyncJob"+cucmAxlPort.getId());
				createJobAndTrigger("cucmAxlPortSyncJob"+cucmAxlPort.getId(), "cucmAxlPortSyncJobGroup", 
									"cucmAxlPortSyncTrigger"+cucmAxlPort.getId(), "cucmAxlPortSyncTriggerGroup", 
									CucmAxlPortSyncJob.class, 
									createCronRule(cucmAxlPort.getResyncInterval(), cucmAxlPort.getResyncUnit()));
			}
		}
	}
	
	public void rescheduleCucmAxlPortJob(CucmAxlPort cucmAxlPort){
		if(cucmAxlPort.isResyncFlag()){
			Trigger schedulerTrigger = null;
			try {
				schedulerTrigger = quartzScheduler.getTrigger(new TriggerKey("cucmAxlPortSyncTrigger"+cucmAxlPort.getId(), "cucmAxlPortSyncTriggerGroup"));
			} catch (SchedulerException e) {
				logger.warn(e);
			}
			if(schedulerTrigger==null){
				addCucmAxlPortJob(cucmAxlPort);
			} else {	
				logger.info("ReSchedule job CucmAxlPort with name: {}", "cucmAxlPortSyncJob"+cucmAxlPort.getId());
				rescheduleJobAndTrigger("cucmAxlPortSyncTrigger"+cucmAxlPort.getId(), "cucmAxlPortSyncTriggerGroup", 
								createCronRule(cucmAxlPort.getResyncInterval(), cucmAxlPort.getResyncUnit()));
			}	
		}
	}
	
	public void rescheduleLdapDirSyncJob(LdapDirSyncParameters ldapDir){
		if(ldapDir.isResyncFlag()){
			Trigger schedulerTrigger = null;
			try {
				schedulerTrigger = quartzScheduler.getTrigger(new TriggerKey("dirSyncTrigger"+ldapDir.getId(), "dirSyncTriggerGroup"));
			} catch (SchedulerException e) {
				logger.warn(e);
			}
			if(schedulerTrigger==null){
				addLdapDirSyncJob(ldapDir);
			} else {	
				logger.info("ReSchedule trigger LdapDirSync with name: {}", "dirSyncTrigger"+ldapDir.getId());
				rescheduleJobAndTrigger("dirSyncTrigger"+ldapDir.getId(), "dirSyncTriggerGroup",								 
								createCronRule(ldapDir.getResyncInterval(), ldapDir.getResyncUnit()));
			}
		}
	}
	
	public void addCucmAxlPortJob(CucmAxlPort cucmAxlPort){
		if(cucmAxlPort.isResyncFlag()){
			logger.info("Schedule job CucmAxlPort with name: {}", "cucmAxlPortSyncJob"+cucmAxlPort.getId());
			createJobAndTrigger("cucmAxlPortSyncJob"+cucmAxlPort.getId(), "cucmAxlPortSyncJobGroup", 
								"cucmAxlPortSyncTrigger"+cucmAxlPort.getId(), "cucmAxlPortSyncTriggerGroup", 
								CucmAxlPortSyncJob.class, 
								createCronRule(cucmAxlPort.getResyncInterval(), cucmAxlPort.getResyncUnit()));
		}
	}
	
	public void addLdapDirSyncJob(LdapDirSyncParameters ldapDir){
		if(ldapDir.isResyncFlag()){
			logger.info("Schedule job LdapDirSync with name: {}", "dirSyncJob"+ldapDir.getId());
			createJobAndTrigger("dirSyncJob"+ldapDir.getId(), "dirSyncJobGroup", 
								"dirSyncTrigger"+ldapDir.getId(), "dirSyncTriggerGroup", 
								DirSyncJob.class, 
								createCronRule(ldapDir.getResyncInterval(), ldapDir.getResyncUnit()));
		}
	}
	
	public boolean deleteJob(String jobKeyName, String jobKeyGroup){
		boolean result = false;
		try {
			result = quartzScheduler.deleteJob(new JobKey(jobKeyName, jobKeyGroup));
			logger.info("Delete job {}.{}", jobKeyName, jobKeyGroup);
		} catch (SchedulerException e) {
			logger.warn(e);
		}
		return result;
	}
	
	public Date getNextFireTime(String triggerName, String triggerGroup){
		Trigger shedulerTrigger = null;
		Date nextFireTime = null;
		try {
			shedulerTrigger = quartzScheduler.getTrigger(new TriggerKey(triggerName, triggerGroup));
			nextFireTime = shedulerTrigger!=null ? shedulerTrigger.getNextFireTime() : null;
		} catch (Exception e) {
			logger.warn(e);
		}
		return nextFireTime;
	}
	
	private String createCronRule(int interval, ResyncUnit unit){
		String cronRule = null;
		switch(unit){
			case MINUTE:	cronRule="0 */"+interval+" * ? * * *"; 
				break;
			case HOUR:		cronRule="0 0 */"+interval+" ? * * *";
				break;
			case DAY:		cronRule="0 0 0 */"+interval+" * ? *";
				break;
			case MONTH:		cronRule="0 0 0 1 */"+interval+" ? *";
				break;
			default: break;
		}
		return cronRule;
	}
	
	private void createJobAndTrigger(	String jobKeyName, String jobKeyGroup, 
										String triggerKeyName, String triggerKeyGroup, 
										Class<? extends Job> jobClass,
										String cronRule){
		if(quartzScheduler!=null){
			JobDetail job = newJob(jobClass)
					.withIdentity(jobKeyName, jobKeyGroup)
					.build();
			
			Trigger trigger = newTrigger()
					.withIdentity(triggerKeyName, triggerKeyGroup)
					.withSchedule(cronSchedule(cronRule)
							.withMisfireHandlingInstructionFireAndProceed())
					.build();
										 
			try {
				quartzScheduler.scheduleJob(job, trigger);
				logger.info("Next fire time is {}", trigger.getNextFireTime());
			} catch (SchedulerException e) {
				logger.warn(e);
			}
		}
	}
	
	private void rescheduleJobAndTrigger(	String triggerKeyName, String triggerKeyGroup, String cronRule) {
		if(quartzScheduler!=null){
			Trigger trigger = newTrigger()
					.withIdentity(triggerKeyName, triggerKeyGroup)
					.withSchedule(cronSchedule(cronRule)
							.withMisfireHandlingInstructionFireAndProceed())
					.build();
			try {
				quartzScheduler.rescheduleJob(new TriggerKey(triggerKeyName, triggerKeyGroup), trigger);
				logger.info("Next fire time is {}", trigger.getNextFireTime());
			} catch (SchedulerException e) {
				logger.warn(e);
			}
			
		}
	}
}

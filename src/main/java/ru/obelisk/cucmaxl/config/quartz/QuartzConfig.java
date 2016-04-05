package ru.obelisk.cucmaxl.config.quartz;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {
	private static Logger logger = LogManager.getLogger(QuartzConfig.class);
	@Autowired private ApplicationContext applicationContext;
	
	@Bean
	public SchedulerFactoryBean quartzSchedulerBean() {
		SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();
		quartzScheduler.setOverwriteExistingJobs(true);
		quartzScheduler.setSchedulerName("quartz-scheduler");
		
		// custom job factory of spring with DI support for @Autowired!
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		quartzScheduler.setJobFactory(jobFactory);
		quartzScheduler.setQuartzProperties(quartzProperties());
		quartzScheduler.setAutoStartup(true);
		return quartzScheduler;
	}
	
	private Properties quartzProperties() {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		Properties properties = null;
		try {
			propertiesFactoryBean.afterPropertiesSet();
			properties = propertiesFactoryBean.getObject();
		} catch (IOException e) {
			logger.warn("Cannot load quartz.properties.");
		}
		return properties;
	}
}

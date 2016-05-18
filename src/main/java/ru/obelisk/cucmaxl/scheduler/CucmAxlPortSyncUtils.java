package ru.obelisk.cucmaxl.scheduler;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.obelisk.cucmaxl.cucm.service.CucmWithDBService;
import ru.obelisk.database.models.entity.CucmAxlPort;
import ru.obelisk.database.models.entity.enums.ResyncStatus;
import ru.obelisk.database.models.service.CucmAxlPortService;
import ru.obelisk.module.utils.TimePeriod;
import ru.obelisk.cucmaxl.utils.ObeliskStringUtils;

@Component
public class CucmAxlPortSyncUtils {
	private static Logger logger = LogManager.getLogger(CucmAxlPortSyncUtils.class);
	
	@Autowired private CucmAxlPortService cucmAxlPortService;
	@Autowired private CucmWithDBService cucmDbService;
		
	public void refreshCucmAxlPort(int axlPortId) {
		
			logger.info("Requesting resync CUCM AXL port");
			
			CucmAxlPort axlPort = cucmAxlPortService.findById(axlPortId);
			if(axlPort!=null){
				LocalDateTime startDate = LocalDateTime.now();
				axlPort.setResyncStatus(ResyncStatus.ACTIVE);
				cucmAxlPortService.edit(axlPort);
				logger.info("Start resync CUCM AXL Port at {}: {}",startDate, axlPort);
				
				try {
					cucmDbService.cucmSQLQuery(axlPort);
				} catch (Exception e) {
					logger.warn(ObeliskStringUtils.getTraceToLog(e));
				}
				LocalDateTime stopDate = LocalDateTime.now();
				logger.info("Stop resync CUCM AXL Port at {}: {}",stopDate, axlPort);
				TimePeriod interval = TimePeriod.getDateTimeDuration(startDate, stopDate); 
				logger.info("Total work time is {}", interval);
			}
			axlPort.setResyncStatus(ResyncStatus.NONACTIVE);
			cucmAxlPortService.edit(axlPort);
	}
}

package ru.obelisk.cucmaxl.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.obelisk.cucmaxl.cucm.service.CucmWithDBService;
import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.entity.enums.ResyncStatus;
import ru.obelisk.cucmaxl.database.models.service.CucmAxlPortService;
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
				DateTime startDate = new DateTime();
				axlPort.setResyncStatus(ResyncStatus.ACTIVE);
				cucmAxlPortService.edit(axlPort);
				logger.info("Start resync CUCM AXL Port at {}: {}",startDate, axlPort);
				
				try {
					cucmDbService.cucmSQLQuery(axlPort);
				} catch (Exception e) {
					logger.warn(ObeliskStringUtils.getTraceToLog(e));
				}
				DateTime stopDate = new DateTime();
				logger.info("Stop resync CUCM AXL Port at {}: {}",stopDate, axlPort);
				Interval interval = new Interval(startDate, stopDate); 
				logger.info("Total work time is {}", interval);
			}
			axlPort.setResyncStatus(ResyncStatus.NONACTIVE);
			cucmAxlPortService.edit(axlPort);
	}
}

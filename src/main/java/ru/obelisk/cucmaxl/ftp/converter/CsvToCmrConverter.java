package ru.obelisk.cucmaxl.ftp.converter;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import ru.obelisk.cucmaxl.database.models.entity.CallManagementRecord;
import ru.obelisk.cucmaxl.ftp.csv.CsvCucmCmr;
import ru.obelisk.cucmaxl.utils.ObeliskStringUtils;

@Service
@Log4j2
public class CsvToCmrConverter implements CsvToDBConverter<CsvCucmCmr, CallManagementRecord>{

	@Override
	public CallManagementRecord convert(CsvCucmCmr item) {
		CallManagementRecord cmr = new CallManagementRecord();
		try {
			BeanUtils.copyProperties(cmr, item);
			
			cmr.setDateTimeStampDb(item.getDateTimeStamp() > 0 ? 
					LocalDateTime.ofInstant(Instant.ofEpochSecond(item.getDateTimeStamp()), TimeZone.getDefault().toZoneId()) : null);
						
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.info(ObeliskStringUtils.getTraceToLog(e));
		}
		return cmr;
	}
	
}

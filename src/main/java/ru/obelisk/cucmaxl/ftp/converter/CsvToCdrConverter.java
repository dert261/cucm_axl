package ru.obelisk.cucmaxl.ftp.converter;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import ru.obelisk.cucmaxl.database.models.entity.CallDetailRecord;
import ru.obelisk.cucmaxl.ftp.csv.CsvCucmCdr;
import ru.obelisk.cucmaxl.utils.ObeliskStringUtils;

@Service
@Log4j2
public class CsvToCdrConverter implements CsvToDBConverter<CsvCucmCdr, CallDetailRecord>{

	@Override
	public CallDetailRecord convert(CsvCucmCdr item) {
		CallDetailRecord cdr = new CallDetailRecord();
		try {
			BeanUtils.copyProperties(cdr, item);
			
			//DateTime conversion (from Integer to Timestamp)
			cdr.setDateTimeOriginationDb(item.getDateTimeOrigination() > 0 ? 
					LocalDateTime.ofInstant(Instant.ofEpochSecond(item.getDateTimeOrigination()), TimeZone.getDefault().toZoneId()) : null);
			cdr.setDateTimeConnectDb(item.getDateTimeConnect() > 0 ? 
					LocalDateTime.ofInstant(Instant.ofEpochSecond(item.getDateTimeConnect()), TimeZone.getDefault().toZoneId()) : null);
			cdr.setDateTimeConnectDb(item.getDateTimeDisconnect() > 0 ? 
					LocalDateTime.ofInstant(Instant.ofEpochSecond(item.getDateTimeDisconnect()), TimeZone.getDefault().toZoneId()) : null);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.info(ObeliskStringUtils.getTraceToLog(e));
		}
		return cdr;
	}
}

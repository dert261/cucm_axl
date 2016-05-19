package ru.obelisk.cucmaxl.statistics.metrics;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import ru.obelisk.database.models.views.View;

@Component
@Data
public class FtpMetrics {
	@JsonView(value={View.Statistica.class})
	private long countOfRecordsProcesed;
	
	@JsonView(value={View.Statistica.class})
	private long countOfFilesProcesed;
	
	@JsonView(value={View.Statistica.class})
	private double averageRecordsInSec;
	
	@JsonView(value={View.Statistica.class})
	private double averageFilesInSec;
	
	private long lastCountOfRecordsProcesed;
	private long lastCountOfFilesProcesed;
		
	synchronized public void addRecordsProcesed(long i){
		this.countOfRecordsProcesed+=i;
	}
	
	synchronized public void addFilesProcesed(long i){
		this.countOfFilesProcesed+=i;
	}
	
	@Scheduled(fixedRate=60000)
	public void stat(){
		averageRecordsInSec = Math.round((double)(((double)countOfRecordsProcesed-lastCountOfRecordsProcesed)/60) * 100.0) / 100.0;
		averageFilesInSec = Math.round((double)(((double)countOfFilesProcesed-lastCountOfFilesProcesed)/60) * 100.0) / 100.0;

		lastCountOfRecordsProcesed = countOfRecordsProcesed;
		lastCountOfFilesProcesed = countOfFilesProcesed;
	}
}

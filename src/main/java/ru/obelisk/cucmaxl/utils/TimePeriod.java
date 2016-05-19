package ru.obelisk.cucmaxl.utils;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.Data;

@Data
public class TimePeriod {
	private final long years;
	private final long months;
	private final long days;
	
	private final long hours;
	private final long minutes;
	private final long seconds;
	
	public static TimePeriod getDateTimeDuration(LocalDateTime fromDateTime, LocalDateTime toDateTime){
		LocalDateTime tempDateTime = LocalDateTime.from( fromDateTime );

		long years = tempDateTime.until( toDateTime, ChronoUnit.YEARS);
		tempDateTime = tempDateTime.plusYears( years );

		long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS);
		tempDateTime = tempDateTime.plusMonths( months );

		long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS);
		tempDateTime = tempDateTime.plusDays( days );


		long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS);
		tempDateTime = tempDateTime.plusHours( hours );

		long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES);
		tempDateTime = tempDateTime.plusMinutes( minutes );

		long seconds = tempDateTime.until( toDateTime, ChronoUnit.SECONDS);
		
		return new TimePeriod(years, months, days, hours, minutes, seconds);
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
    	DecimalFormat twoDigFormater = new DecimalFormat("00");
    	DecimalFormat fourDigFormater = new DecimalFormat("0000");
    	
    	builder.append(twoDigFormater.format(this.getDays())+".");
    	builder.append(twoDigFormater.format(this.getMonths())+".");
    	builder.append(fourDigFormater.format(this.getYears())+" ");
    	builder.append(twoDigFormater.format(this.getHours())+":");
    	builder.append(twoDigFormater.format(this.getMinutes())+":");
    	builder.append(twoDigFormater.format(this.getSeconds()));
    	
    	return builder.toString();
	}
}

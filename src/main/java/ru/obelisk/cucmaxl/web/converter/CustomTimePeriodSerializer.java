package ru.obelisk.cucmaxl.web.converter;

import java.io.IOException;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import ru.obelisk.module.utils.TimePeriod;

public class CustomTimePeriodSerializer extends JsonSerializer<TimePeriod>{
	
    @Override
    public void serialize(TimePeriod value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
    	    	
    	StringBuilder builder = new StringBuilder();
    	DecimalFormat twoDigFormater = new DecimalFormat("00");
    	DecimalFormat fourDigFormater = new DecimalFormat("0000");
    	
    	builder.append(twoDigFormater.format(value.getDays())+".");
    	builder.append(twoDigFormater.format(value.getMonths())+".");
    	builder.append(fourDigFormater.format(value.getYears())+" ");
    	builder.append(twoDigFormater.format(value.getHours())+":");
    	builder.append(twoDigFormater.format(value.getMinutes())+":");
    	builder.append(twoDigFormater.format(value.getSeconds()));
    	
    	jsonGenerator.writeObject(builder.toString());
    }
}

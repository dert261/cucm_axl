package ru.obelisk.cucmaxl.web.converter;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.MatchResult;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import ru.obelisk.module.utils.TimePeriod;


public class CustomTimePeriodDeserializer extends JsonDeserializer<TimePeriod>{
	private static final String datePeriodPattern = "^(\\d+)\\.(\\d+)\\.(\\d+)[ ](\\d+):(\\d+):(\\d+)$";

    @Override
	public TimePeriod deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
    	
    	TimePeriod timePeriod = null;
    	Scanner scan = new Scanner(jsonParser.getText());
    	
    	scan.findInLine(datePeriodPattern);
        MatchResult result = scan.match();
        
        timePeriod = new TimePeriod(Long.parseLong(result.group(0)), 
        		Long.parseLong(result.group(1)),
        		Long.parseLong(result.group(2)),
        		Long.parseLong(result.group(3)),
        		Long.parseLong(result.group(4)),
        		Long.parseLong(result.group(5)));
        scan.close(); 

    	return timePeriod;
	}
}

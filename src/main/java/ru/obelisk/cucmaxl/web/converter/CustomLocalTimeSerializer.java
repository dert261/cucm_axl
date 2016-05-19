package ru.obelisk.cucmaxl.web.converter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomLocalTimeSerializer extends JsonSerializer<LocalTime>{
	private final static DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    @Override
    public void serialize(LocalTime value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
    	jsonGenerator.writeObject(value.format(DATETIME_FORMAT));
    }
}

package ru.obelisk.cucmaxl.web.converter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime>{
	private final static DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    
    @Override
    public void serialize(LocalDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
    	jsonGenerator.writeObject(value.format(DATETIME_FORMAT));
    }
}

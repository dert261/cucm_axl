package ru.obelisk.cucmaxl.web.converter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomLocalTimeDeserializer extends JsonDeserializer<LocalTime>{
	private final static DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    @Override
	public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		return LocalTime.parse(jsonParser.getText(), DATETIME_FORMAT);
	}
}

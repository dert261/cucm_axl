package ru.obelisk.cucmaxl.web.converter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime>{
	private final static DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        
    @Override
	public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		return LocalDateTime.parse(jsonParser.getText(), DATETIME_FORMAT);
	}
}

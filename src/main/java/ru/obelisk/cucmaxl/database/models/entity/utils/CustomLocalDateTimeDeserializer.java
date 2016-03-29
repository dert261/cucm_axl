package ru.obelisk.cucmaxl.database.models.entity.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

public class CustomLocalDateTimeDeserializer extends StdScalarDeserializer<LocalDateTime>{
	private static final long serialVersionUID = 6571791526215148497L;
	private final static DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    
    public CustomLocalDateTimeDeserializer() {
          super(LocalDateTime.class);
    }

    protected CustomLocalDateTimeDeserializer(Class<LocalDateTime> t) {
          super(t);
    }

    @Override
	public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		return LocalDateTime.parse(jsonParser.getText(), DATETIME_FORMAT);
	}
}

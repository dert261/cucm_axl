package ru.obelisk.cucmaxl.database.models.entity.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

public class CustomLocalDateTimeSerializer extends StdScalarSerializer<LocalDateTime>{
	private static final long serialVersionUID = 6571791526215148497L;
	private final static DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    
    public CustomLocalDateTimeSerializer() {
          super(LocalDateTime.class);
    }

    protected CustomLocalDateTimeSerializer(Class<LocalDateTime> t) {
          super(t);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
    	jsonGenerator.writeObject(value.format(DATETIME_FORMAT));
    }
}

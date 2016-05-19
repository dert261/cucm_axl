package ru.obelisk.cucmaxl.statistics;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import ru.obelisk.module.converter.CustomLocalDateTimeDeserializer;
import ru.obelisk.module.converter.CustomLocalDateTimeSerializer;

@Data
public class ModuleEvent {
	private EventType eventType;
	private String eventMessage;
	
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime fireTime = LocalDateTime.now();
}

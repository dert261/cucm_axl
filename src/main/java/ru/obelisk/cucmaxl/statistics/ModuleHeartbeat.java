package ru.obelisk.cucmaxl.statistics;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import ru.obelisk.module.converter.CustomLocalDateTimeDeserializer;
import ru.obelisk.module.converter.CustomLocalDateTimeSerializer;
import ru.obelisk.module.converter.CustomTimePeriodDeserializer;
import ru.obelisk.module.converter.CustomTimePeriodSerializer;
import ru.obelisk.module.utils.TimePeriod;

@Data
@Component
public class ModuleHeartbeat {
	private long eventCount;
	
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private final LocalDateTime startTime = LocalDateTime.now();
	
	@JsonSerialize(using = CustomTimePeriodSerializer.class)
	@JsonDeserialize(using = CustomTimePeriodDeserializer.class)
	private TimePeriod uptime;
	
	private List<ModuleEvent> events = new ArrayList<ModuleEvent>(0);
	
	synchronized public void addEvent(ModuleEvent event){
		if(this.events.size()>=eventCount){
			events.remove(0);
		}
		this.events.add(event);
	}
	
	synchronized public void removeEvents(){
		events.clear();
	}
}

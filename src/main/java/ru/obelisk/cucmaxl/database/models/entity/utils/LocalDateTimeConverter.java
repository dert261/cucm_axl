package ru.obelisk.cucmaxl.database.models.entity.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	 @Override
	 public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
		 return (locDateTime == null ? null : Timestamp.valueOf(locDateTime));
	 }
	 @Override
	 public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
		 return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime());
	 }
}
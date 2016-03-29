package ru.obelisk.cucmaxl.database.models.entity.utils;

import java.time.LocalDate;
import java.sql.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	 @Override
	 public Date convertToDatabaseColumn(LocalDate locDate) {
		 return (locDate == null ? null : Date.valueOf(locDate));
	 }
	 @Override
	 public LocalDate convertToEntityAttribute(Date sqlDate) {
		 return (sqlDate == null ? null : sqlDate.toLocalDate());
	 }
}
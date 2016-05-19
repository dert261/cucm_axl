package ru.obelisk.cucmaxl.ftp.meta;

import lombok.Data;

@Data
public class MetaField {
		
	private String name;
	private Class<?> type;
	private Object value;
}

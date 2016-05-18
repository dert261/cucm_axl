package ru.obelisk.cucmaxl.web.controllers.utils;

import lombok.Data;

@Data
public class EnumDictionaryEntry {
	private Long longValue;
	private String textValue;
	
	private boolean popover;
	
	private String popoverTitle;
	private String popoverMessage;
}

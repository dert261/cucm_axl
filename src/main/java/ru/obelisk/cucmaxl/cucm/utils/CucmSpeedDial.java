package ru.obelisk.cucmaxl.cucm.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CucmSpeedDial {
	@Getter
	@Setter
	private String index;
	
	@Getter
	@Setter
	private String number;
	
	@Getter
	@Setter
	private String label; 
}

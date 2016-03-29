package ru.obelisk.cucmaxl.web.controllers.utils;

import com.opencsv.bean.CsvBind;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CsvChangeNumber {

	@CsvBind(required=true)
	@Getter
	@Setter
	private String oldNumber;
	
	@CsvBind(required=true)
	@Getter
	@Setter
	private String newNumber;
	
	@CsvBind(required=false)
	@Getter
	@Setter
	private String message;
	
	@CsvBind(required=false)
	@Getter
	@Setter
	private int state;
}

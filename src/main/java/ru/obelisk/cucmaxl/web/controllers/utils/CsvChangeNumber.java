package ru.obelisk.cucmaxl.web.controllers.utils;

import com.opencsv.bean.CsvBind;
import lombok.Data;

@Data
public class CsvChangeNumber {

	@CsvBind(required=true)
	private String oldNumber;
	
	@CsvBind(required=true)
	private String newNumber;
	
	@CsvBind(required=false)
	private String message;
	
	@CsvBind(required=false)
	private int state;
}

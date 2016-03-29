package ru.obelisk.cucmaxl.web.databinding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class AjaxOperationResult {
	@Getter
	@Setter
	private int status;
	
	@Getter
	@Setter
	private String message;
	
	@Getter
	@Setter
	private String redirectURL;
}

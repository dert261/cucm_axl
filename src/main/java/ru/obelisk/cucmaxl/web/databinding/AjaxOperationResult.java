package ru.obelisk.cucmaxl.web.databinding;

import lombok.Data;

@Data
public class AjaxOperationResult {
	private int status;
	private String message;
	private String redirectURL;
}

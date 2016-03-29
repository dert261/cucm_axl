package ru.obelisk.cucmaxl.cucm.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude={"device"})
public class CucmExportLine {
	
	@Getter
	@Setter
	private String lineId;
	
	@Getter
	@Setter
	private String display;
	
	@Getter
	@Setter
	private String displayascii;
	
	@Getter
	@Setter
	private String externalPhoneNumberMask;
	
	@Getter
	@Setter
	private String preferredMediaSource;
	
	@Getter
	@Setter
	private CucmExportDevice device;
	
	@Getter	
	@Setter
	private CucmExtension extension;
}

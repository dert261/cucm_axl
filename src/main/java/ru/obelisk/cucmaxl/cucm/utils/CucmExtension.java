package ru.obelisk.cucmaxl.cucm.utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude={"lines"})
public class CucmExtension {
	
	@Getter
	@Setter
	private String number;
	
	@Getter
	@Setter
	private String description;
	
	@Getter
	@Setter
	private String alertingname;
	
	@Getter
	@Setter
	private String alertingnameascii;
	
	@Getter
	@Setter
	private String cfadestination;
	
	@Getter
	@Setter
	private String cfbdestination;
	
	@Getter
	@Setter
	private String cfnadestination;
	
	@Getter
	@Setter
	private String cfnaduration;
	
	@Getter
	@Setter
	private String css;
	
	@Getter
	@Setter
	private String callPickupGroup;
	
	@Getter
	@Setter
	private List<CucmExportLine> lines = new ArrayList<CucmExportLine>(0); 
}

package ru.obelisk.cucmaxl.cucm.utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.database.models.entity.User;
import ru.obelisk.database.models.entity.cme.CmeDevice;
import ru.obelisk.database.models.entity.cme.CmeSipDevice;
import ru.obelisk.database.models.entity.enums.CucmPhoneType;

@ToString
public class CucmExportDevice {
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private String description;
	
	@Getter
	@Setter
	private CucmPhoneType type;
	
	@Getter
	@Setter
	private String protocol;
	
	@Getter
	@Setter
	private User user;
	
	@Getter
	@Setter
	private boolean enable;
	
	@Getter
	@Setter
	private List<CucmExportLine> lines = new ArrayList<CucmExportLine>(0);
	
	@Getter
	@Setter
	private List<CucmSpeedDial> speedDials = new ArrayList<CucmSpeedDial>(0);
		
	@Getter
	@Setter
	private List<CucmBusyLampField> blfs = new ArrayList<CucmBusyLampField>(0);
	
	@Getter
	@Setter
	private List<CucmAddOnModule> addons = new ArrayList<CucmAddOnModule>(0);
	
	@Getter
	@Setter
	private CmeDevice cmeDevice;
	
	@Getter
	@Setter
	private CmeSipDevice cmeSipDevice;
}

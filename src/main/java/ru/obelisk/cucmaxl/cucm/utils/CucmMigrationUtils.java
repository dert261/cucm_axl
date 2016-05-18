package ru.obelisk.cucmaxl.cucm.utils;

import java.util.ArrayList;
import java.util.Iterator;
//import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.obelisk.database.models.entity.User;
import ru.obelisk.database.models.entity.cme.CmeAddonModule;
import ru.obelisk.database.models.entity.cme.CmeBlfSpeedDial;
import ru.obelisk.database.models.entity.cme.CmeCustomExtension;
import ru.obelisk.database.models.entity.cme.CmeCustomSipExtension;
import ru.obelisk.database.models.entity.cme.CmeDevice;
import ru.obelisk.database.models.entity.cme.CmeExtMapStatus;
import ru.obelisk.database.models.entity.cme.CmeExtension;
import ru.obelisk.database.models.entity.cme.CmeSipDevice;
import ru.obelisk.database.models.entity.cme.CmeSipExtMapStatus;
import ru.obelisk.database.models.entity.cme.CmeSipExtension;
import ru.obelisk.database.models.entity.cme.CmeSpeedDial;
import ru.obelisk.database.models.entity.enums.CucmPhoneType;
//import ru.obelisk.database.models.service.UserService;

@Component
public class CucmMigrationUtils {
	//@Autowired private UserService userService;
	
	public String getPhoneButtonTemplate(int lineCount, String deviceType, String deviceProtocol){
		String phoneButtonTemplate = "NOT SET";
		String model = deviceType.replaceFirst("Cisco ", "");
		String protocol = deviceProtocol;
				
		if(lineCount==1){
			StringBuilder phoneButtonTemplateBuilder = new StringBuilder();
			phoneButtonTemplateBuilder.append(model);
			phoneButtonTemplateBuilder.append(" ");
			phoneButtonTemplateBuilder.append(protocol);
			phoneButtonTemplateBuilder.append(" ");
			phoneButtonTemplateBuilder.append(lineCount+"L");
			phoneButtonTemplateBuilder.append(" ");
			phoneButtonTemplateBuilder.append("another SD");
			
			switch(model.trim()+" "+protocol.trim()){
				case "7912 SIP"		: phoneButtonTemplate = "7912 SIP 1L no SD"; break;
				case "7936 SCCP" 	: phoneButtonTemplate = "7936 SCCP 1L no SD"; break;
				case "7940 SIP" 	: phoneButtonTemplate = "7940 SIP 1L no SD"; break;
				case "7942 SCCP"	: phoneButtonTemplate = "7942G SCCP 1L another SD"; break;
				case "7942 SIP"		: phoneButtonTemplate = "7942G SIP 1L another SD"; break;
				case "7962 SCCP"	: phoneButtonTemplate = "7962G SCCP 1L another SD"; break;
				case "7962 SIP"		: phoneButtonTemplate = "7962G SIP 1L another SD"; break;
				default 			: phoneButtonTemplate = phoneButtonTemplateBuilder.toString(); break; 
			}
			
		} else if(lineCount>=2){
			StringBuilder phoneButtonTemplateBuilder = new StringBuilder();
			phoneButtonTemplateBuilder.append("Standard");
			phoneButtonTemplateBuilder.append(" ");
			phoneButtonTemplateBuilder.append(model);
			phoneButtonTemplateBuilder.append(" ");
			phoneButtonTemplateBuilder.append(protocol);
			
			switch(model+" "+protocol){
				case "7902 SCCP"	: phoneButtonTemplate = "Standard 7902"; break;
				case "7906 SCCP" 	: phoneButtonTemplate = "Standard 7906"; break;
				case "7910 SCCP" 	: phoneButtonTemplate = "Standard 7910"; break;
				case "7911 SCCP" 	: phoneButtonTemplate = "Standard 7911"; break;
				case "7920 SCCP" 	: phoneButtonTemplate = "Standard 7920"; break;
				case "7935 SCCP" 	: phoneButtonTemplate = "Standard 7935"; break;
				case "7936 SCCP" 	: phoneButtonTemplate = "Standard 7936"; break;
				case "7937 SCCP" 	: phoneButtonTemplate = "Standard 7937"; break;
				case "7942 SCCP" 	: phoneButtonTemplate = "Standard 7942G SCCP"; break;
				case "7942 SIP" 	: phoneButtonTemplate = "Standard 7942G SIP"; break;
				case "7962 SCCP" 	: phoneButtonTemplate = "Standard 7962G SCCP"; break;
				case "7962 SIP" 	: phoneButtonTemplate = "Standard 7962G SIP"; break;
				case "7985 SCCP" 	: phoneButtonTemplate = "Standard 7985"; break;
				default 			: phoneButtonTemplate = phoneButtonTemplateBuilder.toString(); break; 
			}
		}
		
		return phoneButtonTemplate;
	}
	
	public String getSoftkeyTemplate(String deviceType){
		String hardKeyTemplate = "69xx-78xx Feature Hardkey -- Mobility";
		String softKeyTemplate = "79XX -- Mobility";
		
		switch(deviceType){
			case "Cisco 6921": 		return hardKeyTemplate;
			case "Cisco 6941": 		return hardKeyTemplate;
			case "Cisco 6961": 		return hardKeyTemplate;
			case "Cisco 7811": 		return hardKeyTemplate;
			case "Cisco 7821": 		return hardKeyTemplate;
			case "Cisco 7841": 		return hardKeyTemplate;
			case "Cisco 7910": 		return softKeyTemplate;
			case "Cisco 7911": 		return softKeyTemplate;
			case "Cisco 7912": 		return softKeyTemplate;
			case "Cisco 7925": 		return softKeyTemplate;
			case "Cisco 7936": 		return softKeyTemplate;
			case "Cisco 7937": 		return softKeyTemplate;
			case "Cisco 7940": 		return softKeyTemplate;
			case "Cisco 7941": 		return softKeyTemplate;
			case "Cisco 7942": 		return softKeyTemplate;
			case "Cisco 7945": 		return softKeyTemplate;
			case "Cisco 7960": 		return softKeyTemplate;
			case "Cisco 7961": 		return softKeyTemplate;
			case "Cisco 7961G-GE": 	return softKeyTemplate;
			case "Cisco 7962": 		return softKeyTemplate;
			case "Cisco 7965": 		return softKeyTemplate;
			case "Cisco 7970": 		return softKeyTemplate;
			default: 				return "";
		}
	}
	
	public String getDeviceSecurityProfile(String deviceType, String deviceProtocol){
		return deviceType+" - Standard "+deviceProtocol+" Non-Secure Profile";
	}
	
	public String getSIPProfile(String deviceType, String deviceProtocol, String sipProfile){
		String oldSipProfile = "Older IP PHONE SIP Profile";
		String newSipProfile = "IP PHONE SIP Profile";
		
		if(deviceProtocol=="SIP"){
			switch(deviceType){
				case "Cisco 7902": 		return oldSipProfile;
				case "Cisco 7905": 		return oldSipProfile;
				case "Cisco 7910": 		return oldSipProfile;
				case "Cisco 7912": 		return oldSipProfile;
				case "Cisco 7920": 		return oldSipProfile;
				case "Cisco 7935": 		return oldSipProfile;
				case "Cisco 7940": 		return oldSipProfile;
				case "Cisco 7960": 		return oldSipProfile;
				default: 				return newSipProfile;
			}	
		}	
		else 
			return sipProfile;
	}
		
	public String getRussianLabel(String fullName){
		if(fullName.length()==0) return fullName;
		StringBuilder result = new StringBuilder(); 
		
		String[] parts = fullName.split(" ");
		result.append(parts[0]);
		result.append(" ");
		result.append(parts[1]);
		
		return result.toString();
	}
	
	public String getEnglishLabel(String fullName){
		if(fullName.length()==0) return fullName;
		StringBuilder result = new StringBuilder(); 
		fullName = Transliterator.toTranslit(fullName);
		
		String[] parts = fullName.split(" ");
		result.append(parts[0]);
		result.append(" ");
		if(parts.length>1 && parts[1].length()>0){
			result.append(parts[1].charAt(0));
			result.append(".");
		}
		if(parts.length>2 && parts[2].length()>0){
			result.append(parts[2].charAt(0));
			result.append(".");
		}
		
		return result.toString();
	}
	
	public String getFullEnglishLabel(String fullName){
		return Transliterator.toTranslit(fullName);
	}
	
	public String mapRecordingMediaSource(String deviceType){
		if(deviceType==null) return "Gateway Preferred";
		String result = null;
			
		String phonePreferred = "Phone Preferred";
		String gatewayPreferred = "Gateway Preferred";
		
		switch(deviceType){
			case "Cisco 7912": 		result = gatewayPreferred; 	break;
			default: 				result = phonePreferred; 	break;
		}	
				
		return result;
	}
	
	public CucmExportDevice getCucmDeviceFromCmeSipDevice(CmeSipDevice device){
		CucmExportDevice cucmDevice = new CucmExportDevice();
		cucmDevice.setCmeSipDevice(device);
		cucmDevice.setName(device.getName());
		cucmDevice.setEnable((device.getCustomSipDevice()!=null && !device.getCustomSipDevice().isEnable()) ? false : true);
		cucmDevice.setProtocol("SIP");
		cucmDevice.setType(device.getCustomSipDevice().getPhoneType());
		cucmDevice.setUser((device.getCustomSipDevice()!=null) ? device.getCustomSipDevice().getUser() : null);
		
		Iterator<CmeSipExtMapStatus> cmeSipLinesIterator = device.getLines().iterator();
		while(cmeSipLinesIterator.hasNext()){
			CmeSipExtMapStatus cmeSipLine = cmeSipLinesIterator.next();
			CmeSipExtension cmeSipExtension = cmeSipLine.getExtension();
			if(cmeSipExtension.getCustomSipExtension()!=null && cmeSipExtension.getCustomSipExtension().isEnable()){
			
				CucmExportLine cucmLine = new CucmExportLine();
				cucmLine.setDevice(cucmDevice);
				cucmLine.setLineId(cmeSipLine.getLineId());
				
				String russianLabel = null;
				String englishLabel = null;
				User user = null;
				if(device.getCustomSipDevice()!=null && device.getCustomSipDevice().getUser()!=null){
					user = device.getCustomSipDevice().getUser();
				}	
				if(user!=null){
					if(cmeSipExtension.getCustomSipExtension().getLabel().length()==0){
						russianLabel = getRussianLabel(user.getName());
						englishLabel = getEnglishLabel(user.getName())+" "+((cmeSipExtension.getCustomSipExtension().getNumber()!=null && cmeSipExtension.getCustomSipExtension().getNumber().length()>0) ? cmeSipExtension.getCustomSipExtension().getNumber() : cmeSipExtension.getNumber());
					} else {
						russianLabel = cmeSipExtension.getCustomSipExtension().getLabel();
						englishLabel = getEnglishLabel((cmeSipExtension.getCustomSipExtension().getLabel()!=null && cmeSipExtension.getCustomSipExtension().getLabel().length()>0) ? cmeSipExtension.getCustomSipExtension().getLabel()+" " : "")+((cmeSipExtension.getCustomSipExtension().getNumber()!=null && cmeSipExtension.getCustomSipExtension().getNumber().length()>0) ? cmeSipExtension.getCustomSipExtension().getNumber() : cmeSipExtension.getNumber());
					}
				} else {
					russianLabel = cmeSipExtension.getCustomSipExtension().getLabel();
					englishLabel = getFullEnglishLabel((cmeSipExtension.getCustomSipExtension().getLabel()!=null && cmeSipExtension.getCustomSipExtension().getLabel().length()>0) ? cmeSipExtension.getCustomSipExtension().getLabel()+" " : "")+((cmeSipExtension.getCustomSipExtension().getNumber()!=null && cmeSipExtension.getCustomSipExtension().getNumber().length()>0) ? cmeSipExtension.getCustomSipExtension().getNumber() : cmeSipExtension.getNumber());
				}	
				
				CmeCustomSipExtension cmeCustomSipExtension = cmeSipExtension.getCustomSipExtension();
				cucmLine.setExternalPhoneNumberMask((cmeCustomSipExtension.getExternalPhoneNumberMask()!=null && cmeCustomSipExtension.getExternalPhoneNumberMask().length()>0) ? "7"+cmeCustomSipExtension.getExternalPhoneNumberMask().replaceAll("[\\D]", "") : "");
				cucmLine.setDisplay(russianLabel);
				cucmLine.setDisplayascii(englishLabel);
				cucmLine.setPreferredMediaSource(mapRecordingMediaSource(getStringPhoneType(cucmDevice.getType())));
				
				CucmExtension cucmExtension = new CucmExtension();
				cucmExtension.getLines().add(cucmLine);
				cucmExtension.setNumber((cmeSipExtension.getCustomSipExtension().getNumber()!=null && cmeSipExtension.getCustomSipExtension().getNumber().length()>0) ? cmeSipExtension.getCustomSipExtension().getNumber() : cmeSipExtension.getNumber());
				cucmExtension.setAlertingname(russianLabel);
				cucmExtension.setAlertingnameascii(englishLabel);
				cucmExtension.setDescription(englishLabel);
				
				cucmExtension.setCallPickupGroup((cmeSipExtension.getCustomSipExtension().getPickupGroup()!=null && cmeSipExtension.getCustomSipExtension().getPickupGroup().length()>0) ? cmeSipExtension.getCustomSipExtension().getPickupGroup() : "");
				cucmExtension.setCss("");
				
				cucmLine.setExtension(cucmExtension);
				cucmDevice.getLines().add(cucmLine);
			}
		}
		
		String deviceDescription = !cucmDevice.getLines().isEmpty() ? (cucmDevice.getLines().get(0)!=null ? cucmDevice.getLines().get(0).getDisplayascii() : "") : "";
		cucmDevice.setDescription(cucmDevice.getUser()==null ? "Public phone "+deviceDescription : deviceDescription);
		
		return cucmDevice;
	}
	
	
	public CucmExportDevice getCucmDeviceFromCmeDevice(CmeDevice device){
		CucmExportDevice cucmDevice = new CucmExportDevice();
		cucmDevice.setCmeDevice(device);
		cucmDevice.setName(device.getName());
		cucmDevice.setEnable((device.getCustomDevice()!=null && !device.getCustomDevice().isEnable()) ? false : true);
		cucmDevice.setProtocol("SCCP");
		cucmDevice.setType(getPhoneType(device.getConfigType()));
		cucmDevice.setUser((device.getCustomDevice()!=null) ? device.getCustomDevice().getUser() : null);
		
		Iterator<CmeExtMapStatus> cmeLinesIterator = device.getLines().iterator();
		while(cmeLinesIterator.hasNext()){
			CmeExtMapStatus cmeLine = cmeLinesIterator.next();
			CmeExtension cmeExtension = cmeLine.getExtension();
			if(cmeExtension.getCustomExtension()!=null && cmeExtension.getCustomExtension().isEnable()){
			
				CucmExportLine cucmLine = new CucmExportLine();
				cucmLine.setDevice(cucmDevice);
				cucmLine.setLineId(cmeLine.getLineId());
				
				String russianLabel = null;
				String englishLabel = null;
				User user = null;
				if(device.getCustomDevice()!=null && device.getCustomDevice().getUser()!=null){
					user = device.getCustomDevice().getUser();
				}	
				if(user!=null){
					if(cmeExtension.getCustomExtension().getLabel().length()==0){
						russianLabel = getRussianLabel(user.getName());
						englishLabel = getEnglishLabel(user.getName())+" "+((cmeExtension.getCustomExtension().getNumber()!=null && cmeExtension.getCustomExtension().getNumber().length()>0) ? cmeExtension.getCustomExtension().getNumber() : cmeExtension.getNumber());
					} else {
						russianLabel = cmeExtension.getCustomExtension().getLabel();
						englishLabel = getEnglishLabel((cmeExtension.getCustomExtension().getLabel()!=null && cmeExtension.getCustomExtension().getLabel().length()>0) ? cmeExtension.getCustomExtension().getLabel()+" " : "")+((cmeExtension.getCustomExtension().getNumber()!=null && cmeExtension.getCustomExtension().getNumber().length()>0) ? cmeExtension.getCustomExtension().getNumber() : cmeExtension.getNumber());
					}
				} else {
					russianLabel = cmeExtension.getCustomExtension().getLabel();
					englishLabel = getFullEnglishLabel((cmeExtension.getCustomExtension().getLabel()!=null && cmeExtension.getCustomExtension().getLabel().length()>0) ? cmeExtension.getCustomExtension().getLabel()+" " : "")+((cmeExtension.getCustomExtension().getNumber()!=null && cmeExtension.getCustomExtension().getNumber().length()>0) ? cmeExtension.getCustomExtension().getNumber() : cmeExtension.getNumber());
				}	
				
				CmeCustomExtension cmeCustomExtension = cmeExtension.getCustomExtension();
				cucmLine.setExternalPhoneNumberMask((cmeCustomExtension.getExternalPhoneNumberMask()!=null && cmeCustomExtension.getExternalPhoneNumberMask().length()>0) ? "7"+cmeCustomExtension.getExternalPhoneNumberMask().replaceAll("[\\D]", "") : "");
				cucmLine.setDisplay(russianLabel);
				cucmLine.setDisplayascii(englishLabel);
				cucmLine.setPreferredMediaSource(mapRecordingMediaSource(getStringPhoneType(cucmDevice.getType())));
				
				CucmExtension cucmExtension = new CucmExtension();
				cucmExtension.getLines().add(cucmLine);
				cucmExtension.setNumber((cmeExtension.getCustomExtension().getNumber()!=null && cmeExtension.getCustomExtension().getNumber().length()>0) ? cmeExtension.getCustomExtension().getNumber() : cmeExtension.getNumber());
				cucmExtension.setAlertingname(russianLabel);
				cucmExtension.setAlertingnameascii(englishLabel);
				cucmExtension.setDescription(englishLabel);
				
				cucmExtension.setCallPickupGroup((cmeExtension.getCustomExtension().getPickupGroup()!=null && cmeExtension.getCustomExtension().getPickupGroup().length()>0) ? cmeExtension.getCustomExtension().getPickupGroup() : "");
				cucmExtension.setCss("");
				
				cucmExtension.setCfadestination(cmeExtension.getCallForward().getCfwdAllNumber());
				cucmExtension.setCfbdestination(cmeExtension.getCallForward().getCfwdBusyNumber());
				cucmExtension.setCfnadestination(cmeExtension.getCallForward().getCfwdNoanNumber());
				cucmExtension.setCfnaduration(cmeExtension.getCallForward().getCfwdNoanTimeout());
				
				cucmLine.setExtension(cucmExtension);
				cucmDevice.getLines().add(cucmLine);
				
				cucmDevice.setAddons(getAddonsFromCmeDevice(device));
				cucmDevice.setSpeedDials(getSpeedDialsFromCmeDevice(device));
				cucmDevice.setBlfs(getBusyLampFieldsFromCmeDevice(device));
			}
		}
		
		String deviceDescription = !cucmDevice.getLines().isEmpty() ? (cucmDevice.getLines().get(0)!=null ? cucmDevice.getLines().get(0).getDisplayascii() : "") : "";
		cucmDevice.setDescription(cucmDevice.getUser()==null ? "Public phone "+deviceDescription : deviceDescription);
		
		return cucmDevice;
	}
	
	private List<CucmAddOnModule> getAddonsFromCmeDevice(CmeDevice device){
		List<CucmAddOnModule> addOnModules = new ArrayList<CucmAddOnModule>();
		Iterator<CmeAddonModule> addonIterator = device.getAddonModules().iterator();
		while(addonIterator.hasNext()){
			CmeAddonModule cmeAddon = addonIterator.next();
			CucmAddOnModule addon = new CucmAddOnModule();
			
			addon.setIndex(cmeAddon.getName());
			addon.setModel(cmeAddon.getType());
			
			addOnModules.add(addon);
		}
		return addOnModules;
	}
	
	
	private List<CucmSpeedDial> getSpeedDialsFromCmeDevice(CmeDevice device){
		List<CucmSpeedDial> speedDials = new ArrayList<CucmSpeedDial>();
		Iterator<CmeSpeedDial> speedDialsIterator = device.getSpeedDials().iterator();
		while(speedDialsIterator.hasNext()){
			CmeSpeedDial cmeSpeedDial = speedDialsIterator.next();
			CucmSpeedDial speedDial = new CucmSpeedDial();
			
			speedDial.setIndex(cmeSpeedDial.getIndex());
			speedDial.setNumber(cmeSpeedDial.getNumber());
			speedDial.setLabel(cmeSpeedDial.getLabel());
			
			speedDials.add(speedDial);
		}
		return speedDials;
	}
	
	private List<CucmBusyLampField> getBusyLampFieldsFromCmeDevice(CmeDevice device){
		List<CucmBusyLampField> blfs = new ArrayList<CucmBusyLampField>();
		Iterator<CmeBlfSpeedDial> cmeBlfsIterator = device.getBlfSpeedDials().iterator();
		while(cmeBlfsIterator.hasNext()){
			CmeBlfSpeedDial cmeBlf = cmeBlfsIterator.next();
			CucmBusyLampField blf = new CucmBusyLampField();
			
			blf.setIndex(cmeBlf.getIndex());
			blf.setNumber(cmeBlf.getNumber());
			blf.setLabel(cmeBlf.getLabel());
			
			blfs.add(blf);
		}
		return blfs;
	}
	
	
	
	    
	public CucmPhoneType getPhoneType(String deviceType){
		CucmPhoneType phoneType;
		switch(deviceType){
			case "3905": phoneType=CucmPhoneType.Cisco3905; break;
			case "3911": phoneType=CucmPhoneType.Cisco3911; break;
			case "3951": phoneType=CucmPhoneType.Cisco3951; break;
			case "6901": phoneType=CucmPhoneType.Cisco6901; break;
			case "6911": phoneType=CucmPhoneType.Cisco6911; break;
			case "6921": phoneType=CucmPhoneType.Cisco6921; break;
			case "6941": phoneType=CucmPhoneType.Cisco6941; break;
			case "6945": phoneType=CucmPhoneType.Cisco6945; break;
			case "6961": phoneType=CucmPhoneType.Cisco6961; break;
			case "7811": phoneType=CucmPhoneType.Cisco7811; break;
			case "7821": phoneType=CucmPhoneType.Cisco7821; break;
			case "7841": phoneType=CucmPhoneType.Cisco7841; break;
			case "7861": phoneType=CucmPhoneType.Cisco7861; break;
			case "7902": phoneType=CucmPhoneType.Cisco7902; break;
			case "7905": phoneType=CucmPhoneType.Cisco7905; break;
			case "7906": phoneType=CucmPhoneType.Cisco7906; break;
			case "7910": phoneType=CucmPhoneType.Cisco7910; break;
			case "7911": phoneType=CucmPhoneType.Cisco7911; break;
			case "7912": phoneType=CucmPhoneType.Cisco7912; break;
			case "7920": phoneType=CucmPhoneType.Cisco7920; break;
			case "7921": phoneType=CucmPhoneType.Cisco7921; break;
			case "7925": phoneType=CucmPhoneType.Cisco7925; break;
			case "7926": phoneType=CucmPhoneType.Cisco7926; break;
			case "7931": phoneType=CucmPhoneType.Cisco7931; break;
			case "7935": phoneType=CucmPhoneType.Cisco7935; break;
			case "7936": phoneType=CucmPhoneType.Cisco7936; break;
			case "7937": phoneType=CucmPhoneType.Cisco7937; break;
			case "7940": phoneType=CucmPhoneType.Cisco7940; break;
			case "7941": phoneType=CucmPhoneType.Cisco7941; break;
			case "7942": phoneType=CucmPhoneType.Cisco7942; break;
			case "7945": phoneType=CucmPhoneType.Cisco7945; break;
			case "7960": phoneType=CucmPhoneType.Cisco7960; break;
			case "7961": phoneType=CucmPhoneType.Cisco7961; break;
			case "7962": phoneType=CucmPhoneType.Cisco7962; break;
			case "7965": phoneType=CucmPhoneType.Cisco7965; break;
			case "7970": phoneType=CucmPhoneType.Cisco7970; break;
			case "7971": phoneType=CucmPhoneType.Cisco7971; break;
			case "7975": phoneType=CucmPhoneType.Cisco7975; break;
			case "7985": phoneType=CucmPhoneType.Cisco7985; break;
			case "8811": phoneType=CucmPhoneType.Cisco8811; break;
			case "8831": phoneType=CucmPhoneType.Cisco8831; break;
			case "8841": phoneType=CucmPhoneType.Cisco8841; break;
			case "8851": phoneType=CucmPhoneType.Cisco8851; break;
			case "8861": phoneType=CucmPhoneType.Cisco8861; break;
			case "8941": phoneType=CucmPhoneType.Cisco8941; break;
			case "8945": phoneType=CucmPhoneType.Cisco8945; break;
			case "8961": phoneType=CucmPhoneType.Cisco8961; break;
			case "9951": phoneType=CucmPhoneType.Cisco9951; break;
			case "9971": phoneType=CucmPhoneType.Cisco9971; break;
			/*case "7941GGE,
			case "7961GGE
			case "8851NR,*/
			default: phoneType=null; break;
		}
		return phoneType;
	}
	
	public String getStringPhoneType(CucmPhoneType deviceType){
		if(deviceType==null) return null;
		String phoneType;
		switch(deviceType){
			case Cisco3905: phoneType= "Cisco 3905"; break;
			case Cisco3911: phoneType= "Cisco 3911"; break;
			case Cisco3951: phoneType= "Cisco 3951"; break;
			case Cisco6901: phoneType= "Cisco 6901"; break;
			case Cisco6911: phoneType= "Cisco 6911"; break;
			case Cisco6921: phoneType= "Cisco 6921"; break;
			case Cisco6941: phoneType= "Cisco 6941"; break;
			case Cisco6945: phoneType= "Cisco 6945"; break;
			case Cisco6961: phoneType= "Cisco 6961"; break;
			case Cisco7811: phoneType= "Cisco 7811"; break;
			case Cisco7821: phoneType= "Cisco 7821"; break;
			case Cisco7841: phoneType= "Cisco 7841"; break;
			case Cisco7861: phoneType= "Cisco 7861"; break;
			case Cisco7902: phoneType= "Cisco 7902"; break;
			case Cisco7905: phoneType= "Cisco 7905"; break;
			case Cisco7906: phoneType= "Cisco 7906"; break;
			case Cisco7910: phoneType= "Cisco 7910"; break;
			case Cisco7911: phoneType= "Cisco 7911"; break;
			case Cisco7912: phoneType= "Cisco 7912"; break;
			case Cisco7920: phoneType= "Cisco 7920"; break;
			case Cisco7921: phoneType= "Cisco 7921"; break;
			case Cisco7925: phoneType= "Cisco 7925"; break;
			case Cisco7926: phoneType= "Cisco 7926"; break;
			case Cisco7931: phoneType= "Cisco 7931"; break;
			case Cisco7935: phoneType= "Cisco 7935"; break;
			case Cisco7936: phoneType= "Cisco 7936"; break;
			case Cisco7937: phoneType= "Cisco 7937"; break;
			case Cisco7940: phoneType= "Cisco 7940"; break;
			case Cisco7941: phoneType= "Cisco 7941"; break;
			case Cisco7942: phoneType= "Cisco 7942"; break;
			case Cisco7945: phoneType= "Cisco 7945"; break;
			case Cisco7960: phoneType= "Cisco 7960"; break;
			case Cisco7961: phoneType= "Cisco 7961"; break;
			case Cisco7962: phoneType= "Cisco 7962"; break;
			case Cisco7965: phoneType= "Cisco 7965"; break;
			case Cisco7970: phoneType= "Cisco 7970"; break;
			case Cisco7971: phoneType= "Cisco 7971"; break;
			case Cisco7975: phoneType= "Cisco 7975"; break;
			case Cisco7985: phoneType= "Cisco 7985"; break;
			case Cisco8811: phoneType= "Cisco 8811"; break;
			case Cisco8831: phoneType= "Cisco 8831"; break;
			case Cisco8841: phoneType= "Cisco 8841"; break;
			case Cisco8851: phoneType= "Cisco 8851"; break;
			case Cisco8861: phoneType= "Cisco 8861"; break;
			case Cisco8941: phoneType= "Cisco 8941"; break;
			case Cisco8945: phoneType= "Cisco 8945"; break;
			case Cisco8961: phoneType= "Cisco 8961"; break;
			case Cisco9951: phoneType= "Cisco 9951"; break;
			case Cisco9971: phoneType= "Cisco 9971"; break;
			case Cisco7941GGE: phoneType= "Cisco 7941G-GE"; break;
			case Cisco7961GGE: phoneType= "Cisco 7961G-GE"; break;
			/*case "8851NR,*/
			default: phoneType=null; break;
		}
		return phoneType;
	}
	
	
}

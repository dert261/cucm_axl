package ru.obelisk.cucmaxl.cucm.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cisco.cme.custom.port.CmeXmlHttp;
import com.cisco.cme.custom.xml.AddonItem;
import com.cisco.cme.custom.xml.BlfSpeedDialItem;
import com.cisco.cme.custom.xml.CallForward;
import com.cisco.cme.custom.xml.ExtMapStatus;
import com.cisco.cme.custom.xml.FastdialItem;
import com.cisco.cme.custom.xml.ISDevice;
import com.cisco.cme.custom.xml.ISExtension;
import com.cisco.cme.custom.xml.ISGlobal;
import com.cisco.cme.custom.xml.ISSipDevice;
import com.cisco.cme.custom.xml.ISSipExtension;
import com.cisco.cme.custom.xml.ISSipGlobal;
import com.cisco.cme.custom.xml.ISUrlService;
import com.cisco.cme.custom.xml.ISVoiceHuntGroup;
import com.cisco.cme.custom.xml.Response;
import com.cisco.cme.custom.xml.SpeedDialItem;

import lombok.extern.log4j.Log4j2;
import ru.obelisk.cucmaxl.cucm.service.CmeService;
import ru.obelisk.cucmaxl.utils.ObeliskStringUtils;
import ru.obelisk.cucmaxl.web.databinding.AjaxOperationResult;
import ru.obelisk.database.models.entity.cme.CmeAddonModule;
import ru.obelisk.database.models.entity.cme.CmeBlfSpeedDial;
import ru.obelisk.database.models.entity.cme.CmeCallForward;
import ru.obelisk.database.models.entity.cme.CmeCustomDevice;
import ru.obelisk.database.models.entity.cme.CmeCustomExtension;
import ru.obelisk.database.models.entity.cme.CmeCustomSipDevice;
import ru.obelisk.database.models.entity.cme.CmeCustomSipExtension;
import ru.obelisk.database.models.entity.cme.CmeDevice;
import ru.obelisk.database.models.entity.cme.CmeExtMapStatus;
import ru.obelisk.database.models.entity.cme.CmeExtension;
import ru.obelisk.database.models.entity.cme.CmeFastDial;
import ru.obelisk.database.models.entity.cme.CmeGlobal;
import ru.obelisk.database.models.entity.cme.CmeRouter;
import ru.obelisk.database.models.entity.cme.CmeSipDevice;
import ru.obelisk.database.models.entity.cme.CmeSipExtMapStatus;
import ru.obelisk.database.models.entity.cme.CmeSipExtension;
import ru.obelisk.database.models.entity.cme.CmeSipGlobal;
import ru.obelisk.database.models.entity.cme.CmeSpeedDial;
import ru.obelisk.database.models.entity.cme.CmeUrlService;
import ru.obelisk.database.models.entity.cme.CmeVoiceHuntGroup;
import ru.obelisk.database.models.entity.cme.CmeVoiceHuntGroupNumber;
import ru.obelisk.database.models.entity.enums.CmeRouterSyncStatus;
import ru.obelisk.database.models.entity.enums.CmeUserType;
import ru.obelisk.database.models.service.cme.CmeDeviceService;
import ru.obelisk.database.models.service.cme.CmeExtensionService;
import ru.obelisk.database.models.service.cme.CmeGlobalService;
import ru.obelisk.database.models.service.cme.CmeRouterService;
import ru.obelisk.database.models.service.cme.CmeSipDeviceService;
import ru.obelisk.database.models.service.cme.CmeSipExtensionService;
import ru.obelisk.database.models.service.cme.CmeSipGlobalService;
import ru.obelisk.database.models.service.cme.CmeVoiceHuntGroupService;

@Component
@Log4j2
public class CmeServiceImpl implements CmeService {
	@Autowired private CmeRouterService cmeRouterService;
	
	@Autowired private CmeGlobalService cmeGlobalService;
	@Autowired private CmeSipGlobalService cmeSipGlobalService;
	
	@Autowired private CmeDeviceService cmeDeviceService;
	@Autowired private CmeExtensionService cmeExtensionService;
	
	@Autowired private CmeSipDeviceService cmeSipDeviceService;
	@Autowired private CmeSipExtensionService cmeSipExtensionService;
	
	@Autowired private CmeVoiceHuntGroupService cmeVoiceHuntGroupService;
	
	public AjaxOperationResult importCmeRouter(CmeRouter router){
		synchronized(router) {
			AjaxOperationResult ajaxResult = new AjaxOperationResult();
			ajaxResult.setMessage("All is OK!!!");
			ajaxResult.setStatus(0);
		
			try {
				log.info("Import CME router: {}", router);
				router.setSyncStatus(CmeRouterSyncStatus.SYNC_GLOBAL_STATUS);
				cmeRouterService.edit(router);
				log.info("Get global SCCP information from router: {}", importGlobalState(router));
				
				router.setSyncStatus(CmeRouterSyncStatus.SYNC_SIP_GLOBAL_STATUS);
				cmeRouterService.edit(router);
				log.info("Get global SIP information from router: {}", importSipGlobalState(router));
				
				router.setSyncStatus(CmeRouterSyncStatus.SYNC_SCCP_DEVICES);
				cmeRouterService.edit(router);
				log.info("Get SCCP devices from router: {}", importDevices(router));
				
				router.setSyncStatus(CmeRouterSyncStatus.SYNC_SIP_DEVICES);
				cmeRouterService.edit(router);
				log.info("Get SIP devices from router: {}", importSipDevices(router));
				
				router.setSyncStatus(CmeRouterSyncStatus.SYNC_HUNT_GROUPS);
				cmeRouterService.edit(router);
				log.info("Get voice hunt group from router: {}", importHuntGroups(router));
				
				if(!router.isSync()) router.setSync(true);
			} catch (Exception e) {
				log.warn(ObeliskStringUtils.getTraceToLog(e));
				ajaxResult.setMessage(e.getMessage());
				ajaxResult.setStatus(-1);
			}	
			
			router.setSyncStatus(CmeRouterSyncStatus.NONSYNC);
			router.setLastUpdateTime(LocalDateTime.now());
			cmeRouterService.edit(router);
			return ajaxResult;
		}
	}
	
	
	public Set<CmeVoiceHuntGroup> importHuntGroups(CmeRouter router) throws Exception{
		Response response;
		CmeXmlHttp cme_http = new CmeXmlHttp(router.getIpAddress(), router.getUsername(), router.getPassword());
			
		Set<CmeVoiceHuntGroup> cmeHuntGroups = new HashSet<CmeVoiceHuntGroup>(0);
		
		response = cme_http.getAllVoiceHuntGroups();
		Iterator<ISVoiceHuntGroup> voiceHuntGroupIterator = response.getISVoiceHuntGroups().getISVoiceHuntGroup().iterator();
		while(voiceHuntGroupIterator.hasNext()){
			ISVoiceHuntGroup voiceHuntGroup = voiceHuntGroupIterator.next();
			CmeVoiceHuntGroup cmeVHG = cmeVoiceHuntGroupService.findByPilotNumberAndRouter(voiceHuntGroup.getISVoiceHuntGroupPilotNumber(), router);
			if(cmeVHG == null){
				cmeVHG = new CmeVoiceHuntGroup();
				cmeVHG.setRouter(router);
				cmeVHG.setPilotNumber(voiceHuntGroup.getISVoiceHuntGroupPilotNumber());
			}
			
			
			cmeVHG.setFinalNum(voiceHuntGroup.getISVoiceHuntGroupFinalNum());
			cmeVHG.setGroupID(voiceHuntGroup.getISVoiceHuntGroupID());
			cmeVHG.setHops(voiceHuntGroup.getISVoiceHuntGroupHops());
			cmeVHG.setListSize(voiceHuntGroup.getISVoiceHuntGroupListSize());
			
			int numIndex = 0;
			List<CmeVoiceHuntGroupNumber> cmeVoiceHuntGroupNumbers = new ArrayList<CmeVoiceHuntGroupNumber>();
			Iterator<String> voiceHuntGroupNumberItemIterator = voiceHuntGroup.getISVoiceHuntGroupListNums().getISVoiceHuntGroupListNum().iterator();
			while(voiceHuntGroupNumberItemIterator.hasNext()){
				String number = voiceHuntGroupNumberItemIterator.next();
				CmeVoiceHuntGroupNumber cmeNumber = new CmeVoiceHuntGroupNumber();
				
				if(!cmeVHG.isNew()){
					Iterator<CmeVoiceHuntGroupNumber> cmeVoiceHuntGroupNumberIterator = cmeVHG.getNumbers().iterator();
					while(cmeVoiceHuntGroupNumberIterator.hasNext()){
						CmeVoiceHuntGroupNumber cmeNum = cmeVoiceHuntGroupNumberIterator.next();
						if(cmeNum.getNumber().equals(number)){
							cmeNumber = cmeNum;
						}
					}
				}
				cmeNumber.setIndex(numIndex++);
				cmeNumber.setNumber(number);
				
				cmeNumber.setSccpNumber(cmeExtensionService.findByNumberAndRouter(number, router));
				cmeNumber.setSipNumber(cmeSipExtensionService.findByNumberAndRouter(number, router));
				
				cmeNumber.setVoiceHuntGroup(cmeVHG);
				cmeVoiceHuntGroupNumbers.add(cmeNumber);
			}
			cmeVHG.setNumbers(cmeVoiceHuntGroupNumbers);
							
			cmeVHG.setPilotPeerTag(voiceHuntGroup.getISVoiceHuntGroupPilotPeerTag());
			cmeVHG.setPilotPreference(voiceHuntGroup.getISVoiceHuntGroupPilotPreference());
			cmeVHG.setSecPilotNumber(voiceHuntGroup.getISVoiceHuntGroupSecPilotNumber());
			cmeVHG.setSecPilotPeerTag(voiceHuntGroup.getISVoiceHuntGroupSecPilotPeerTag());
			cmeVHG.setSecPilotPreference(voiceHuntGroup.getISVoiceHuntGroupSecPilotPreference());
			cmeVHG.setTimeout(voiceHuntGroup.getISVoiceHuntGroupTimeout());
			cmeVHG.setType(voiceHuntGroup.getISVoiceHuntGroupType());
			
			if(cmeVHG.isNew()){
				cmeVoiceHuntGroupService.add(cmeVHG);
			} else {
				cmeVoiceHuntGroupService.edit(cmeVHG);
			}	
			
			cmeHuntGroups.add(cmeVHG);
		}
		
		return cmeHuntGroups;
	}
	
	public CmeSipGlobal importSipGlobalState(CmeRouter router) throws Exception {
		CmeSipGlobal cmeSipGlobal = router.getCmeSipGlobal();
		if(cmeSipGlobal==null) cmeSipGlobal = new CmeSipGlobal();
		
		Response response;
		CmeXmlHttp cme_http = new CmeXmlHttp(router.getIpAddress(), router.getUsername(), router.getPassword());
					
		response = cme_http.getGlobalSipSettings();
		ISSipGlobal globalState = response.getISSipGlobal();
		cmeSipGlobal.setAddress(globalState.getISAddress());
		cmeSipGlobal.setCmeRouter(router);
		cmeSipGlobal.setMaxDN(globalState.getISMaxDN());
		cmeSipGlobal.setMaxPool(globalState.getISMaxPool());
		cmeSipGlobal.setMaxRedirect(globalState.getISMaxRedirect());
		cmeSipGlobal.setMode(globalState.getISMode());
		cmeSipGlobal.setPortNumber(globalState.getISPortNumber());
		cmeSipGlobal.setVersion(globalState.getISVersion());
		router.setCmeSipGlobal(cmeSipGlobal);
		cmeSipGlobal.setCmeRouter(router);
		if(cmeSipGlobal.isNew()){
			cmeSipGlobalService.add(cmeSipGlobal);
		} else {
			cmeSipGlobalService.edit(cmeSipGlobal);
		}
				
		return cmeSipGlobal;
	}
	
	public CmeGlobal importGlobalState(CmeRouter router) throws Exception {
		CmeGlobal cmeGlobal = router.getCmeGlobal();
		if(cmeGlobal==null) cmeGlobal = new CmeGlobal();
		
		Response response;
		CmeXmlHttp cme_http = new CmeXmlHttp(router.getIpAddress(), router.getUsername(), router.getPassword());
		
		response = cme_http.getGlobalSettings();
					
		ISGlobal globalState = response.getISGlobal();
		cmeGlobal.setAddress(globalState.getISAddress());
		cmeGlobal.setConfiguredDevice(globalState.getISConfiguredDevice());
		cmeGlobal.setConfiguredExtension(globalState.getISConfiguredExtension());
		cmeGlobal.setDeviceRegistered(globalState.getISDeviceRegistered());
		cmeGlobal.setKeepAliveInterval(globalState.getISKeepAliveInterval());
		cmeGlobal.setMaxConference(globalState.getISMaxConference());
		cmeGlobal.setMaxDN(globalState.getISMaxDN());
		cmeGlobal.setMaxEphone(globalState.getISMaxEphone());
		cmeGlobal.setMaxRedirect(globalState.getISMaxRedirect());
		cmeGlobal.setMode(globalState.getISMode());
		cmeGlobal.setName(globalState.getISName());
		cmeGlobal.setPeakDeviceRegistered(globalState.getISPeakDeviceRegistered());
		cmeGlobal.setPeakDeviceRegisteredTime(globalState.getISPeakDeviceRegisteredTime());
		cmeGlobal.setPortNumber(globalState.getISPortNumber());
		cmeGlobal.setServiceEngine(globalState.getISServiceEngine());
		
		Set<CmeUrlService> cmeUrlServices = new HashSet<CmeUrlService>();
		Iterator<ISUrlService> ISUrlServiceIterator = globalState.getISUrlServices().getISUrlService().iterator();
		while(ISUrlServiceIterator.hasNext()){
			ISUrlService ISurlService = ISUrlServiceIterator.next();
			CmeUrlService urlService = new CmeUrlService();
			urlService.setUrlLink(ISurlService.getISUrlLink());
			urlService.setUrlType(ISurlService.getISUrlType());
			cmeUrlServices.add(urlService);
		}
		
		cmeGlobal.setUrlServices(cmeUrlServices);
		cmeGlobal.setVersion(globalState.getISVersion());
		cmeGlobal.setVoiceMail(globalState.getISVoiceMail());
		
		router.setCmeGlobal(cmeGlobal);
		cmeGlobal.setCmeRouter(router);
		if(cmeGlobal.isNew()){
			cmeGlobalService.add(cmeGlobal);
		} else {
			cmeGlobalService.edit(cmeGlobal);
		}
		return cmeGlobal;
	}
		
	public Set<CmeSipExtension> importSipExtensions(CmeRouter router) throws Exception {
		CmeXmlHttp cme_http = new CmeXmlHttp(router.getIpAddress(), router.getUsername(), router.getPassword());
		Response sipExtensionResponse;
		
		Set<CmeSipExtension> cmeSipExtensions = new HashSet<CmeSipExtension>(0);
				
		sipExtensionResponse = cme_http.getAllSipExtensions();
		
		if(sipExtensionResponse.getISError()!=null){
			log.trace("Error code: {}",sipExtensionResponse.getISError().getISErrorCode());
			log.trace("Error message: {}",sipExtensionResponse.getISError().getISErrorMsg());
			
			Response sipExtensionTagResponse = cme_http.getAllSipExtensionsByTag();
			Iterator<ISSipExtension> sipExtensionTagIterator = sipExtensionTagResponse.getISSipExtensions().getISSipExtension().iterator();
			while(sipExtensionTagIterator.hasNext()){
				Response sipExtensionByTagResponse = cme_http.getSipExtensionById(sipExtensionTagIterator.next().getISVoiceRegDNID());
				ISSipExtension sipExtension = sipExtensionByTagResponse.getISSipExtensions().getISSipExtension().get(0);
				CmeSipExtension cmeSipExtension = getCmeSipExtension(sipExtension, router);
				if(cmeSipExtension!=null) cmeSipExtensions.add(cmeSipExtension);
			}
		} else {
			log.trace("Error code is null");
			Iterator<ISSipExtension> sipExtensionIterator = sipExtensionResponse.getISSipExtensions().getISSipExtension().iterator();
			while(sipExtensionIterator.hasNext()){
				ISSipExtension sipExtension = sipExtensionIterator.next();
				CmeSipExtension cmeSipExtension = getCmeSipExtension(sipExtension, router);
				if(cmeSipExtension!=null) cmeSipExtensions.add(cmeSipExtension);
			}
		}	
				
		return cmeSipExtensions;
	}
	
	public Set<CmeSipDevice> importSipDevices(CmeRouter router) throws Exception {
		CmeXmlHttp cme_http = new CmeXmlHttp(router.getIpAddress(), router.getUsername(), router.getPassword());
		Response sipDeviceResponse;
		Set<CmeSipDevice> cmeSipDevices = new HashSet<CmeSipDevice>(0);
		
		Set<CmeSipExtension> cmeSipExtensions = importSipExtensions(router);
		log.trace("Get SIP extensions from router: {}", cmeSipExtensions);
		
		Map<String, CmeSipExtension> cmeSipExtensionMap = new HashMap<String, CmeSipExtension>();
		Iterator<CmeSipExtension> sipExtensionIterator = cmeSipExtensions.iterator();
		while(sipExtensionIterator.hasNext()){
			CmeSipExtension sipExtension = sipExtensionIterator.next();
			cmeSipExtensionMap.put(sipExtension.getExtCmeID(), sipExtension);
		}
				
		sipDeviceResponse = cme_http.getAllSipDevices();
		
		if(sipDeviceResponse.getISError()!=null){
			log.trace("Error code: {}",sipDeviceResponse.getISError().getISErrorCode());
			log.trace("Error message: {}",sipDeviceResponse.getISError().getISErrorMsg());
			
			Response sipDeviceTagResponse = cme_http.getAllSipDevicesByTag();
			Iterator<ISSipDevice> sipDeviceTagIterator = sipDeviceTagResponse.getISSipDevices().getISSipDevice().iterator();
			while(sipDeviceTagIterator.hasNext()){
				Response sipDeviceByTagResponse = cme_http.getSipDeviceById(sipDeviceTagIterator.next().getISPoolID());
				ISSipDevice sipDevice = sipDeviceByTagResponse.getISSipDevices().getISSipDevice().get(0);
				CmeSipDevice cmeSipDevice = getCmeSipDevice(sipDevice, router, cmeSipExtensionMap);
				cmeSipDevice.setRouter(router);
				cmeSipDevices.add(cmeSipDevice);
			}
		} else {
			log.trace("Error code is null");
			Iterator<ISSipDevice> sipDeviceIterator = sipDeviceResponse.getISSipDevices().getISSipDevice().iterator();
			while(sipDeviceIterator.hasNext()){
				ISSipDevice sipDevice = sipDeviceIterator.next();
				CmeSipDevice cmeSipDevice = getCmeSipDevice(sipDevice, router, cmeSipExtensionMap);
				cmeSipDevice.setRouter(router);
				cmeSipDevices.add(cmeSipDevice);
			}
		}	
		return cmeSipDevices;
	}
	
	public Set<CmeExtension> importExtensions(CmeRouter router) throws Exception {
		CmeXmlHttp cme_http = new CmeXmlHttp(router.getIpAddress(), router.getUsername(), router.getPassword());
		Response extensionResponse;
		
		Set<CmeExtension> cmeExtensions = new HashSet<CmeExtension>(0);
				
		extensionResponse = cme_http.getAllExtensions();
		
		if(extensionResponse!=null && extensionResponse.getISError()!=null){
			log.trace("Error code: {}",extensionResponse.getISError().getISErrorCode());
			log.trace("Error message: {}",extensionResponse.getISError().getISErrorMsg());
			
			Response extensionTagResponse = cme_http.getAllExtensionsByTag();
			Iterator<ISExtension> extensionTagIterator = extensionTagResponse.getISExtensions().getISExtension().iterator();
			while(extensionTagIterator.hasNext()){
				Response extensionByTagResponse = cme_http.getExtensionById(extensionTagIterator.next().getISExtID());
				ISExtension extension = extensionByTagResponse.getISExtensions().getISExtension().get(0);
				CmeExtension cmeExtension = getCmeExtension(extension, router);
				
				if(cmeExtension!=null) cmeExtensions.add(cmeExtension);
			}
		} else {
			log.trace("Error code is null");
			Iterator<ISExtension> extensionIterator = extensionResponse.getISExtensions().getISExtension().iterator();
			while(extensionIterator.hasNext()){
				ISExtension extension = extensionIterator.next();
				CmeExtension cmeExtension = getCmeExtension(extension, router);
				if(cmeExtension!=null) cmeExtensions.add(cmeExtension);
			}
		}	
		return cmeExtensions;
	}
	
	public Set<CmeDevice> importDevices(CmeRouter router) throws Exception {
		CmeXmlHttp cme_http = new CmeXmlHttp(router.getIpAddress(), router.getUsername(), router.getPassword());
		Response deviceResponse;
		Set<CmeDevice> cmeDevices = new HashSet<CmeDevice>(0);
		
		Set<CmeExtension> cmeExtensions = importExtensions(router);
		log.trace("Get SCCP extensions from router: {}", cmeExtensions);
		
		Map<String, CmeExtension> cmeExtensionMap = new HashMap<String, CmeExtension>();
		Iterator<CmeExtension> extensionIterator = cmeExtensions.iterator();
		while(extensionIterator.hasNext()){
			CmeExtension extension = extensionIterator.next();
			cmeExtensionMap.put(extension.getExtCmeID(), extension);
		}
		
		deviceResponse = cme_http.getAllDevices();
		
		if(deviceResponse!=null && deviceResponse.getISError()!=null){
			log.trace("Error code: {}",deviceResponse.getISError().getISErrorCode());
			log.trace("Error message: {}",deviceResponse.getISError().getISErrorMsg());
			
			Response deviceTagResponse = cme_http.getAllDevicesByTag();
			Iterator<ISDevice> deviceTagIterator = deviceTagResponse.getISDevices().getISDevice().iterator();
			while(deviceTagIterator.hasNext()){
				ISDevice tag = deviceTagIterator.next();
				log.trace("Tag ISDevID: {}", tag.getISDevID());
				Response deviceByTagResponse = cme_http.getDeviceById(tag.getISDevID());
				log.trace("DeviceByTagResponse: {}", deviceByTagResponse);
				if(deviceByTagResponse.getISDevices()!=null){
					ISDevice device = deviceByTagResponse.getISDevices().getISDevice().get(0);
					CmeDevice cmeDevice = getCmeDevice(device, router, cmeExtensionMap);
					cmeDevices.add(cmeDevice);
				} else {
					log.trace("DeviceByTagResponse not contain object with this key: {}", tag.getISDevID());
				}
			}
		} else {
			log.trace("Error code is null");
			Iterator<ISDevice> deviceIterator = deviceResponse.getISDevices().getISDevice().iterator();
			while(deviceIterator.hasNext()){
				ISDevice device = deviceIterator.next();
				CmeDevice cmeDevice = getCmeDevice(device, router, cmeExtensionMap);
									
				
				cmeDevices.add(cmeDevice);
			}
		}	
		return cmeDevices;
	}
		
	private CmeDevice getCmeDevice(ISDevice device, CmeRouter router, Map<String, CmeExtension> cmeExtensionMap){
		CmeDevice cmeDevice = cmeDeviceService.findByNameAndRouter(device.getISDevName(), router);
		if(cmeDevice == null){
			cmeDevice = new CmeDevice();
			cmeDevice.setRouter(router);
			cmeDevice.setName(device.getISDevName());
		}
		
		CmeCustomDevice cmeCustomDevice = cmeDevice.getCustomDevice();
		if(cmeCustomDevice==null){
			cmeCustomDevice = new CmeCustomDevice();
			cmeCustomDevice.setCmeDevice(cmeDevice);
			cmeCustomDevice.setEnable(true);
			cmeCustomDevice.setUserType(CmeUserType.ANONYMOUS);
			cmeDevice.setCustomDevice(cmeCustomDevice);
		}
		
		cmeDevice.setAddress(device.getISDevAddr().getXipv4Address());
		cmeDevice.setConfigType(device.getISconfigDevType());
		cmeDevice.setDescription(device.getDescription());
		cmeDevice.setDeviceCmeId(device.getISDevID());
		cmeDevice.setDnd(device.getISDevDND());
		cmeDevice.setLastStatus(device.getISDevLastStatus());
		cmeDevice.setPassword(device.getPassword());
		cmeDevice.setStatus(device.getISDevStatus());
		cmeDevice.setType(device.getISDevType());
				
		List<CmeExtMapStatus> cmeCmeExtMapStatuses = new ArrayList<CmeExtMapStatus>();
		Iterator<ExtMapStatus> extMapStatusIterator = device.getISPhoneLineList().getExtMapStatus().iterator();
		while(extMapStatusIterator.hasNext()){
			ExtMapStatus extMapStatus = extMapStatusIterator.next();
			if(extMapStatus!=null){
				if(cmeExtensionMap.containsKey(extMapStatus.getExtId())){
					CmeExtension cmeExtension = cmeExtensionMap.get(extMapStatus.getExtId());
					
					CmeExtMapStatus cmeExtMapStatus = null;
					if(!cmeExtension.isNew() && !cmeDevice.isNew()){
						Iterator<CmeExtMapStatus> cmeExtMapStatusIterator = cmeDevice.getLines().iterator();
						while(cmeExtMapStatusIterator.hasNext()){
							CmeExtMapStatus mapStat = cmeExtMapStatusIterator.next();
							if(mapStat.getExtension().getId()==cmeExtension.getId()){
								cmeExtMapStatus = mapStat;
							}
						}
					} else {
						cmeExtMapStatus = new CmeExtMapStatus();
					}			
					log.info("cmeExtMapStatus: {}\t\textMapStatus: {}",cmeExtMapStatus,extMapStatus);				
					cmeExtMapStatus.setLineId(extMapStatus.getLineId());
					cmeExtMapStatus.setLineState(extMapStatus.getLineState());
					cmeExtMapStatus.setDevice(cmeDevice);
					cmeExtMapStatus.setExtension(cmeExtension);
					cmeCmeExtMapStatuses.add(cmeExtMapStatus);
				}
			}
		}
		cmeDevice.setLines(cmeCmeExtMapStatuses);
		
		
		
		
		Set<CmeAddonModule> cmeAddonModules = new HashSet<CmeAddonModule>();
		Iterator<AddonItem> addonItemIterator = device.getPhoneType().getAddonList().getAddonItem().iterator();
		while(addonItemIterator.hasNext()){
			AddonItem addonItem = addonItemIterator.next();
			CmeAddonModule cmeAddon = new CmeAddonModule();
			
			if(!cmeDevice.isNew()){
				Iterator<CmeAddonModule> cmeAddonModIterator = cmeDevice.getAddonModules().iterator();
				while(cmeAddonModIterator.hasNext()){
					CmeAddonModule addon = cmeAddonModIterator.next();
					if(addon.getName().equals(addonItem.getAddon())){
						cmeAddon = addon;
					}
				}
			}
			
			
			cmeAddon.setDevice(cmeDevice);
			cmeAddon.setName(addonItem.getAddon());
			cmeAddon.setType(addonItem.getAddonType());
			cmeAddonModules.add(cmeAddon);
		}
		cmeDevice.setAddonModules(cmeAddonModules);
		
		Set<CmeBlfSpeedDial> cmeBlfSpeedDials = new HashSet<CmeBlfSpeedDial>();
		Iterator<BlfSpeedDialItem> blfSdIterator = device.getBlfSpeedDialList().getBlfSpeedDialItem().iterator(); 
		while(blfSdIterator.hasNext()){
			BlfSpeedDialItem blfSdItem = blfSdIterator.next();
			CmeBlfSpeedDial cmeBlfSd = new CmeBlfSpeedDial();
			
			if(!cmeDevice.isNew()){
				Iterator<CmeBlfSpeedDial> cmeBlfSdIterator = cmeDevice.getBlfSpeedDials().iterator();
				while(cmeBlfSdIterator.hasNext()){
					CmeBlfSpeedDial blfSd = cmeBlfSdIterator.next();
					if(blfSd.getNumber().equals(blfSdItem.getPhoneNumber())){
						cmeBlfSd = blfSd;
					}
				}
			}
			
			cmeBlfSd.setDevice(cmeDevice);
			cmeBlfSd.setIndex(blfSdItem.getIndex());
			cmeBlfSd.setLabel(blfSdItem.getLabel());
			cmeBlfSd.setNumber(blfSdItem.getPhoneNumber());
			cmeBlfSpeedDials.add(cmeBlfSd);
		}
		cmeDevice.setBlfSpeedDials(cmeBlfSpeedDials);
		
		Set<CmeSpeedDial> cmeSpeedDials = new HashSet<CmeSpeedDial>();
		Iterator<SpeedDialItem> sdIterator = device.getSpeedDialList().getSpeedDialItem().iterator(); 
		while(sdIterator.hasNext()){
			SpeedDialItem sdItem = sdIterator.next();
			
			CmeSpeedDial cmeSd = new CmeSpeedDial();
			if(!cmeDevice.isNew()){
				Iterator<CmeSpeedDial> cmeSdIterator = cmeDevice.getSpeedDials().iterator();
				while(cmeSdIterator.hasNext()){
					CmeSpeedDial sd = cmeSdIterator.next();
					if(sd.getNumber().equals(sdItem.getPhoneNumber())){
						cmeSd = sd;
					}
				}
			}
						
			cmeSd.setDevice(cmeDevice);
			cmeSd.setIndex(sdItem.getIndex());
			cmeSd.setLabel(sdItem.getLabel());
			cmeSd.setNumber(sdItem.getPhoneNumber());
			cmeSpeedDials.add(cmeSd);
		}
		cmeDevice.setSpeedDials(cmeSpeedDials);
						
		Set<CmeFastDial> cmeFastDials = new HashSet<CmeFastDial>();
		
		Iterator<FastdialItem> fdIterator = device.getFastdialList().getFastdialItem().iterator();  
		while(fdIterator.hasNext()){
			FastdialItem fdItem = fdIterator.next();
			CmeFastDial cmeFd = new CmeFastDial();
			
			if(!cmeDevice.isNew()){
				Iterator<CmeFastDial> cmeFdIterator = cmeDevice.getFastDials().iterator();
				while(cmeFdIterator.hasNext()){
					CmeFastDial fd = cmeFdIterator.next();
					if(fd.getNumber().equals(fdItem.getFastdialNumber())){
						cmeFd = fd;
					}
				}
			}
			
			cmeFd.setDevice(cmeDevice);
			cmeFd.setIndex(fdItem.getFastdial());
			cmeFd.setName(fdItem.getFastdialName());
			cmeFd.setNumber(fdItem.getFastdialNumber());
			cmeFastDials.add(cmeFd);
		}
		cmeDevice.setFastDials(cmeFastDials);
		
		if(cmeDevice.isNew()){
			cmeDeviceService.add(cmeDevice);
		} else {
			cmeDeviceService.edit(cmeDevice);
		}
				
		return cmeDevice;
	}
	
	private CmeSipDevice getCmeSipDevice(ISSipDevice device, CmeRouter router, Map<String, CmeSipExtension> cmeSipExtensionMap){
		CmeSipDevice cmeSipDevice = cmeSipDeviceService.findByNameAndRouter("SEP"+device.getISDevMac().replaceAll("\\.", ""), router);
		if(cmeSipDevice == null){
			cmeSipDevice = new CmeSipDevice();
			cmeSipDevice.setRouter(router);
			cmeSipDevice.setName("SEP"+(device.getISDevMac().replaceAll("\\.", "")));
		}
		
		CmeCustomSipDevice cmeCustomSipDevice = cmeSipDevice.getCustomSipDevice();
		if(cmeCustomSipDevice==null){
			cmeCustomSipDevice = new CmeCustomSipDevice();
			cmeCustomSipDevice.setCmeSipDevice(cmeSipDevice);
			cmeCustomSipDevice.setEnable(true);
			cmeCustomSipDevice.setUserType(CmeUserType.ANONYMOUS);
			cmeCustomSipDevice.setPhoneType(null);
			cmeSipDevice.setCustomSipDevice(cmeCustomSipDevice);
		}
				
		Set<CmeSipExtMapStatus> cmeCmeSipExtMapStatuses = new HashSet<CmeSipExtMapStatus>();
		Iterator<ExtMapStatus> extMapStatusIterator = device.getISSipPhoneLineList().getExtMapStatus().iterator();
		while(extMapStatusIterator.hasNext()){
			ExtMapStatus extMapStatus = extMapStatusIterator.next();
			
			if(cmeSipExtensionMap.containsKey(extMapStatus.getExtId())){
				CmeSipExtension cmeSipExtension = cmeSipExtensionMap.get(extMapStatus.getExtId());
				
				CmeSipExtMapStatus cmeSipExtMapStatus = null;
				if(!cmeSipExtension.isNew() && !cmeSipDevice.isNew()){
					Iterator<CmeSipExtMapStatus> cmeSipExtMapStatusIterator = cmeSipDevice.getLines().iterator();
					while(cmeSipExtMapStatusIterator.hasNext()){
						CmeSipExtMapStatus sipMapStat = cmeSipExtMapStatusIterator.next();
						if(sipMapStat.getExtension().getId()==cmeSipExtension.getId()){
							cmeSipExtMapStatus = sipMapStat;
						}
					}
				} else {
					cmeSipExtMapStatus = new CmeSipExtMapStatus();
				}			
								
				cmeSipExtMapStatus.setLineId(extMapStatus.getLineId());
				cmeSipExtMapStatus.setLineState(extMapStatus.getLineState());
				cmeSipExtMapStatus.setDevice(cmeSipDevice);
				cmeSipExtMapStatus.setExtension(cmeSipExtension);
				cmeCmeSipExtMapStatuses.add(cmeSipExtMapStatus);
			}
		}
		cmeSipDevice.setLines(cmeCmeSipExtMapStatuses);
			
		
		cmeSipDevice.setCodec(device.getISDevCodec());
		cmeSipDevice.setDeviceCmeId(device.getISPoolID());
		cmeSipDevice.setPoolDtmfRelay(device.getISPoolDtmfRelay());
		cmeSipDevice.setPoolMaxRegistration(device.getISPoolMaxRegistration());
		
		if(cmeSipDevice.isNew()){
			cmeSipDeviceService.add(cmeSipDevice);
		} else {
			cmeSipDeviceService.edit(cmeSipDevice);
		}
				
		return cmeSipDevice;
	}
	
	private CmeExtension getCmeExtension(ISExtension extension, CmeRouter router){
		if(extension.getISDevList().getISDeviceID().size()==0) {
			log.info("Extension {} not linked with any device. Extension not saved in local DB.", extension.getISExtNumber());
			return null;
		}
		log.trace("Find extension by number and router. Extension: {}, router_id: {}", extension.getISExtNumber(), router.getId());
		CmeExtension cmeExtension = cmeExtensionService.findByNumberAndRouter(extension.getISExtNumber(), router);
		if(cmeExtension==null){
			cmeExtension = new CmeExtension();
			cmeExtension.setNumber(extension.getISExtNumber());
		}
		
		CmeCustomExtension customExtension = cmeExtension.getCustomExtension();
		if(customExtension==null){
			customExtension = new CmeCustomExtension();
			customExtension.setCmeExtension(cmeExtension);
			customExtension.setEnable(true);
			customExtension.setNumber(cmeExtension.getNumber());
			cmeExtension.setCustomExtension(customExtension);
		}
		
		
		cmeExtension.setCallStatus(extension.getISExtCallStatus());
		cmeExtension.setDescription(extension.getDescription());
		cmeExtension.setExtCmeID(extension.getISExtID());
		cmeExtension.setFirstName(extension.getFirstName());
		cmeExtension.setLabel(extension.getLabel());
		cmeExtension.setLastName(extension.getLastName());
		cmeExtension.setLineMode(extension.getISExtLineMode());
		cmeExtension.setMobility(extension.getMobility());
		cmeExtension.setMultiLines(extension.getISExtMultiLines());
		cmeExtension.setName(extension.getName());
		cmeExtension.setPickupGroup(extension.getPickupGroup());
		cmeExtension.setPortName(extension.getISExtPortName());
		cmeExtension.setSecNumber(extension.getISExtSecNumber());
		cmeExtension.setStatus(extension.getISExtStatus());
		cmeExtension.setType(extension.getISExtType());
		cmeExtension.setUsage(extension.getISExtUsage());
		
		CmeCallForward cmeCallForward = null;
		if(cmeExtension.isNew()){
			cmeCallForward = new CmeCallForward();
		} else {
			cmeCallForward = cmeExtension.getCallForward();
		}
		
		CallForward cfwd = extension.getCallForward();
		cmeCallForward.setCfwdAllNumber(cfwd.getAll().getNumber());
		cmeCallForward.setCfwdBusyNumber(cfwd.getBusy().getNumber());
		cmeCallForward.setCfwdNoanNumber(cfwd.getNoan().getNumber());
		cmeCallForward.setCfwdNoanTimeout(cfwd.getNoan().getTimeout());
		cmeCallForward.setExtension(cmeExtension);
		cmeExtension.setCallForward(cmeCallForward);
		
		log.trace("CmeExtension: {}",cmeExtension);
		
		if(cmeExtension.isNew()){ 
			log.trace("Add CmeExtension: {}",cmeExtension);
			cmeExtensionService.add(cmeExtension);
		} else {
			log.trace("Edit CmeExtension: {}",cmeExtension);
			cmeExtensionService.edit(cmeExtension);
		}	
		return cmeExtension;
	}
	
	private CmeSipExtension getCmeSipExtension(ISSipExtension extension, CmeRouter router){
		if(extension.getISSipDevList().getISPoolID().size()==0) {
			log.info("SIP extension {} not linked with any device. Extension not saved in local DB.", extension.getISExtNumber());
			return null;
		}
		CmeSipExtension cmeSipExtension = cmeSipExtensionService.findByNumberAndRouter(extension.getISExtNumber(), router);
		if(cmeSipExtension==null){
			cmeSipExtension = new CmeSipExtension();
			cmeSipExtension.setNumber(extension.getISExtNumber());
		}
		
		CmeCustomSipExtension customSipExtension = cmeSipExtension.getCustomSipExtension();
		if(customSipExtension==null){
			customSipExtension = new CmeCustomSipExtension();
			customSipExtension.setCmeSipExtension(cmeSipExtension);
			customSipExtension.setEnable(true);
			customSipExtension.setNumber(cmeSipExtension.getNumber());
			cmeSipExtension.setCustomSipExtension(customSipExtension);
		}
		
		cmeSipExtension.setFirstName(extension.getFirstName());
		cmeSipExtension.setLastName(extension.getLastName());
		cmeSipExtension.setNumber(extension.getISExtNumber());
		cmeSipExtension.setExtCmeID(extension.getISVoiceRegDNID());
		
		if(cmeSipExtension.isNew()){ 
			cmeSipExtensionService.add(cmeSipExtension);
		} else {
			cmeSipExtensionService.edit(cmeSipExtension);
		}	
		
		return cmeSipExtension;
	}
	
}

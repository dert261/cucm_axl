package ru.obelisk.cucmaxl.cucm.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.cisco.axl.api._10.AddLineReq;
import com.cisco.axl.api._10.AddPhoneReq;
import com.cisco.axl.api._10.ExecuteSQLQueryReq;
import com.cisco.axl.api._10.ExecuteSQLQueryRes;
import com.cisco.axl.api._10.GetLineReq;
import com.cisco.axl.api._10.GetLineRes;
import com.cisco.axl.api._10.GetPhoneReq;
import com.cisco.axl.api._10.GetPhoneRes;
import com.cisco.axl.api._10.GetUserReq;
import com.cisco.axl.api._10.GetUserRes;
import com.cisco.axl.api._10.RLine;
import com.cisco.axl.api._10.RPhone;
import com.cisco.axl.api._10.RPhoneLine;
import com.cisco.axl.api._10.RUser.AssociatedDevices;
import com.cisco.axl.api._10.UpdateLineReq;
import com.cisco.axl.api._10.UpdatePhoneReq;
import com.cisco.axl.api._10.UpdateUserReq;
import com.cisco.axl.api._10.XAddOnModule;
import com.cisco.axl.api._10.XBusyLampField;
import com.cisco.axl.api._10.XCallForwardAll;
import com.cisco.axl.api._10.XCallForwardBusy;
import com.cisco.axl.api._10.XCallForwardBusyInt;
import com.cisco.axl.api._10.XCallForwardNoAnswer;
import com.cisco.axl.api._10.XCallForwardNoAnswerInt;
import com.cisco.axl.api._10.XDirn;
import com.cisco.axl.api._10.XFkType;
import com.cisco.axl.api._10.XLine;
import com.cisco.axl.api._10.XPhone;
import com.cisco.axl.api._10.XPhoneLine;
import com.cisco.axl.api._10.XSpeeddial;
import com.cisco.axlapiservice.AXLError;
import com.cisco.axlapiservice.AXLPort;

import ru.obelisk.cucmaxl.cucm.repository.AxlPortRepository;
import ru.obelisk.cucmaxl.cucm.service.CucmWithDBService;
import ru.obelisk.cucmaxl.cucm.utils.CucmAddOnModule;
import ru.obelisk.cucmaxl.cucm.utils.CucmBusyLampField;
import ru.obelisk.cucmaxl.cucm.utils.CucmExportDevice;
import ru.obelisk.cucmaxl.cucm.utils.CucmExtension;
import ru.obelisk.cucmaxl.cucm.utils.CucmExportLine;
import ru.obelisk.cucmaxl.cucm.utils.CucmMigrationUtils;
import ru.obelisk.cucmaxl.cucm.utils.CucmRepo;
import ru.obelisk.cucmaxl.cucm.utils.CucmRow;
import ru.obelisk.cucmaxl.cucm.utils.CucmSpeedDial;
import ru.obelisk.cucmaxl.cucm.utils.CucmUtils;
import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.entity.CucmDevice;
import ru.obelisk.cucmaxl.database.models.entity.CucmDeviceLine;
import ru.obelisk.cucmaxl.database.models.entity.CucmLine;
import ru.obelisk.cucmaxl.database.models.entity.User;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeDevice;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeSipDevice;
import ru.obelisk.cucmaxl.database.models.service.CucmDeviceLineService;
import ru.obelisk.cucmaxl.database.models.service.CucmDeviceService;
import ru.obelisk.cucmaxl.database.models.service.CucmLineService;
import ru.obelisk.cucmaxl.database.models.service.UserService;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeDeviceService;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeSipDeviceService;
import ru.obelisk.cucmaxl.utils.ObeliskStringUtils;
import ru.obelisk.cucmaxl.web.controllers.cme.viewtypes.RouterExportDetail;
import ru.obelisk.cucmaxl.web.controllers.utils.CsvChangeNumber;

@Service
public class CucmWithDBServiceImpl implements CucmWithDBService {
	private static Logger logger = LogManager.getLogger(CucmWithDBServiceImpl.class);
	
	@Autowired AxlPortRepository axlPortRepository;
	@Autowired CucmUtils cucmUtils;
	@Autowired CucmDeviceService deviceService;
	@Autowired CmeDeviceService cmeDeviceService;
	@Autowired CmeSipDeviceService cmeSipDeviceService;
	@Autowired UserService userService;
	@Autowired CucmLineService lineService;
	@Autowired CucmDeviceLineService deviceLineService;
	
	@Autowired private CucmMigrationUtils migrationUtils;
	
	private Map<String, CucmDevice> devicesRepo = new HashMap<String, CucmDevice>();
	private Map<String, CucmLine> linesRepo = new HashMap<String, CucmLine>();
	private Map<String, User> usersRepo = new HashMap<String, User>();
		
	/* (non-Javadoc)
	 * @see ru.obelisk.cucmaxl.cucm.service.impl.CucmWithDBService#cucmSQLQuery(ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort)
	 */
	
	@Override
	public void cucmSQLQuery(CucmAxlPort port){
		AXLPort axlPort = axlPortRepository.addAxlPort(port);
				
		ExecuteSQLQueryReq req = new ExecuteSQLQueryReq();
		StringBuilder sqlBuilder = new StringBuilder();
				
		sqlBuilder.append("SELECT"
				+ " device.pkid AS devicePkid,"
				+ " device.name AS deviceName,"
				+ " device.description AS deviceDescription,"
				+ " typeClass.name AS deviceTypeClassName,"
				+ " typeDeviceProtocol.name AS deviceTypeDeviceProtocolName,"
				+ " typeModel.name AS deviceTypeModelName,"
				+ " endUser.pkid AS endUserPkid,"
				+ " endUser.userid AS endUserUserId,"
				+ " endUser.firstname AS endUserFirstName,"
				+ " endUser.lastname AS endUserLastName,"
				+ " endUser.middlename AS endUserMiddleName,"
				+ " endUser.status AS endUserStatus,"
				+ " endUser.uniqueidentifier AS endUserUniqueIdentifier,"
				+ " directoryPluginConfig.connectedldaphost AS endUserConnectedLdapHost,"
				+ " directoryPluginConfig.ldapsynchronizationbase AS endUserLdapSynchronizationBase,"
				+ " devNumPlanMap.numplanindex AS lineIndex,"
				+ " numPlan.pkid AS linePkid,"
				+ " numPlan.dnorpattern AS linePattern,"
				+ " numPlan.description AS lineDescription,"
				+ " routePartition.name AS linePartition"
			+ " FROM device"
				+ " LEFT JOIN TypeProduct AS typeProduct ON typeProduct.enum=device.tkproduct"
				+ " LEFT JOIN TypeModel AS typeModel ON typeModel.enum=device.tkmodel"
				+ " LEFT JOIN TypeDeviceProtocol AS typeDeviceProtocol ON typeDeviceProtocol.enum=device.tkdeviceprotocol"
				+ " LEFT JOIN TypeClass AS typeClass ON typeClass.enum=device.tkclass"	
				+ " LEFT JOIN EndUser AS endUser ON endUser.pkid=device.fkenduser"
				+ " LEFT JOIN DirectoryPluginConfig AS directoryPluginConfig ON directoryPluginConfig.pkid=endUser.fkdirectorypluginconfig"
				+ " LEFT JOIN DeviceNumplanMap AS devNumPlanMap ON device.pkid=devNumPlanMap.fkDevice"
				+ " LEFT JOIN NumPlan AS numPlan ON numPlan.pkid=devNumPlanMap.fkNumPlan"
				+ " LEFT JOIN RoutePartition AS routePartition ON numPlan.fkRoutePartition=routePartition.pkid");
		sqlBuilder.append(" WHERE"
				+ " typeClass.name='Phone'"
				+ " AND typeModel.name IN ("+cucmUtils.modelTypeToString()+")");
		sqlBuilder.append(" ORDER BY device.name, devNumPlanMap.numplanindex");
		
		String sql = sqlBuilder.toString();
		logger.trace(sql);
		req.setSql(sql);
		
		List<CucmRow> cucmTable = new ArrayList<CucmRow>();
		try {
			ExecuteSQLQueryRes res = axlPort.executeSQLQuery(req);
			Iterator<Object> iterator = res.getReturn().getRow().iterator();
			while(iterator.hasNext()){
				Element elem = (Element) iterator.next();
				if(elem!=null){
					CucmRow cucmRow = new CucmRow();
					//logger.info("elem: {}", serializeElement(elem));
					cucmRow.setPort(port);
					cucmRow.setDeviceDescription(getDataFromElementByTagName(elem, "deviceDescription"));
					cucmRow.setDeviceName(getDataFromElementByTagName(elem, "deviceName"));
					cucmRow.setDevicePkid(getDataFromElementByTagName(elem, "devicePkid"));
					cucmRow.setDeviceTypeClassName(getDataFromElementByTagName(elem, "deviceTypeClassName"));
					cucmRow.setDeviceTypeDeviceProtocolName(getDataFromElementByTagName(elem, "deviceTypeDeviceProtocolName"));
					cucmRow.setDeviceTypeModelName(getDataFromElementByTagName(elem, "deviceTypeModelName"));
					cucmRow.setEndUserFirstName(getDataFromElementByTagName(elem, "endUserFirstName"));
					cucmRow.setEndUserLastName(getDataFromElementByTagName(elem, "endUserLastName"));
					cucmRow.setEndUserMiddleName(getDataFromElementByTagName(elem, "endUserMiddleName"));
					cucmRow.setEndUserPkid(getDataFromElementByTagName(elem, "endUserPkid"));
					cucmRow.setEndUserStatus(getDataFromElementByTagName(elem, "endUserStatus"));
					cucmRow.setEndUserUniqueIdentifier(getDataFromElementByTagName(elem, "endUserUniqueIdentifier"));
					cucmRow.setEndUserUserId(getDataFromElementByTagName(elem, "endUserUserId"));
					cucmRow.setEndUserConnectedLdapHost(getDataFromElementByTagName(elem, "endUserConnectedLdapHost"));
					cucmRow.setEndUserLdapSynchronizationBase(getDataFromElementByTagName(elem, "endUserLdapSynchronizationBase"));
					cucmRow.setLineDescription(getDataFromElementByTagName(elem, "lineDescription"));
					cucmRow.setLineIndex(getDataFromElementByTagName(elem, "lineIndex"));
					cucmRow.setLinePartition(getDataFromElementByTagName(elem, "linePartition"));
					cucmRow.setLinePattern(getDataFromElementByTagName(elem, "linePattern"));
					cucmRow.setLinePkid(getDataFromElementByTagName(elem, "linePkid"));
					cucmTable.add(cucmRow);
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(cucmTable.size()==0){
			logger.info("CUCM return 0 elements. Stop processing.");
			return;
		}
		
		devicesRepo.clear();
		linesRepo.clear();
		usersRepo.clear();
		
		devicesRepo = getCucmRepo(deviceService.findAllWithRelational(port));
		linesRepo = getCucmRepo(lineService.getAllLine());
		usersRepo = getCucmUserRepo(userService.getAllUsers());
		
		logger.trace("Size of lines repo: {}",linesRepo.size());
		cucmTable.stream().forEach(n->checkDevice(n));
						
		devicesRepo.values().stream().forEach(device -> {
			if(!device.getLines().containsAll(device.getLinesTemp()) || !device.getLinesTemp().containsAll(device.getLines())){
				device.getLines().clear();
				device.getLines().addAll(device.getLinesTemp());
				device.setChanged(true);
			}
			
			if(device.isNew()){
				logger.info("Add device: {}",device);
				deviceService.addDevice(device);
			} else {
				if(device.isChanged()){
					logger.info("Update device: {}",device);
					device.getLines().forEach(n -> {
							
							logger.trace("DevLine Hash {}: {}", n.hashCode(), n);
							logger.trace("LineHash {}: {}", n.getLine().hashCode(), n.getLine());
							logger.trace("DeviceHash {}: {}", n.getDevice().hashCode(),n.getDevice());
							
					});
					logger.trace("LinesTemp -------------------------");
					device.getLinesTemp().forEach(n -> {
						
						logger.trace("DevLine Hash {}: {}", n.hashCode(), n);
						logger.trace("LineHash {}: {}", n.getLine().hashCode(), n.getLine());
						logger.trace("DeviceHash {}: {}", n.getDevice().hashCode(),n.getDevice());
				});
					deviceService.editDevice(device);
				}	
			}
		});
		
		List<CucmDevice> devices = deviceService.getAllDeviceByCucm(port);
		Iterator<CucmDevice> iteratorDevice = devices.iterator();
		while(iteratorDevice.hasNext()){
			boolean isContain = false; 
			CucmDevice device = iteratorDevice.next();
			Iterator<CucmRow> rowDeviceIterator = cucmTable.iterator();
			while(rowDeviceIterator.hasNext()){
				CucmRow row = rowDeviceIterator.next();
				if(device.getPkid().equals(row.getDevicePkid())){
					isContain = true;
				}	
			}
			if(!isContain){
				logger.info("Delete device: {}", device);
				deviceService.deleteDevice(device.getId());
			}
		}
		
		linesRepo.clear();
		linesRepo = getCucmRepo(lineService.findByPkCucmAxlPort(port));
		
		Iterator<CucmLine> iteratorLine = linesRepo.values().iterator();
		StringBuffer buff = new StringBuffer();
		while(iteratorLine.hasNext()){
			boolean isContain = false; 
			CucmLine line = iteratorLine.next();
			Iterator<CucmRow> rowDeviceIterator = cucmTable.iterator();
			while(rowDeviceIterator.hasNext()){
				CucmRow row = rowDeviceIterator.next();
				if(line.getPkid().equals(row.getLinePkid())){
					isContain = true;
				}	
			}
			if(!isContain){
				CucmLine refreshLine = lineService.findByID(line.getId());
				logger.info("Clear line: {}", refreshLine);
				try{
					refreshLine.getDevices().clear();
					logger.info("Deleted line");
					refreshLine.getDevices().forEach(n->logger.info(n));
					lineService.editLine(refreshLine);
				}catch(Exception e){
					buff.append(refreshLine.getId()+", ");
					Log.info(ObeliskStringUtils.getTraceToLog(e));
				}
				
			}
		}
		logger.info(buff.toString());
	}
	
	private void checkDevice(CucmRow row){
		if(row.getDevicePkid()==null) return;
		
		CucmDevice device = devicesRepo.get(row.getDevicePkid());//deviceService.findByPkID(row.getDevicePkid());
		
		CucmDevice oldDevice = new CucmDevice();
		if(device!=null){
			try {
				BeanUtils.copyProperties(oldDevice, device);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		if(device==null){
			device = new CucmDevice();
			device.setPkid(row.getDevicePkid());
			device.setCucmAxlPort(row.getPort());
			devicesRepo.put(row.getDevicePkid(), device);
		}
		device.setDescription(row.getDeviceDescription());
		device.setModel(row.getDeviceTypeModelName());
		device.setName(row.getDeviceName());
		device.setProtocol(row.getDeviceTypeDeviceProtocolName());
		device.setUserId(getUserByPkID(row));
		
				
		CucmLine line = getLineByPkID(row);
		if(line!=null){
			CucmDeviceLine deviceLine = null;
			if(!device.isNew() && !line.isNew()){
				Iterator<CucmDeviceLine> deviceLineIterator = device.getLines().iterator();
				while(deviceLineIterator.hasNext()){
					CucmDeviceLine devLine = deviceLineIterator.next();
					if(devLine.getLine().getId().equals(line.getId())){
						deviceLine = devLine;
						deviceLine.setLine(line);
					}
				}
										
				if(deviceLine==null){
					deviceLine = new CucmDeviceLine();
					deviceLine.setDevice(device);
					deviceLine.setLine(line);
				}
			} else {	 
				deviceLine = new CucmDeviceLine();
				deviceLine.setDevice(device);
				deviceLine.setLine(line);
			}
			deviceLine.setLineIndex(row.getLineIndex());
			device.getLinesTemp().add(deviceLine);
		}
		
		if(!device.isNew() && !devicesRepo.get(device.getPkid()).myEquals(oldDevice)){
			device.setChanged(true);
			logger.trace("Set Changed to TRUE");
		}
	}
	
	
	private CucmLine getLineByPkID(CucmRow row){
		if(row.getLinePattern()==null) return null;
		
		CucmLine line = linesRepo.get(row.getLinePkid());
		CucmLine oldLine = new CucmLine();
		if(line!=null){
			try {
				BeanUtils.copyProperties(oldLine, line);
			} catch (IllegalAccessException | InvocationTargetException e) {
				ObeliskStringUtils.getTraceToLog(e);
			}
		}
		if(line==null){
			line = new CucmLine();
			line.setPkid(row.getLinePkid());
			linesRepo.put(row.getLinePkid(), line);
		}
		line.setDescription(row.getLineDescription());
		line.setPartition(row.getLinePartition());
		line.setPattern(row.getLinePattern());
			
		if(!line.isNew() && !linesRepo.get(line.getPkid()).myEquals(oldLine)){
			logger.info("Update line: {}",line);
			line = lineService.editLine(line);
		} else if(line.isNew()) {
			logger.info("Add line: {}",line);
			line = lineService.addLine(line);
		}
		
		
		return line;
	}
	
	private <T extends  CucmRepo> Map<String, T> getCucmRepo(List<T> list){
		Map<String, T> repo = new HashMap<String, T>();
		list.stream().forEach(n->repo.put(n.getPkid(), n));
		return repo;
	}
	
	private Map<String, User> getCucmUserRepo(List<User> list){
		Map<String, User> repo = new HashMap<String, User>();
		list.stream().forEach(n->repo.put(n.getAdGuid(), n));
		return repo;
	}
	
	private User getUserByPkID(CucmRow row){
		if(row.getEndUserUniqueIdentifier()==null) return null;
		User user = usersRepo.get(getNormalGUID(row.getEndUserUniqueIdentifier()));
		return user;
	}
	
	private String getNormalGUID(String cucmAdGUID){
		return convertToBindingString(toByteArray(cucmAdGUID));
	}
	
	public static String convertToBindingString(byte[] objectGUID) {
	    StringBuilder displayStr = new StringBuilder();
	    displayStr.append("<GUID=");
	    displayStr.append(convertToDashedString(objectGUID));
	    displayStr.append(">");
	    return displayStr.toString();
	}
	
	private static byte[] toByteArray(String s) {
	    return DatatypeConverter.parseHexBinary(s);
	}
	
	private static String convertToDashedString(byte[] objectGUID) {
	    StringBuilder displayStr = new StringBuilder();

	    displayStr.append(prefixZeros((int) objectGUID[3] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[2] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[1] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[0] & 0xFF));
	    displayStr.append("-");
	    displayStr.append(prefixZeros((int) objectGUID[5] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[4] & 0xFF));
	    displayStr.append("-");
	    displayStr.append(prefixZeros((int) objectGUID[7] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[6] & 0xFF));
	    displayStr.append("-");
	    displayStr.append(prefixZeros((int) objectGUID[8] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[9] & 0xFF));
	    displayStr.append("-");
	    displayStr.append(prefixZeros((int) objectGUID[10] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[11] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[12] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[13] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[14] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[15] & 0xFF));

	    return displayStr.toString();
	}

	private static String prefixZeros(int value) {
	    if (value <= 0xF) {
	        StringBuilder sb = new StringBuilder("0");
	        sb.append(Integer.toHexString(value));

	        return sb.toString();

	    } else {
	        return Integer.toHexString(value);
	    }
	}
	
	
	
	
	private String getDataFromElementByTagName(Element elem, String tagName){
		tagName = tagName.toLowerCase();
		NodeList nodeList = elem.getElementsByTagName(tagName);
		if(nodeList!=null){
			Node node = nodeList.item(0);
			if(node!=null){
				return node.getFirstChild()!=null ? node.getFirstChild().getTextContent() : null; 
			}
		}
		return null;
	}
	
	public final static String serializeElement(Element elem){
		Document document = elem.getOwnerDocument();
		DOMImplementationLS domImplLS = (DOMImplementationLS) document
		    .getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		String str = serializer.writeToString(elem);
		return str;
	}	
	
	
	
	
	private JAXBElement<XFkType> getXFkTypeInJax(String namespace, String value){
		XFkType xfkType = new XFkType();  
		xfkType.setValue(value);  
		JAXBElement<XFkType> jaxbElement = new JAXBElement<XFkType>(new QName(namespace!=null ? namespace: XFkType.class.getSimpleName()), XFkType.class, xfkType);  
	    jaxbElement.setValue(xfkType);    
	    return jaxbElement;
	}
	
	private JAXBElement<String> getStringInJax(String namespace, String value){
		JAXBElement<String> jaxbElement = new JAXBElement<String>(new QName(namespace!=null ? namespace: XFkType.class.getSimpleName()), String.class, value);  
	    jaxbElement.setValue(value);    
	    return jaxbElement;
	}
	
	private XFkType getXFkType(String value){
		XFkType xfkType = new XFkType();  
		xfkType.setValue(value);  
	    return xfkType;
	}
	
	
	private boolean isPhoneOnCUCM(CucmAxlPort port, CucmExportDevice device){
		boolean result = false;
		AXLPort axlPort = axlPortRepository.addAxlPort(port);
		GetPhoneReq phoneReq = new GetPhoneReq();
		phoneReq.setName(device.getName());
		try {
			axlPort.getPhone(phoneReq);
			result = true;
			setExportState(device, false, "Duplicate entry");
		} catch (SOAPFaultException | AXLError e) {
			// TODO Auto-generated catch block
			logger.trace(ObeliskStringUtils.getTraceToLog(e));
		}
		return result;
	}
	
	public void cucmAddPhone(CucmAxlPort port, CucmExportDevice device, RouterExportDetail routerDetail){
		if(device.getLines().size()==0) return;
		
		if(device.getType()==null){
			setExportState(device, false, "Not supported type.");
			return;
		}
		
		if(isPhoneOnCUCM(port, device)) return;
				
		AXLPort axlPort = axlPortRepository.addAxlPort(port);
		String stringDeviceType = migrationUtils.getStringPhoneType(device.getType());
		
		XPhone xphone = new XPhone();
		xphone.setName(device.getName());
		xphone.setDescription(device.getDescription());
		xphone.setClazz("Phone");
				
		xphone.setDevicePoolName(getXFkTypeInJax("devicePoolName", routerDetail.getDevicePool()));
		
		xphone.setCallingSearchSpaceName(getXFkTypeInJax("callingSearchSpaceName",routerDetail.getPhoneCss()));
		xphone.setLocationName(getXFkType("Hub_None"));
		
		
		
		//------------ BIN CUCM PARAMETERS --------------//
		xphone.setPhoneTemplateName(getXFkTypeInJax("phoneTemplateName", migrationUtils.getPhoneButtonTemplate(device.getLines().size(), stringDeviceType, device.getProtocol())));
		xphone.setSoftkeyTemplateName(getXFkTypeInJax("softkeyTemplateName",migrationUtils.getSoftkeyTemplate(stringDeviceType)));
		xphone.setSubscribeCallingSearchSpaceName(getXFkTypeInJax("subscribeCallingSearchSpaceName","BLF_CSS"));
		xphone.setCommonDeviceConfigName(getXFkTypeInJax("commonDeviceConfigName","CDC_StandUser_RU_v4_sampleAudio"));
		//-------------- BIN CUCM PARAMETERS END -----------//
		
		
		
		xphone.setProduct(stringDeviceType);
		xphone.setCommonPhoneConfigName(getXFkType("Standard Common Phone Profile"));
		
		if(device.getUser()!=null){
			xphone.setOwnerUserName(getXFkTypeInJax("ownerUserName",device.getUser().getLogin()));
		}	
		
		xphone.setPresenceGroupName(getXFkType("Standard Presence group"));
		xphone.setSecurityProfileName(getXFkTypeInJax("securityProfileName", migrationUtils.getDeviceSecurityProfile(stringDeviceType, device.getProtocol())));
		
		xphone.setProtocol(device.getProtocol());
		
		xphone.setSipProfileName(getXFkTypeInJax("sipProfileName", migrationUtils.getSIPProfile(stringDeviceType, device.getProtocol(), "")));
		
						
		XPhone.Lines lines = getLines(port, device, routerDetail);
		if(lines != null){
			xphone.setLines(lines);
			
			if(!device.getSpeedDials().isEmpty()){
				xphone.setSpeeddials(getSpeedDials(device));
			}
			
			if(!device.getBlfs().isEmpty()){
				xphone.setBusyLampFields(getBlfs(device));
			}
			
			if(!device.getAddons().isEmpty()){
				xphone.setAddOnModules(getAddons(device));
			}
						
			AddPhoneReq phoneReq = new AddPhoneReq();
			phoneReq.setPhone(xphone);
					
			try {	
				axlPort.addPhone(phoneReq);
							
				if(device.getUser()!=null){ 
					
					GetUserReq getUserReq = new GetUserReq();
					getUserReq.setUserid(device.getUser().getLogin());
					GetUserRes userResponse = axlPort.getUser(getUserReq);
					
					AssociatedDevices assDev = userResponse.getReturn().getUser().getAssociatedDevices();
					if(!assDev.getDevice().contains(device.getName())){
						assDev.getDevice().add(device.getName());
						UpdateUserReq userReq = new UpdateUserReq();
					
						UpdateUserReq.AssociatedDevices assDevUpdate = new UpdateUserReq.AssociatedDevices();
					
						assDevUpdate.getDevice().addAll(assDev.getDevice());
						
						userReq.setAssociatedDevices(assDevUpdate);
					
						userReq.setUserid(device.getUser().getLogin());
						try{
							axlPort.updateUser(userReq);
						}catch (SOAPFaultException | AXLError e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							logger.trace(ObeliskStringUtils.getTraceToLog(e));
						}
					}	
				}	
				
				setExportState(device, true, "OK");
				
			} catch (SOAPFaultException | AXLError e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.trace(ObeliskStringUtils.getTraceToLog(e));
				setExportState(device, false, e.getMessage());
				
			}
		}	
	}
	
	private XPhone.Speeddials getSpeedDials(CucmExportDevice device){
		XPhone.Speeddials xSpeedDials = new XPhone.Speeddials();
				
		Iterator<CucmSpeedDial> speedDialIterator = device.getSpeedDials().iterator();
		while(speedDialIterator.hasNext()){
			CucmSpeedDial sd = speedDialIterator.next();
			
			XSpeeddial xSpeedDial = new XSpeeddial();
			xSpeedDial.setIndex(sd.getIndex());
			xSpeedDial.setDirn(sd.getNumber());
			xSpeedDial.setLabel(sd.getLabel());
			
			xSpeedDials.getSpeeddial().add(xSpeedDial);
		}
		return xSpeedDials;
	}
	
	private XPhone.BusyLampFields getBlfs(CucmExportDevice device){
		XPhone.BusyLampFields xBlfs = new XPhone.BusyLampFields();
				
		Iterator<CucmBusyLampField> blfIterator = device.getBlfs().iterator();
		while(blfIterator.hasNext()){
			CucmBusyLampField blf = blfIterator.next();
			
			XBusyLampField xBlf = new XBusyLampField();
			xBlf.setBlfDirn(blf.getNumber());
			xBlf.setIndex(blf.getIndex());
			xBlf.setLabel(blf.getLabel());
			
			xBlfs.getBusyLampField().add(xBlf);
		}
		return xBlfs;
	}
	
	private XPhone.AddOnModules getAddons(CucmExportDevice device){
		XPhone.AddOnModules xAddons = new XPhone.AddOnModules();
				
		Iterator<CucmAddOnModule> addonsIterator = device.getAddons().iterator();
		while(addonsIterator.hasNext()){
			CucmAddOnModule addon = addonsIterator.next();
			
			XAddOnModule xAddon = new XAddOnModule();
			xAddon.setIndex(addon.getIndex());
			xAddon.setModel(addon.getModel());
			//xAddon.setLoadInformation(value);
						
			xAddons.getAddOnModule().add(xAddon);
		}
		return xAddons;
	}
	
	private void setExportState(CucmExportDevice cucmDevice, boolean state, String stateMessage){
		if(cucmDevice.getProtocol().equals("SCCP")){
			CmeDevice device = cucmDevice.getCmeDevice();
			device.setExported(state);
			device.setExport_message(stateMessage);
			cmeDeviceService.edit(device);
		} else {
			CmeSipDevice device = cucmDevice.getCmeSipDevice();
			device.setExported(state);
			device.setExport_message(stateMessage);
			cmeSipDeviceService.edit(device);
		}
	}
	
	private XPhone.Lines getLines(CucmAxlPort port, CucmExportDevice device, RouterExportDetail routerDetail) {
		XPhone.Lines lines = new XPhone.Lines();
		
		int id = -1;
		Iterator<CucmExportLine> cucmLines = device.getLines().iterator();
		while(cucmLines.hasNext()){
			CucmExportLine cucmLine = cucmLines.next();
			
			XPhoneLine line = new XPhoneLine();
		    line.setIndex(cucmLine.getLineId());
		    
		    line.setDisplay(cucmLine.getDisplay());
		    line.setDisplayAscii(cucmLine.getDisplayascii());
		    line.setE164Mask(getStringInJax("e164Mask", cucmLine.getExternalPhoneNumberMask()));
		    line.setLabel(cucmLine.getDisplay());
		    line.setRecordingMediaSource(cucmLine.getPreferredMediaSource());
		    		    
		    XDirn dirn = getDirn(port, cucmLine.getExtension(), device, routerDetail);
		    if(dirn!=null){
		    	logger.trace("XDirn: {}, {}",dirn.getPattern(), dirn.getRoutePartitionName()!=null ? dirn.getRoutePartitionName().getValue() : "");
		    	line.setDirn(dirn);
			    lines.getLine().add(++id, line);
		    }	
		        
		    
		}
				
	    return lines.getLine().size()>0 ? lines : null;
	}
		
	private XDirn getDirn(CucmAxlPort port, CucmExtension extension, CucmExportDevice device, RouterExportDetail routerDetail) {
		String pattern = extension.getNumber();
		AXLPort axlPort = axlPortRepository.addAxlPort(port);
		
		XDirn dirn = null;
	  				
		GetLineReq lineSearchReq = new GetLineReq();
		lineSearchReq.setPattern(pattern);
		lineSearchReq.setRoutePartitionName(getXFkTypeInJax("routePartitionName", routerDetail.getLinePartition()));
				
		try {
			axlPort.getLine(lineSearchReq);
			dirn = new XDirn();
		    dirn.setPattern(pattern);
		    dirn.setRoutePartitionName(getXFkTypeInJax("routePartitionName", routerDetail.getLinePartition()));
			
		} catch (SOAPFaultException | AXLError e1) {
			logger.trace(ObeliskStringUtils.getTraceToLog(e1));
			XLine line = new XLine();
			line.setPattern(pattern);
			line.setRoutePartitionName(getXFkTypeInJax("routePartitionName", routerDetail.getLinePartition()));
			line.setDescription(extension.getDescription());
			line.setAlertingName(extension.getAlertingname());
			line.setAsciiAlertingName(extension.getAlertingnameascii());
			line.setCallPickupGroupName(getXFkType(extension.getCallPickupGroup()));
			
			line.setShareLineAppearanceCssName(getXFkTypeInJax("shareLineAppearanceCssName", routerDetail.getLineCss()));
			
			XCallForwardAll cfwdall = new XCallForwardAll();
			cfwdall.setCallingSearchSpaceName(getXFkTypeInJax("callingSearchSpaceName", "call_forward_line_cube"));
			cfwdall.setDestination(getStringInJax("destination", extension.getCfadestination()));
			cfwdall.setForwardToVoiceMail("f");
			line.setCallForwardAll(cfwdall);
			
			XCallForwardBusy cfwdbusy = new XCallForwardBusy();
			cfwdbusy.setCallingSearchSpaceName(getXFkTypeInJax("callingSearchSpaceName", "call_forward_line_cube"));
			cfwdbusy.setDestination(getStringInJax("destination", extension.getCfbdestination()));
			cfwdbusy.setForwardToVoiceMail("f");
			XCallForwardBusyInt cfwdbusyInt = new XCallForwardBusyInt();
			cfwdbusyInt.setCallingSearchSpaceName(getXFkTypeInJax("callingSearchSpaceName", "call_forward_line_cube"));
			cfwdbusyInt.setDestination(getStringInJax("destination", extension.getCfbdestination()));
			cfwdbusyInt.setForwardToVoiceMail("f");
			line.setCallForwardBusyInt(cfwdbusyInt);
			
			
			
			logger.warn("CucmExtension: {}", extension);
			if(extension.getCfnadestination()!=null && extension.getCfnadestination().length()>0){
				XCallForwardNoAnswer cfwdNa = new XCallForwardNoAnswer();
				cfwdNa.setCallingSearchSpaceName(getXFkTypeInJax("callingSearchSpaceName", "call_forward_line_cube"));
				cfwdNa.setDestination(getStringInJax("destination", extension.getCfnadestination()));
				cfwdNa.setDuration(getStringInJax("duration", extension.getCfnaduration()));
				cfwdNa.setForwardToVoiceMail("f");
				line.setCallForwardNoAnswer(cfwdNa);
				XCallForwardNoAnswerInt cfwdNaInt = new XCallForwardNoAnswerInt();
				cfwdNaInt.setCallingSearchSpaceName(getXFkTypeInJax("callingSearchSpaceName", "call_forward_line_cube"));
				cfwdNaInt.setDestination(getStringInJax("destination", extension.getCfnadestination()));
				cfwdNaInt.setDuration(getStringInJax("duration", extension.getCfnaduration()));
				cfwdNaInt.setForwardToVoiceMail("f");
				line.setCallForwardNoAnswerInt(cfwdNaInt);
			}
						
			AddLineReq lineReq = new AddLineReq();
			lineReq.setLine(line);
			
			try {
				axlPort.addLine(lineReq);
				dirn = new XDirn();
			    dirn.setPattern(pattern);
			    dirn.setRoutePartitionName(getXFkTypeInJax("routePartitionName", routerDetail.getLinePartition()));
			} catch (SOAPFaultException | AXLError e) {
				logger.trace(ObeliskStringUtils.getTraceToLog(e));
				setExportState(device, false, e.getMessage());
			}
		
		}
		
		return dirn;
	}
	//----------------------------------------*** CHANGE DIRECTORY NUMBER ***-------------------------------------------------//
	public CsvChangeNumber changeDirectoryNumber(CucmAxlPort port, CsvChangeNumber number, String partition){
		AXLPort axlPort = axlPortRepository.addAxlPort(port);
		try {
			GetLineReq lineSearchReq = new GetLineReq();
			lineSearchReq.setPattern(number.getOldNumber());
			lineSearchReq.setRoutePartitionName(getXFkTypeInJax("routePartitionName", partition));
			
			GetLineRes lineRes = axlPort.getLine(lineSearchReq);
			RLine rline = lineRes.getReturn().getLine();
			List<String> devices = rline.getAssociatedDevices().getDevice();
			Iterator<String> devIterator = devices.iterator();
			while(devIterator.hasNext()){
				String deviceName = devIterator.next();
								
				RPhone phone = getPhoneByName(axlPort,deviceName);
				if(phone!=null){
					UpdatePhoneReq updPhoneReq = new UpdatePhoneReq();
					updPhoneReq.setName(deviceName);
					updPhoneReq.setDescription(phone.getDescription().replaceAll(number.getOldNumber(), number.getNewNumber()));
										
					List<XPhoneLine> lines = getXPhoneLinesFromRPhone(phone, number);
					UpdatePhoneReq.Lines updLines = new UpdatePhoneReq.Lines();
					updLines.getLine().addAll(lines);
					updPhoneReq.setLines(updLines);
					
					axlPort.updatePhone(updPhoneReq);
				}
				
			}
						
			UpdateLineReq updateLineReq = new UpdateLineReq();
			updateLineReq.setPattern(number.getOldNumber());
			updateLineReq.setNewPattern(number.getNewNumber());
			updateLineReq.setRoutePartitionName(getXFkTypeInJax("routePartitionName", partition));
			updateLineReq.setAlertingName(rline.getAlertingName().replaceAll(number.getOldNumber(), number.getNewNumber()));
			updateLineReq.setAsciiAlertingName(rline.getAsciiAlertingName().replaceAll(number.getOldNumber(), number.getNewNumber()));
			updateLineReq.setDescription(rline.getDescription().replaceAll(number.getOldNumber(), number.getNewNumber()));
			axlPort.updateLine(updateLineReq);
			
			number.setMessage("OK");
			number.setState(1);
				
		} catch (SOAPFaultException | AXLError e1) {
			logger.trace(ObeliskStringUtils.getTraceToLog(e1));
			number.setMessage(e1.getMessage());
			number.setState(-1);
		}
		
		
		return number;
	}
	
	private List<XPhoneLine> getXPhoneLinesFromRPhone(RPhone phone, CsvChangeNumber number){
		
		List<XPhoneLine> xPhoneLinesList = new ArrayList<XPhoneLine>();
		Iterator<RPhoneLine> phoneLinesIterator = phone.getLines().getLine().iterator();
		while(phoneLinesIterator.hasNext()){
			RPhoneLine phoneLine = phoneLinesIterator.next();
			XPhoneLine xPhoneLine = new XPhoneLine();
			
			XDirn xDirn = new XDirn();
			xDirn.setUuid(phoneLine.getDirn().getUuid());
			xDirn.setPattern(phoneLine.getDirn().getPattern());
			xDirn.setRoutePartitionName(getXFkTypeInJax("routePartitionName", phoneLine.getDirn().getRoutePartitionName().getValue()));
			xPhoneLine.setDirn(xDirn);
			
			xPhoneLine.setIndex(phoneLine.getIndex());
			
			xPhoneLine.setLabel(phoneLine.getLabel().replaceAll(number.getOldNumber(), number.getNewNumber()));
			xPhoneLine.setDisplay(phoneLine.getDisplay().replaceAll(number.getOldNumber(), number.getNewNumber()));
			xPhoneLine.setDisplayAscii(phoneLine.getDisplayAscii().replaceAll(number.getOldNumber(), number.getNewNumber()));
						
			xPhoneLinesList.add(xPhoneLine);
		}
		return xPhoneLinesList;
	}
	
	private RPhone getPhoneByName(AXLPort axlPort, String device){
		GetPhoneReq phoneReq = new GetPhoneReq();
		phoneReq.setName(device);
		RPhone ret = null;
		try {
			GetPhoneRes responce = axlPort.getPhone(phoneReq);
			ret =  responce.getReturn().getPhone();
		} catch (SOAPFaultException | AXLError e) {
			// TODO Auto-generated catch block
			logger.trace(ObeliskStringUtils.getTraceToLog(e));
		}
		return ret;
	}
}

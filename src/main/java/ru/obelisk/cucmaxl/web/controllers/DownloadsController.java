package ru.obelisk.cucmaxl.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import ru.obelisk.database.models.entity.CucmAxlPort;
import ru.obelisk.database.models.entity.CucmDevice;
import ru.obelisk.database.models.entity.CucmDeviceLine;
import ru.obelisk.database.models.entity.LdapDirSyncParameters;
import ru.obelisk.database.models.entity.User;
import ru.obelisk.database.models.entity.enums.PhoneBookSyncSource;
import ru.obelisk.database.models.service.LdapDirSyncParametersService;
import ru.obelisk.database.models.service.UserService;
import ru.obelisk.database.models.views.CucmToADSync;
import ru.obelisk.database.models.views.ListCucmToADSync;

@Controller
@RequestMapping("/downloads")
public class DownloadsController {
	private static Logger logger = LogManager.getLogger(DownloadsController.class);
	
	@Autowired private UserService userService;
	@Autowired private LdapDirSyncParametersService ldapDirSyncParametersService;
			
	@Value("${download.operations.logs.directory}") private String OPEARATION_LOGS_DIRECTORY;
	@Value("${download.buffer.size}") private int BUFFER_SIZE = 4096;
	
	Map<Integer,Set<String>> partitionFilterRepo = new HashMap<Integer,Set<String>>(0);
	
	@RequestMapping(value = {"/downloaddir/{id}"}, method = RequestMethod.GET)
	public void downloadDirByCucmAxlPort(@PathVariable(value = "id") int id, SessionStatus status, HttpServletResponse response) {
		logger.info("Requesting download dir by LDAP directory source");
		LdapDirSyncParameters ldapDir = ldapDirSyncParametersService.findById(id);
		List<User> users = userService.findAllForADSyncByLdapSource(ldapDir);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ListCucmToADSync.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			ListCucmToADSync xmlUsers = getUsersPhonesList(users);
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(xmlUsers, new StreamResult(response.getOutputStream()));
			response.setContentType("application/xml");      
		    response.setHeader("Content-Disposition", "attachment; filename="+ldapDir.getFqdnName()+".xml"); 
		    response.flushBuffer();
		} catch (IOException ex) {
			logger.info("Error writing file to output stream.", ex);
		    throw new RuntimeException("IOError writing file to output stream");
		} catch (JAXBException e) {
			logger.info("Error marshalling to output stream.", e);
		    throw new RuntimeException("JAXBException marshalling to output stream");
		}
	}
	
	@RequestMapping(value = {"/downloaddir/fqdn/{fqdn}"}, method = RequestMethod.GET)
	public void downloadDirByFQDN(@PathVariable(value = "fqdn") String fqdn, SessionStatus status, HttpServletResponse response) {
		logger.info("Requesting download dir by FQDN source");
		List<User> users = userService.findAllForADSyncByFqdn(fqdn);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ListCucmToADSync.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			ListCucmToADSync xmlUsers = getUsersPhonesList(users);
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(xmlUsers, new StreamResult(response.getOutputStream()));
			response.setContentType("application/xml");      
		    response.setHeader("Content-Disposition", "attachment; filename="+fqdn+".xml"); 
		    response.flushBuffer();
		} catch (IOException ex) {
			logger.info("Error writing file to output stream.", ex);
		    throw new RuntimeException("IOError writing file to output stream");
		} catch (JAXBException e) {
			logger.info("Error marshalling to output stream.", e);
		    throw new RuntimeException("JAXBException marshalling to output stream");
		}
	}
	
	@RequestMapping(value = {"/operation/logs/{filename}"}, method = RequestMethod.GET)
	public void downloadOperationLog(@PathVariable(value = "filename") String filename, SessionStatus status, 
				HttpServletRequest request, HttpServletResponse response) {
		logger.info("Requesting download opearation log");
		try {
			
			ServletContext context = request.getServletContext();
	        				    
		 // construct the complete absolute path of the file
	        String fullPath = OPEARATION_LOGS_DIRECTORY + "/" + filename+".txt";
	        
	        File downloadFile = new File(fullPath);
	        FileInputStream inputStream = new FileInputStream(downloadFile);
	     	      	        
	     // get MIME type of the file
	        String mimeType = context.getMimeType(fullPath);
	        if (mimeType == null) {
	            mimeType = "application/octet-stream";
	        }
	        //System.out.println("MIME type: " + mimeType);
	 
	        // set content attributes for the response
	        response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());
	        
	        // set headers for the response
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	        	        
	        // get output stream of the response
	        OutputStream outStream = response.getOutputStream();
	 
	        byte[] buffer = new byte[BUFFER_SIZE];
	        int bytesRead = -1;
	 
	        // write bytes read from the input stream into the output stream
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	 
	        inputStream.close();
	        outStream.close();
		
		} catch (IOException ex) {
			logger.info("Error writing file to output stream.", ex);
		    throw new RuntimeException("IOError writing file to output stream");
		}
	}
	
	private ListCucmToADSync getUsersPhonesList(List<User> users){
		partitionFilterRepo.clear();
		ListCucmToADSync resultList = new ListCucmToADSync();
		Iterator<User> usersIterator = users.iterator();
		while(usersIterator.hasNext()){
			CucmToADSync xmlUser = new CucmToADSync();
			User user = usersIterator.next();
			xmlUser.setFqdnName(user.getLdapDirSyncParameters().getFqdnName());
			xmlUser.setUserId(user.getLogin());
			
			if(user.getLdapDirSyncParameters().getPhoneBookSyncSource()==PhoneBookSyncSource.LDAPDIR){
				xmlUser.setPhones(user.getTelephoneNumber());
			}
			else {
				if(user.getPhoneBook()==null || !user.getPhoneBook().isUseCustomPhone()){
					List<String> userPhones = new ArrayList<String>();	
					Iterator<CucmDevice> deviceIterator = user.getDevices().stream().sorted( (left, right) -> left.getPkid().compareTo(right.getPkid()) ).iterator();
					while(deviceIterator.hasNext()){
						CucmDevice device = deviceIterator.next();
						
						Set<String> partitionsFilter = getFilterByCucmAxlPort(device.getCucmAxlPort());   
												
						List<CucmDeviceLine> deviceLines = new ArrayList<CucmDeviceLine>(device.getLines());
						Collections.sort(deviceLines); 
						Iterator<CucmDeviceLine> deviceLinesIterator = deviceLines.iterator();
						while(deviceLinesIterator.hasNext()){
							CucmDeviceLine deviceLine = deviceLinesIterator.next();
							if(deviceLine.getLine()!=null && deviceLine.getLine().getPattern().length()>0)
								if(partitionsFilter!=null){
									if(partitionsFilter.contains(deviceLine.getLine().getPartition())){			//If partition in partition filter setted on cucm Axl Port 
										if(!userPhones.contains(deviceLine.getLine().getPattern())){
											userPhones.add(deviceLine.getLine().getPattern());
										}
									}
								} else {
									if(!userPhones.contains(deviceLine.getLine().getPattern())){
										userPhones.add(deviceLine.getLine().getPattern());
									}
								}
								
						}
					}
					StringBuilder phones = new StringBuilder();
					Iterator<String> patternIterator = userPhones.stream().sorted((left, right) -> {
								int result = Integer.parseInt(left)-Integer.parseInt(right);
								if(result != 0) return (int) result / Math.abs( result );
								return 0;
							}).iterator();
					while(patternIterator.hasNext()){
						phones.append(patternIterator.next());
						phones.append(patternIterator.hasNext() ? "," : "");
					}
					xmlUser.setPhones(phones.toString());
				} else if(user.getPhoneBook().isUseCustomPhone()) {
					xmlUser.setPhones(user.getPhoneBook().getPhone()!=null ? user.getPhoneBook().getPhone() : "");
				} else {
					xmlUser.setPhones("");
				}
			}
			resultList.getUser().add(xmlUser);
		}
		return resultList;
	}
	
	private Set<String> getFilterByCucmAxlPort(CucmAxlPort cucmAxlPort){
		Set<String> filter = partitionFilterRepo.get(cucmAxlPort.getId());
		if(filter==null){
			if(cucmAxlPort!= null && cucmAxlPort.getPartitionFilter()!=null){
				String [] partitions = cucmAxlPort.getPartitionFilter().getPartitions().split(",");
				filter = new HashSet<String>();
				for(String partition : partitions){
					filter.add(partition.trim());
				}
				partitionFilterRepo.put(cucmAxlPort.getId(), filter);
			}
		}	
		
		return filter;
	}
	
	/*@RequestMapping(value = {"/downloaddir/{id}"}, method = RequestMethod.GET)
	public void downloadDirByCucmAxlPort(@PathVariable(value = "id") int id, SessionStatus status, HttpServletResponse response) {
		logger.info("Requesting download dir by CUCM AXL port");
		CucmAxlPort axlPort = cucmAxlPortService.getCucmAxlPortById(id);
		List<EndUser> users = endUserService.findAllForADSyncByAxlSource(axlPort);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ListCucmToADSync.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			ListCucmToADSync xmlUsers = getUsersPhonesList(users);
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(xmlUsers, new StreamResult(response.getOutputStream()));
			response.setContentType("application/xml");      
		    response.setHeader("Content-Disposition", "attachment; filename="+axlPort.getFqdnName()+".xml"); 
		    response.flushBuffer();
		} catch (IOException ex) {
			logger.info("Error writing file to output stream.", ex);
		    throw new RuntimeException("IOError writing file to output stream");
		} catch (JAXBException e) {
			logger.info("Error marshalling to output stream.", e);
		    throw new RuntimeException("JAXBException marshalling to output stream");
		}
	}*/
	
	/*@RequestMapping(value = {"/downloaddir/fqdn/{fqdn}"}, method = RequestMethod.GET)
	public void downloadDirByFQDN(@PathVariable(value = "fqdn") String fqdn, SessionStatus status, HttpServletResponse response) {
		logger.info("Requesting download dir by FQDN");
		List<EndUser> users = endUserService.findAllForADSyncByFQDN(fqdn);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ListCucmToADSync.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			ListCucmToADSync xmlUsers = null; //getUsersPhonesList(users);
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(xmlUsers, new StreamResult(response.getOutputStream()));
			response.setContentType("application/xml");      
		    response.setHeader("Content-Disposition", "attachment; filename="+fqdn+".xml"); 
		    response.flushBuffer();
		} catch (IOException ex) {
			logger.info("Error writing file to output stream.", ex);
		    throw new RuntimeException("IOError writing file to output stream");
		} catch (JAXBException e) {
			logger.info("Error marshalling to output stream.", e);
		    throw new RuntimeException("JAXBException marshalling to output stream");
		}
	}
	
	/*private ListCucmToADSync getUsersPhonesList(List<EndUser> users){
		ListCucmToADSync resultList = new ListCucmToADSync();
		
		Iterator<EndUser> endUsersIterator = users.iterator();
		while(endUsersIterator.hasNext()){
			CucmToADSync xmlUser = new CucmToADSync();
			EndUser endUser = endUsersIterator.next();
			xmlUser.setFqdnName(endUser.getCucmAxlPort().getFqdnName());
			xmlUser.setUserId(endUser.getUserId());
			
			if(endUser.getPhoneBook()==null || !endUser.getPhoneBook().isUseCustomPhone()){
				List<String> userPhones = new ArrayList<String>();	
				Iterator<Device> deviceIterator = endUser.getDevices().iterator();
				while(deviceIterator.hasNext()){
					Device device = deviceIterator.next();
					
					List<DeviceLine> deviceLines = new ArrayList<DeviceLine>(device.getLines());
					Collections.sort(deviceLines); 
					Iterator<DeviceLine> deviceLinesIterator = deviceLines.iterator();
					while(deviceLinesIterator.hasNext()){
						DeviceLine deviceLine = deviceLinesIterator.next();
						if(deviceLine.getLine()!=null && deviceLine.getLine().getPattern().length()>0)
							if(!userPhones.contains(deviceLine.getLine().getPattern()))
								userPhones.add(deviceLine.getLine().getPattern());
					}
				}
				StringBuilder phones = new StringBuilder();
				Iterator<String> patternIterator = userPhones.iterator();
				while(patternIterator.hasNext()){
					phones.append(patternIterator.next());
					phones.append(patternIterator.hasNext() ? "," : "");
				}
				xmlUser.setPhones(phones.toString());
			} else if(endUser.getPhoneBook().isUseCustomPhone()) {
				xmlUser.setPhones(endUser.getPhoneBook().getPhone()!=null ? endUser.getPhoneBook().getPhone() : "");
			} else {
				xmlUser.setPhones("");
			}
			resultList.getUser().add(xmlUser);
		}
		return resultList;
	}*/
}

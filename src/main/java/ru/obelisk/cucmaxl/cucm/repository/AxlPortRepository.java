package ru.obelisk.cucmaxl.cucm.repository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cisco.axlapiservice.AXLAPIService;
import com.cisco.axlapiservice.AXLPort;

import ru.obelisk.database.models.entity.CucmAxlPort;


@Component
public class AxlPortRepository {
	private static Logger logger = LogManager.getLogger(AxlPortRepository.class);
	
	@Value("${cucm.axl.service.schema.location}") private String schemaLocation;
	
	private Map<String, AXLPort> axlPorts = new HashMap<String, AXLPort>(0); 
		
	public AXLPort addAxlPort(CucmAxlPort port){
		if(axlPorts.containsKey(port.getAxlUrl())) return getAxlPort(port);
		AXLPort axlPort = initAxlPort(port);
		if(axlPort!=null){
			logger.info("Insert AXL port in repo: {}", port);
			axlPorts.put(port.getAxlUrl(), axlPort);
		}
		return axlPort;
	}
	
	public AXLPort getAxlPort(CucmAxlPort port){
		AXLPort axlPort = null;
		if(axlPorts.containsKey(port.getAxlUrl())) 
			axlPort=axlPorts.get(port.getAxlUrl());
		return axlPort;
	}
	
	public void deleteAxlPort(CucmAxlPort port){
		logger.info("Remove AXL port from repo: {}", port);
		axlPorts.remove(port.getAxlUrl());
	}
	
	private AXLPort initAxlPort(CucmAxlPort port){
		logger.info("Init AXL port for {}", port);	
		AXLAPIService axlService = null;
		AXLPort axlPort = null;
		try {
			axlService = new AXLAPIService(new URL(schemaLocation));
			axlPort = axlService.getAXLPort();
			 
			((BindingProvider) axlPort).getRequestContext().put(
			    BindingProvider.ENDPOINT_ADDRESS_PROPERTY, port.getAxlUrl());
			((BindingProvider) axlPort).getRequestContext().put(
			    BindingProvider.USERNAME_PROPERTY, port.getAxlUser());
			((BindingProvider) axlPort).getRequestContext().put(
			    BindingProvider.PASSWORD_PROPERTY, port.getAxlPassword());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return axlPort;
	}
}

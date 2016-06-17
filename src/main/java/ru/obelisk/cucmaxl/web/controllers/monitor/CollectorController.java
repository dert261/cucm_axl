package ru.obelisk.cucmaxl.web.controllers.monitor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;
import ru.obelisk.cucmaxl.ftp.FtpCamelRun;
import ru.obelisk.cucmaxl.web.databinding.AjaxOperationResult;
import ru.obelisk.database.models.entity.Collector;
import ru.obelisk.database.models.entity.CollectorFtpConfig;
import ru.obelisk.database.models.entity.CucmAxlPort;
import ru.obelisk.database.models.entity.enums.CollectorResourceSourceType;
import ru.obelisk.database.models.entity.enums.CollectorType;
import ru.obelisk.database.models.entity.enums.CurrentRunStatus;
import ru.obelisk.database.models.entity.enums.FtpType;
import ru.obelisk.database.models.entity.enums.LaunchModeType;
import ru.obelisk.database.models.service.CollectorService;
import ru.obelisk.database.models.service.CucmAxlPortService;
import ru.obelisk.database.models.views.View;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

@Controller
@RequestMapping("/monitor/collectors")
@Log4j2
public class CollectorController {
	
	@Autowired private CollectorService collectorService;
	@Autowired private CucmAxlPortService axlPortService;
	
	@Autowired private FtpCamelRun ftpCamelRun;
		
	@JsonView(value={View.Collector.class})
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String indexPage(Model model) {
		log.info("Requesting collectors page");
		model.addAttribute("collectorLaunchModeMap", Arrays.asList(LaunchModeType.values()).stream().collect(Collectors.toMap(LaunchModeType::name,LaunchModeType::name)));
		
		return "monitor/collectors/index";
	}
		
	@JsonView(value={View.Collector.class})
	@RequestMapping(value = "/ajax/serverside/collectors.json", method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public @ResponseBody DataTablesOutput<Collector> getCollectors(@Valid DataTablesInput input) {
		DataTablesOutput<Collector> output = collectorService.findAllWithRelations(input);
		output.setData(idGenerate(output.getData(),input.getStart()));
		return output;
	}
		
	private List<Collector> idGenerate(List<Collector> files, int start){
		for(int i=0;i<files.size();i++){
			files.get(i).setNumberLocalized(start+i+1);
		}
		return files;
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewCreateCollectorPage(Model model) {
		log.info("Requesting new collector page");
		Collector collector = new Collector();
		CollectorFtpConfig collectorFtpConfig = new CollectorFtpConfig();
		collectorFtpConfig.setCollector(collector);
		collector.setCollectorFtpConfig(collectorFtpConfig);
		model.addAttribute("collector", collector);
		model.addAttribute("collectorTypes", Arrays.asList(CollectorType.values()));
		model.addAttribute("ftpTypes", Arrays.asList(FtpType.values()));
		
		model.addAttribute("collectorResourceSourceTypes", Arrays.asList(CollectorResourceSourceType.values()));
		
		
		Map<Integer, String> axlPortsMap = new HashMap<Integer, String>();
		List<CucmAxlPort> cucmAxlPorts = axlPortService.findAll();
		Iterator<CucmAxlPort> cucmAxlPortsIterator = cucmAxlPorts.iterator();
		while(cucmAxlPortsIterator.hasNext()){
			CucmAxlPort cucmAxlPort = cucmAxlPortsIterator.next();
			axlPortsMap.put(cucmAxlPort.getId(), cucmAxlPort.getName()+" ("+cucmAxlPort.getDescription()+")");
		}
		model.addAttribute("axlPortsMap", axlPortsMap);
		
		return "monitor/collectors/create";
	}
		
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public String saveCreateCollectorPage(ModelMap model, 
									@Valid @ModelAttribute("collector") final Collector collector, 
									final BindingResult bindingResult) {
		log.info("Requesting save new coollector method");
		
		if(bindingResult.hasErrors()){
			model.addAttribute("collectorTypes", Arrays.asList(CollectorType.values()));
			model.addAttribute("ftpTypes", Arrays.asList(FtpType.values()));
			model.addAttribute("collectorResourceSourceTypes", Arrays.asList(CollectorResourceSourceType.values()));
			Map<Integer, String> axlPortsMap = new HashMap<Integer, String>();
			List<CucmAxlPort> cucmAxlPorts = axlPortService.findAll();
			Iterator<CucmAxlPort> cucmAxlPortsIterator = cucmAxlPorts.iterator();
			while(cucmAxlPortsIterator.hasNext()){
				CucmAxlPort cucmAxlPort = cucmAxlPortsIterator.next();
				axlPortsMap.put(cucmAxlPort.getId(), cucmAxlPort.getName()+" ("+cucmAxlPort.getDescription()+")");
			}
			model.addAttribute("axlPortsMap", axlPortsMap);
			
			return "monitor/collectors/create";
		}
		
		CollectorFtpConfig ftpConfig = collector.getCollectorFtpConfig();
		if(ftpConfig!=null){
			ftpConfig.setCollector(collector);
		}
		
		collectorService.add(collector);
		return "redirect:/monitor/collectors/";
		
	}
	
	@RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewUpdateCollectorPage(ModelMap model, @PathVariable(value = "id") int id) {
		log.info("Requesting update collector page");
		Collector collector = collectorService.findById(id);
		model.addAttribute("collector", collector);
		log.info(collector);
		model.addAttribute("collectorTypes", Arrays.asList(CollectorType.values()));
		model.addAttribute("ftpTypes", Arrays.asList(FtpType.values()));
		model.addAttribute("collectorResourceSourceTypes", Arrays.asList(CollectorResourceSourceType.values()));
		
		Map<Integer, String> axlPortsMap = new HashMap<Integer, String>();
		List<CucmAxlPort> cucmAxlPorts = axlPortService.findAll();
		Iterator<CucmAxlPort> cucmAxlPortsIterator = cucmAxlPorts.iterator();
		while(cucmAxlPortsIterator.hasNext()){
			CucmAxlPort cucmAxlPort = cucmAxlPortsIterator.next();
			axlPortsMap.put(cucmAxlPort.getId(), cucmAxlPort.getName()+" ("+cucmAxlPort.getDescription()+")");
		}
		model.addAttribute("axlPortsMap", axlPortsMap);
		return "monitor/collectors/update";
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.PUT)
	@Secured("ROLE_ADMIN")
	public String saveUpdateCollectorPage(ModelMap model, 
									@Valid @ModelAttribute("collector") final Collector collector, 
									final BindingResult bindingResult) {
		log.info("Requesting save update collector method");
		if(bindingResult.hasErrors()){
			
			model.addAttribute("collectorTypes", Arrays.asList(CollectorType.values()));
			model.addAttribute("ftpTypes", Arrays.asList(FtpType.values()));
			model.addAttribute("collectorResourceSourceTypes", Arrays.asList(CollectorResourceSourceType.values()));
			Map<Integer, String> axlPortsMap = new HashMap<Integer, String>();
			List<CucmAxlPort> cucmAxlPorts = axlPortService.findAll();
			Iterator<CucmAxlPort> cucmAxlPortsIterator = cucmAxlPorts.iterator();
			while(cucmAxlPortsIterator.hasNext()){
				CucmAxlPort cucmAxlPort = cucmAxlPortsIterator.next();
				axlPortsMap.put(cucmAxlPort.getId(), cucmAxlPort.getName()+" ("+cucmAxlPort.getDescription()+")");
			}
			model.addAttribute("axlPortsMap", axlPortsMap);
			
			return "monitor/collectors/update";
		}
		
		log.info(collector);
		
		CollectorFtpConfig ftpConfig = collector.getCollectorFtpConfig();
		if(ftpConfig!=null){
			ftpConfig.setCollector(collector);
		}
		collectorService.edit(collector);
		return "redirect:/monitor/collectors/";
	}
	
	@JsonView(value={View.Collector.class})
	@RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
	@Secured("ROLE_ADMIN")
	public String deleteCollector(int id) {
		log.info("Requesting delete collector");
		collectorService.delete(id);
		return "redirect:/monitor/collectors/";
	}
	
	@RequestMapping(value = {"/change_status"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public @ResponseBody AjaxOperationResult changeStatus(int id, String operation, RedirectAttributes redirectAttributes) {
		Collector collector = collectorService.findById(id);
		log.info("Requesting change launch mode for collector: {}", collector);
		
		AjaxOperationResult result;
		
		if(collector.getType()==CollectorType.FTP){
		
			
			
			switch(operation){
				case "RUN": result = runCollectorRoute(collector); break;
				case "SHUTDOWN": result = stopCollectorRoute(collector); break;
				default: 	result = new AjaxOperationResult();
							result.setMessage("Error: operation must be RUN or SHUTDOWN, but got: "+operation);
							result.setStatus(-1);
			}
		} else {
			result = new AjaxOperationResult();
			result.setMessage("This collector not support start/stop method or it's not implemented yet.");
			result.setStatus(-1);
		}
				
		return result;
	}
	
	
	private AjaxOperationResult runCollectorRoute(Collector collector){
		AjaxOperationResult result = new AjaxOperationResult();
		if(ftpCamelRun.runCollectorRoute(collector)){
			result.setMessage("Collector successful start.");
			result.setStatus(0);
		} else {
			result.setMessage("Collector not start. Error on start process");
			result.setStatus(-1);
		}
		return result;
	}
	
	private AjaxOperationResult stopCollectorRoute(Collector collector){
		AjaxOperationResult result = new AjaxOperationResult();
		if(ftpCamelRun.stopCollectorRoute(collector)){
			collector.setCurrentRunStatus(CurrentRunStatus.STOP);
			collectorService.edit(collector);
			result.setMessage("Collector successful stop.");
			result.setStatus(0);
		} else {
			result.setMessage("Collector not stop. Error on stop process");
			result.setStatus(-1);
		}
		return result;
	}
	
	@RequestMapping(value = {"/change_launch_mode"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public @ResponseBody AjaxOperationResult changeLaunchMode(int id, LaunchModeType launchMode, RedirectAttributes redirectAttributes) {
		Collector collector = collectorService.findById(id);
		log.info("Requesting change launch mode for collector: {}", collector);
		
		AjaxOperationResult result = new AjaxOperationResult();
		if(collector!=null){
			collector.setLaunchModeType(launchMode);
			collectorService.edit(collector);
			result.setMessage("Launch mode for collector "+collector+" changed.");
			result.setStatus(0);
		} else {
			result.setMessage("This module not found in collector repo.");
			result.setStatus(-1);
		} 
			
		return result;
	}
}

package ru.obelisk.cucmaxl.web.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;
import ru.obelisk.database.models.entity.Module;
import ru.obelisk.database.models.entity.enums.ModuleLaunchMode;
import ru.obelisk.database.models.service.ModuleService;
import ru.obelisk.database.models.views.View;
import ru.obelisk.cucmaxl.backend.processors.module.ModuleRunner;
import ru.obelisk.cucmaxl.web.databinding.AjaxOperationResult;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

@Controller
@RequestMapping("/modules")
@Log4j2
public class ModuleController {
	
	@Autowired private ModuleService moduleService;
	@Autowired private ModuleRunner moduleRunner;
		
	@JsonView(value={View.Module.class})
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String indexPage(Model model) {
		log.info("Requesting modules page");
		
		Map<String, String> moduleLaunchModeMap = new HashMap<String, String>();
		List<ModuleLaunchMode> moduleLaunchModes = Arrays.asList(ModuleLaunchMode.values());
		Iterator<ModuleLaunchMode> moduleLaunchModesIterator = moduleLaunchModes.iterator();
		while(moduleLaunchModesIterator.hasNext()){
			ModuleLaunchMode launchMode = moduleLaunchModesIterator.next();
			moduleLaunchModeMap.put(launchMode.name(), launchMode.name());
		}
		model.addAttribute("moduleLaunchModeMap", moduleLaunchModeMap);
		return "modules/index";
	}
		
	@JsonView(value={View.Module.class})
	@RequestMapping(value = "/ajax/serverside/modules.json", method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public @ResponseBody DataTablesOutput<Module> getJobs(@Valid DataTablesInput input) {
		DataTablesOutput<Module> output = moduleService.findAll(input);
		output.setData(idGenerate(output.getData(),input.getStart()));
		return output;
	}
		
	private List<Module> idGenerate(List<Module> files, int start){
		for(int i=0;i<files.size();i++){
			files.get(i).setNumberLocalized(start+i+1);
		}
		return files;
	}
	
	@RequestMapping(value = {"/change_status"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public @ResponseBody AjaxOperationResult changeStatus(int id, String operation, RedirectAttributes redirectAttributes) {
		Module module = moduleService.findById(id);
		log.info("Requesting \"{}\" operation on module: {}", operation, module);
		AjaxOperationResult result;
		
		switch(operation){
			case "RUN": result = moduleRunner.runModule(module); break;
			case "SHUTDOWN": result = moduleRunner.stopModule(module); break;
			default: 	result = new AjaxOperationResult();
						result.setMessage("Error: operation must be RUN or SHUTDOWN, but got: "+operation);
						result.setStatus(-1);
		}
				
		return result;
	}
	
	@RequestMapping(value = {"/change_launch_mode"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public @ResponseBody AjaxOperationResult changeLaunchMode(int id, ModuleLaunchMode launchMode, RedirectAttributes redirectAttributes) {
		Module module = moduleService.findById(id);
		log.info("Requesting change launch mode for module: {}", module);
		
		AjaxOperationResult result = new AjaxOperationResult();
		if(module!=null){
			module.setLaunchMode(launchMode);
			moduleService.edit(module);
			result.setMessage("Launch mode for module "+module+" changed.");
			result.setStatus(0);
		} else {
			result.setMessage("This module not found in module repo.");
			result.setStatus(-1);
		} 
			
		return result;
	}
}

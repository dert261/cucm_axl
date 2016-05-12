package ru.obelisk.cucmaxl.web.controllers;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.annotation.Secured;
import ru.obelisk.database.models.entity.PartitionFilter;
import ru.obelisk.database.models.service.PartitionFilterService;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

@Controller
@RequestMapping("/cucmaxl/partitionfilter")
@Log4j2
public class PartitionFilterController {
	@Autowired private PartitionFilterService partitionFilterService;
		
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String indexPage(Model model) {
		log.info("Requesting  CUCM AXL port page");
		return "cucmaxl/partitionfilter/index";
	}
	
	@RequestMapping(value = {"/ajax/serverside/partitionfilter.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DataTablesOutput<PartitionFilter> getPartitionFilters(@Valid DataTablesInput input) {
		DataTablesOutput<PartitionFilter> output = partitionFilterService.findAll(input);
		output.setData(idGenerate(output.getData(),input.getStart()));
		return output;
	}
		
	private List<PartitionFilter> idGenerate(List<PartitionFilter> files, int start){
		for(int i=0;i<files.size();i++){
			files.get(i).setNumberLocalized(start+i+1);
		}
		return files;
	}
		
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewCreatePartitionFilterPage(Model model) {
		log.info("Requesting create CUCM AXL partition filter page");
		PartitionFilter partitionFilter = new PartitionFilter();
		model.addAttribute("partitionFilter", partitionFilter);
		return "cucmaxl/partitionfilter/create";
	}
		
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public String saveCreatePartitionFilterPage(final ModelMap model, 
									@Valid @ModelAttribute("partitionFilter") final PartitionFilter partitionFilter, 
									final BindingResult bindingResult) {
		log.info("Requesting add CUCM AXL partition filter method");
		if(bindingResult.hasErrors()){
			return "cucmaxl/partitionfilter/create";
		}
		partitionFilterService.add(partitionFilter);
		model.clear();
        return "redirect:/cucmaxl/partitionfilter/index.html";
	}
	
	@RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewUpdatePartitionFilterPage(ModelMap model, Locale locale, @PathVariable(value = "id") int id) {
		log.info("Requesting update CUCM AXL partition filter page");
		PartitionFilter partitionFilter = partitionFilterService.findById(id);
		model.addAttribute("partitionFilter", partitionFilter);
		return "cucmaxl/partitionfilter/update";
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.PUT)
	@Secured("ROLE_ADMIN")
	public String saveUpdatePartitionFilterPage(final ModelMap model, 
									@Valid @ModelAttribute("partitionFilter") final PartitionFilter partitionFilter, 
									final BindingResult bindingResult,
									SessionStatus status) {
		log.info("Requesting save update CUCM AXL partition filter method");
		if(bindingResult.hasErrors()){
			return "cucmaxl/port/update";
		}
		partitionFilterService.edit(partitionFilter);
		status.setComplete();
		return "redirect:/cucmaxl/partitionfilter/index.html";
	}
	
	@RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
	@Secured("ROLE_ADMIN")
	public String deletePartitionFilter(int id, SessionStatus status) {
		log.info("Requesting delete CUCM AXL partition filter");
		partitionFilterService.delete(id);
		status.setComplete();
		return "redirect:/cucmaxl/partitionfilter/index.html";
	}
}

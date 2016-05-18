package ru.obelisk.cucmaxl.web.controllers.cucmaxl;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.security.access.annotation.Secured;

import ru.obelisk.cucmaxl.cucm.repository.AxlPortRepository;
import ru.obelisk.database.models.entity.CucmAxlPort;
import ru.obelisk.database.models.entity.PartitionFilter;
import ru.obelisk.database.models.entity.enums.ResyncStatus;
import ru.obelisk.database.models.entity.enums.ResyncUnit;
import ru.obelisk.database.models.service.CucmAxlPortService;
import ru.obelisk.database.models.service.PartitionFilterService;
import ru.obelisk.database.models.views.View;
import ru.obelisk.database.select2.Select2Result;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import ru.obelisk.cucmaxl.scheduler.CucmAxlPortSyncUtils;
import ru.obelisk.cucmaxl.scheduler.JobScheduler;

@Controller
@RequestMapping("/cucmaxl/port")
public class CucmAxlPortController {
	
	private static Logger logger = LogManager.getLogger(CucmAxlPortController.class);
	
	@Autowired private CucmAxlPortService cucmAxlPortService;
	@Autowired private PartitionFilterService partitionFilterService;
	@Autowired private AxlPortRepository axlPortRepository;
	
	@Autowired private CucmAxlPortSyncUtils cucmAxlPortSyncUtils;
	@Autowired private JobScheduler jobScheduler;
		
	@RequestMapping(value = {"/search"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody List<Select2Result> searchCucmAxlPort(@RequestParam String searchString) {
		logger.info("Requesting search CUCM AXL port with term: {}",searchString);
		return cucmAxlPortService.findByTerm(searchString);
	}
	
	@JsonView(value={View.CucmAxlPort.class})
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String indexPage(Model model) {
		logger.info("Requesting  CUCM AXL port page");
		CucmAxlPort cucmAxlPort = new CucmAxlPort();
		model.addAttribute("cucmAxlPort", cucmAxlPort);
		model.addAttribute("cucmAxlPortAll", cucmAxlPortService.findAll());
		return "cucmaxl/port/index";
	}
	
	
	
	
	/*@JsonView(value={View.CucmAxlPort.class})
	@RequestMapping(value = {"/ajax/serverside/cucmaxlport.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DatatablesResponse<CucmAxlPort> cucmAxlPortsDatatables(
			@DatatableCriterias DatatablesCriterias criterias, 
			Model model) {
		logger.info("Requesting CUCM AXL port data for table on index page");
		List<CucmAxlPort> cucmAxlPorts = cucmAxlPortService.findWithDatatablesCriterias(criterias);
		Long count = cucmAxlPortService.getTotalCount();
		Long countFiltered = cucmAxlPortService.getFilteredCount(criterias);
		return DatatablesResponse.build(new DataSet<CucmAxlPort>(cucmAxlPorts,count,countFiltered), criterias);
	}
		
	@JsonView(value={View.CucmAxlPort.class})
	@RequestMapping(value = {"/ajax/clientside/cucmaxlport.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DatatablesResponse<CucmAxlPort> cucmAxlPortsDataClientSide(Model model) {
		logger.info("Requesting CUCM AXL port data for table on index page");
		List<CucmAxlPort> cucmAxlPorts = cucmAxlPortService.findAll();
		return DatatablesResponse.clientSideBuild(cucmAxlPorts);
	}*/
	
	
	
	@JsonView(value={View.CucmAxlPort.class})
	@RequestMapping(value = "/ajax/serverside/cucmaxlport.json", method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public @ResponseBody DataTablesOutput<CucmAxlPort> cucmAxlPortDatatable(@Valid DataTablesInput input) {
		DataTablesOutput<CucmAxlPort> output = cucmAxlPortService.findAll(input);
		output.setData(idGenerate(output.getData(),input.getStart()));
		return output;
	}
		
	private List<CucmAxlPort> idGenerate(List<CucmAxlPort> files, int start){
		for(int i=0;i<files.size();i++){
			files.get(i).setNumberLocalized(start+i+1);
		}
		return files;
	}
	
	
	
	
	@JsonView(value={View.CucmAxlPort.class})
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewCreateCucmAxlPortPage(Model model) {
		logger.info("Requesting create CUCM AXL port page");
		CucmAxlPort cucmAxlPort = new CucmAxlPort();
		model.addAttribute("cucmAxlPort", cucmAxlPort);
		model.addAttribute("resyncUnits", Arrays.asList(ResyncUnit.values()));
		
		Map<Integer, String> partitionFiltersMap = new HashMap<Integer, String>();
		List<PartitionFilter> partitionFilters = partitionFilterService.findAll();
		Iterator<PartitionFilter> partitionFiltersIterator = partitionFilters.iterator();
		while(partitionFiltersIterator.hasNext()){
			PartitionFilter partitionFilter = partitionFiltersIterator.next();
			partitionFiltersMap.put(partitionFilter.getId(), partitionFilter.getName()+" ("+partitionFilter.getDescription()+")");
		}
		model.addAttribute("partitionFiltersMap", partitionFiltersMap);
		return "cucmaxl/port/create";
	}
	
	@JsonView(value={View.CucmAxlPort.class})
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public String saveCreateCucmAxlPortPage(final ModelMap model, 
									@Valid @ModelAttribute("cucmAxlPort") final CucmAxlPort cucmAxlPort, 
									final BindingResult bindingResult) {
		logger.info("Requesting add CUCM AXL port method");
		
		if(cucmAxlPort.getPartitionFilter()!=null && cucmAxlPort.getPartitionFilter().getId()==0){
			cucmAxlPort.setPartitionFilter(null);
		}
		
		if(bindingResult.hasErrors()){
			return "cucmaxl/port/create";
		}
		cucmAxlPortService.add(cucmAxlPort);
		jobScheduler.addCucmAxlPortJob(cucmAxlPort);
		model.clear();
        return "redirect:/cucmaxl/port/index.html";
	}
	
	@JsonView(value={View.CucmAxlPort.class})
	@RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewUpdateCucmAxlPortPage(ModelMap model, Locale locale, @PathVariable(value = "id") int id) {
		logger.info("Requesting update CUCM AXL port page");
		CucmAxlPort cucmAxlPort = cucmAxlPortService.findById(id);
		model.addAttribute("cucmAxlPort", cucmAxlPort);
		model.addAttribute("resyncUnits", Arrays.asList(ResyncUnit.values()));
		
		Map<Integer, String> partitionFiltersMap = new HashMap<Integer, String>();
		List<PartitionFilter> partitionFilters = partitionFilterService.findAll();
		Iterator<PartitionFilter> partitionFiltersIterator = partitionFilters.iterator();
		while(partitionFiltersIterator.hasNext()){
			PartitionFilter partitionFilter = partitionFiltersIterator.next();
			partitionFiltersMap.put(partitionFilter.getId(), partitionFilter.getName()+" ("+partitionFilter.getDescription()+")");
		}
		model.addAttribute("partitionFiltersMap", partitionFiltersMap);
						
		Date nextFireTime = jobScheduler.getNextFireTime("cucmAxlPortSyncTrigger"+cucmAxlPort.getId(), "cucmAxlPortSyncTriggerGroup");
		String formattedDate="---";
		if(nextFireTime!=null){
			DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale);
			formattedDate = df.format(nextFireTime);
		}
		model.addAttribute("nextFireTime", formattedDate);
		
		return "cucmaxl/port/update";
	}
	
	@JsonView(value={View.CucmAxlPort.class})
	@RequestMapping(value = {"/update"}, method = RequestMethod.PUT)
	@Secured("ROLE_ADMIN")
	public String saveUpdateCucmAxlPortPage(final ModelMap model, 
									@Valid @ModelAttribute("cucmAxlPort") final CucmAxlPort cucmAxlPort, 
									final BindingResult bindingResult,
									SessionStatus status) {
		logger.info("Requesting save update CUCM AXL port method");
		if(cucmAxlPort.getPartitionFilter()!=null && cucmAxlPort.getPartitionFilter().getId()==0){
			cucmAxlPort.setPartitionFilter(null);
		}
		if(bindingResult.hasErrors()){
			return "cucmaxl/port/update";
		}
		
		
			
		cucmAxlPortService.edit(cucmAxlPort);
		
		axlPortRepository.deleteAxlPort(cucmAxlPort);
		axlPortRepository.addAxlPort(cucmAxlPort);

		if(!cucmAxlPort.isResyncFlag())
			jobScheduler.deleteJob("cucmAxlPortSyncJob"+cucmAxlPort.getId(), "cucmAxlPortSyncJobGroup");
		else 
			jobScheduler.rescheduleCucmAxlPortJob(cucmAxlPort);
		
		status.setComplete();
		return "redirect:/cucmaxl/port/index.html";
	}
	
	@JsonView(value={View.CucmAxlPort.class})
	@RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
	@Secured("ROLE_ADMIN")
	public String deleteCucmAxlPort(int id, SessionStatus status) {
		logger.info("Requesting delete CUCM AXL port");
		
		jobScheduler.deleteJob("cucmAxlPortSyncJob"+id, "cucmAxlPortSyncJobGroup");
		axlPortRepository.deleteAxlPort(cucmAxlPortService.findById(id));
		cucmAxlPortService.delete(id);
		status.setComplete();
		return "redirect:/cucmaxl/port/index.html";
	}
	
	@JsonView(value={View.CucmAxlPort.class})
	@RequestMapping(value = {"/resync"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String resyncCucmAxlPort(int id, SessionStatus status) {
		logger.info("Requesting resync CUCM AXL port");
		CucmAxlPort axlPort = cucmAxlPortService.findById(id);
				
		if(axlPort.getResyncStatus()!=ResyncStatus.ACTIVE)
			cucmAxlPortSyncUtils.refreshCucmAxlPort(id);
				
		return "redirect:/cucmaxl/port/index.html";
	}
}

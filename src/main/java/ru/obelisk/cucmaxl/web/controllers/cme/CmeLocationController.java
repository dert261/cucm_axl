package ru.obelisk.cucmaxl.web.controllers.cme;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

import ru.obelisk.cucmaxl.annotations.DatatableCriterias;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeLocation;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeLocationService;
import ru.obelisk.cucmaxl.database.models.views.View;
import ru.obelisk.cucmaxl.web.ui.datatables.DataSet;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesResponse;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

@Controller
@RequestMapping("/cme/locations")
public class CmeLocationController {
	private static Logger logger = LogManager.getLogger(CmeLocationController.class);
	
	@Autowired private CmeLocationService cmeLocationService;
	
	@JsonView(View.CmeLocation.class)
	@RequestMapping(value = {"/search"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody List<Select2Result> searchCmeLocation(@RequestParam String searchString) {
		logger.info("Requesting search CME location with term: {}",searchString);
		return cmeLocationService.findByTerm(searchString);
	}
	@JsonView(View.CmeLocation.class)
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String indexPage(Model model) {
		logger.info("Requesting CME location page");
		CmeLocation cmeLocation = new CmeLocation();
		model.addAttribute("cmeLocation", cmeLocation);
		model.addAttribute("cmeLocationAll", new ArrayList<CmeLocation>());
		return "cme/locations/index";
	}
	@JsonView(View.CmeLocation.class)
	@RequestMapping(value = {"/ajax/serverside/cmelocations.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DatatablesResponse<CmeLocation> cmeLocationsDatatables(
			@DatatableCriterias DatatablesCriterias criterias, 
			Model model) {
		logger.info("Requesting CME location data for table on index page");
		List<CmeLocation> cmeLocations = cmeLocationService.findWithDatatablesCriterias(criterias);
		Long count = cmeLocationService.getTotalCount();
		Long countFiltered = cmeLocationService.getFilteredCount(criterias);
		return DatatablesResponse.build(new DataSet<CmeLocation>(cmeLocations,count,countFiltered), criterias);
	}
	@JsonView(View.CmeLocation.class)
	@RequestMapping(value = {"/ajax/clientside/cmelocations.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DatatablesResponse<CmeLocation> cmeLocationsDataClientSide(Model model) {
		logger.info("Requesting CME location data for table on index page");
		List<CmeLocation> cmeLocations = cmeLocationService.findAll();
		return DatatablesResponse.clientSideBuild(cmeLocations);
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewCreateCmeLocationPage(Model model) {
		logger.info("Requesting create CME location page");
		CmeLocation cmeLocation = new CmeLocation();
		model.addAttribute("cmeLocation", cmeLocation);
		return "cme/locations/create";
	}
		
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public String saveCreateCmeLocationPage(final ModelMap model, 
									@Valid @ModelAttribute("cmeLocation") final CmeLocation cmeLocation, 
									final BindingResult bindingResult) {
		logger.info("Requesting add CME location method");
		if(bindingResult.hasErrors()){
			return "cme/locations/create";
		}
		cmeLocationService.add(cmeLocation);
		model.clear();
        return "redirect:/cme/locations/";
	}
	
	@RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewUpdateCmeLocationPage(ModelMap model, Locale locale, @PathVariable(value = "id") int id) {
		logger.info("Requesting update CME location page");
		CmeLocation cmeLocation = cmeLocationService.findById(id);
		model.addAttribute("cmeLocation", cmeLocation);
		return "cme/locations/update";
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.PUT)
	@Secured("ROLE_ADMIN")
	public String saveUpdateCmeLocationPage(final ModelMap model, 
									@Valid @ModelAttribute("cmeLocation") final CmeLocation cmeLocation, 
									final BindingResult bindingResult,
									SessionStatus status) {
		logger.info("Requesting save update CME location method");
		if(bindingResult.hasErrors()){
			return "cme/locations/update";
		}
		cmeLocationService.edit(cmeLocation);
		status.setComplete();
		return "redirect:/cme/locations/";
	}
	
	@RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
	@Secured("ROLE_ADMIN")
	public String deleteCmeLocation(int id, SessionStatus status) {
		logger.info("Requesting delete CME location");
		cmeLocationService.delete(id);
		status.setComplete();
		return "redirect:/cme/locations/";
	}
}

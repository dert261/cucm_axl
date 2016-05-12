package ru.obelisk.cucmaxl.web.controllers.ldap;

import java.util.List;

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
import org.springframework.security.access.annotation.Secured;

import com.fasterxml.jackson.annotation.JsonView;
import ru.obelisk.database.models.entity.LdapCustomFilter;
import ru.obelisk.database.models.service.LdapCustomFilterService;
import ru.obelisk.database.models.views.View;
import ru.obelisk.database.select2.Select2Result;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

@Controller
@RequestMapping("/ldap/customfilter")
public class LdapCustomFilterController {
	
	private static Logger logger = LogManager.getLogger(LdapCustomFilterController.class);
	
	@Autowired
    private LdapCustomFilterService ldapCustomFilterService;
	
	@RequestMapping(value = {"/search"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody List<Select2Result> searchLdapCustomFilter(@RequestParam String searchString) {
		logger.info("Requesting search ldap custom filter with term: {}",searchString);
		return ldapCustomFilterService.findByTerm(searchString);
	}
	
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String indexPage(Model model) {
		logger.info("Requesting  ldap custom filter page");
		LdapCustomFilter ldapCustomFilter = new LdapCustomFilter();
		model.addAttribute("ldapCustomFilter", ldapCustomFilter);
		model.addAttribute("ldapCustomFilterAll", ldapCustomFilterService.findAll());
		return "ldap/customfilter/index";
	}
	
	/*@JsonView(View.LdapCustomFilter.class)
	@RequestMapping(value = {"/ajax/serverside/ldapcustomfilter.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DatatablesResponse<LdapCustomFilter> ldapCustomFiltersDatatables(
			@DatatableCriterias DatatablesCriterias criterias, 
			Model model) {
		logger.info("Requesting ldap custom filter data for table on index page");
		List<LdapCustomFilter> ldapCustomFilters = ldapCustomFilterService.findLdapCustomFilterWithDatatablesCriterias(criterias);
		Long count = ldapCustomFilterService.getTotalCount();
		Long countFiltered = ldapCustomFilterService.getFilteredCount(criterias);
		return DatatablesResponse.build(new DataSet<LdapCustomFilter>(ldapCustomFilters,count,countFiltered), criterias);
	}
	
	@JsonView(View.LdapCustomFilter.class)	
	@RequestMapping(value = {"/ajax/clientside/ldapcustomfilter.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DatatablesResponse<LdapCustomFilter> ldapCustomFiltersDataClientSide(Model model) {
		logger.info("Requesting ldap custom filter data for table on index page");
		List<LdapCustomFilter> ldapCustomFilters = ldapCustomFilterService.getAllLdapCustomFilters();
		return DatatablesResponse.clientSideBuild(ldapCustomFilters);
	}*/
	
	@JsonView(View.LdapCustomFilter.class)
	@RequestMapping(value = {"/ajax/serverside/ldapcustomfilter.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DataTablesOutput<LdapCustomFilter> ldapCustomFiltersDatatable(@Valid DataTablesInput input) {
		DataTablesOutput<LdapCustomFilter> output = ldapCustomFilterService.findAll(input);
		output.setData(idGenerate(output.getData(),input.getStart()));
		return output;
	}
		
	private List<LdapCustomFilter> idGenerate(List<LdapCustomFilter> files, int start){
		for(int i=0;i<files.size();i++){
			files.get(i).setNumberLocalized(start+i+1);
		}
		return files;
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewCreateLdapCustomFilterPage(Model model) {
		logger.info("Requesting create ldap custom filter page");
		LdapCustomFilter ldapCustomFilter = new LdapCustomFilter();
		model.addAttribute("ldapCustomFilter", ldapCustomFilter);
		return "ldap/customfilter/create";
	}
		
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public String saveCreateLdapCustomFilterPage(	final ModelMap model, 
									@Valid @ModelAttribute("ldapCustomFilter") final LdapCustomFilter ldapCustomFilter, 
									final BindingResult bindingResult) {
		logger.info("Requesting add ldap custom filter method");
		if(bindingResult.hasErrors()){
			return "ldap/customfilter/create";
		}
		ldapCustomFilterService.add(ldapCustomFilter);
		model.clear();
        return "redirect:/ldap/customfilter/index.html";
	}
	
	@RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewUpdateLdapCustomFilterPage(ModelMap model, @PathVariable(value = "id") int id) {
		logger.info("Requesting update ldap custom filter page");
		LdapCustomFilter ldapCustomFilter = ldapCustomFilterService.findById(id);
		model.addAttribute("ldapCustomFilter", ldapCustomFilter);
		return "ldap/customfilter/update";
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.PUT)
	@Secured("ROLE_ADMIN")
	public String saveUpdateLdapCustomFilterPage(final ModelMap model, 
									@Valid @ModelAttribute("ldapCustomFilter") final LdapCustomFilter ldapCustomFilter, 
									final BindingResult bindingResult,
									SessionStatus status) {
		logger.info("Requesting save update ldap custom filter method");
		if(bindingResult.hasErrors()){
			return "ldap/customfilter/update";
		}
		ldapCustomFilterService.edit(ldapCustomFilter);
		status.setComplete();
		return "redirect:/ldap/customfilter/index.html";
	}
	
	@RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
	@Secured("ROLE_ADMIN")
	public String deleteLdapCustomFilter(int id, SessionStatus status) {
		logger.info("Requesting delete ldap custom filter");
		ldapCustomFilterService.delete(id);
		status.setComplete();
		return "redirect:/ldap/customfilter/index.html";
	}
}

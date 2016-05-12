package ru.obelisk.cucmaxl.web.controllers.ldap;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.validation.ConstraintViolation;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import javax.validation.metadata.ConstraintDescriptor;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.security.access.annotation.Secured;

import ru.obelisk.database.models.entity.LdapCustomFilter;
import ru.obelisk.database.models.entity.LdapDirSyncParameters;
import ru.obelisk.database.models.entity.LdapDirSyncServer;
import ru.obelisk.database.models.entity.enums.PhoneBookSyncSource;
import ru.obelisk.database.models.entity.enums.ResyncStatus;
import ru.obelisk.database.models.entity.enums.ResyncUnit;
import ru.obelisk.database.models.service.LdapCustomFilterService;
import ru.obelisk.database.models.service.LdapDirSyncParametersService;
import ru.obelisk.database.models.views.View;
import ru.obelisk.database.select2.Select2Result;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import ru.obelisk.cucmaxl.scheduler.DirSyncUtils;
import ru.obelisk.cucmaxl.scheduler.JobScheduler;

@Controller
@RequestMapping("/ldap/dirsync")
public class LdapDirSyncParametersController {
	
	@Autowired private LdapDirSyncParametersService ldapDirSyncParamsService;
	@Autowired private LdapCustomFilterService ldapCustomFilterService;
	@Autowired private JobScheduler jobScheduler;
	@Autowired private DirSyncUtils dirSyncUtils;
	
	
	@Autowired(required = true)
	private javax.validation.Validator validator;
	
	private static Logger logger = LogManager.getLogger(LdapDirSyncParametersController.class);
	
	@JsonView(value={View.LdapDirSyncParameters.class})
	@RequestMapping(value = {"/search"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody List<Select2Result> searchUser(@RequestParam String searchString) {
		logger.info("Requesting search ldap dir sync with term: {}",searchString);
		return ldapDirSyncParamsService.findByTerm(searchString);
	}
	
	@JsonView(value={View.LdapDirSyncParameters.class})
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String indexPage(Model model) {
		logger.info("Requesting index ldap dir sync page");
		LdapDirSyncParameters ldapDirSyncParameters = new LdapDirSyncParameters();
		List<LdapDirSyncParameters> ldapDirSyncParametersAll = ldapDirSyncParamsService.findAll();
		model.addAttribute("ldapDirSyncParams", ldapDirSyncParameters);
		model.addAttribute("ldapDirSyncParamsAll", ldapDirSyncParametersAll);
        return "ldap/dirsync/index";
	}
	
	
	
	
	/*
	
	@JsonView(value={View.LdapDirSyncParameters.class})
	@RequestMapping(value = {"/ajax/serverside/ldapdirsyncdata.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DatatablesResponse<LdapDirSyncParameters> usersDatatables(
			@DatatableCriterias DatatablesCriterias criterias, 
			Model model, 
			Locale locale) {
		logger.info("Requesting ldap dir sync data for table on index page");
		
		List<LdapDirSyncParameters> ldapDirSyncParameters = ldapDirSyncParamsService.findLdapDirSyncParametersWithDatatablesCriterias(criterias);
		Long count = ldapDirSyncParamsService.getTotalCount();
		Long countFiltered = ldapDirSyncParamsService.getFilteredCount(criterias);
	    return DatatablesResponse.build(new DataSet<LdapDirSyncParameters>(ldapDirSyncParameters,count,countFiltered), criterias);
	}
	
	@JsonView(value={View.LdapDirSyncParameters.class})
	@RequestMapping(value = {"/ajax/clientside/ldapdirsyncdata.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DatatablesResponse<LdapDirSyncParameters> usersDataClientSide(Model model, Locale locale) {
		logger.info("Requesting users data for table on index page");
		List<LdapDirSyncParameters> ldapDirSyncParameters = ldapDirSyncParamsService.getAllLdapDirSyncParameters();
		return DatatablesResponse.clientSideBuild(ldapDirSyncParameters);
	}*/
	
	
	
	@JsonView(value={View.LdapDirSyncParameters.class})
	@RequestMapping(value = {"/ajax/serverside/ldapdirsyncdata.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DataTablesOutput<LdapDirSyncParameters> ldapDirSyncParametersDatatable(@Valid DataTablesInput input) {
		DataTablesOutput<LdapDirSyncParameters> output = ldapDirSyncParamsService.findAll(input);
		output.setData(idGenerate(output.getData(),input.getStart()));
		return output;
	}
		
	private List<LdapDirSyncParameters> idGenerate(List<LdapDirSyncParameters> files, int start){
		for(int i=0;i<files.size();i++){
			files.get(i).setNumberLocalized(start+i+1);
		}
		return files;
	}
	
	
	
	
	@JsonView(value={View.LdapDirSyncParameters.class})
	@RequestMapping(value = {"/ajax/addserver"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String addAuthServerAjax(ModelMap model,
									@ModelAttribute("ldapDirSyncParams") LdapDirSyncParameters formParameters,
									RedirectAttributes redirectAttributes) {
		logger.info("Requesting to add redundant ldap server (ajax)");
		formParameters.getLdapDirSyncServers().add(new LdapDirSyncServer());
		return "ldap/dirsync/form::ldapDirSyncServersContent";
	}
	
	@JsonView(value={View.LdapDirSyncParameters.class})
	@RequestMapping(value = {"/ajax/delserver"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String deleteAuthServerAjax(	final ModelMap model,
										@RequestParam("index") int index,
										@ModelAttribute("ldapDirSyncParams") final LdapDirSyncParameters formParameters) {
		logger.info("Requesting to delete redundant ldap server (ajax)");
		formParameters.getLdapDirSyncServers().remove(index);
		return "ldap/dirsync/form::ldapDirSyncServersContent";
	}
	
	@JsonView(value={View.LdapDirSyncParameters.class})
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewAddDirSyncParametersPage(ModelMap model) {
		logger.info("Requesting create ldap dir sync page");
		LdapDirSyncParameters ldapDirSyncParams = new LdapDirSyncParameters();
		ldapDirSyncParams.getLdapDirSyncServers().add(new LdapDirSyncServer());
		model.addAttribute("ldapDirSyncParams", ldapDirSyncParams);
		List<LdapCustomFilter> filters = ldapCustomFilterService.findAll();
		model.addAttribute("ldapCustomFilterAll", filters);
		model.addAttribute("resyncUnits", Arrays.asList(ResyncUnit.values()));
		model.addAttribute("phoneBookSyncSources", Arrays.asList(PhoneBookSyncSource.values()));
		return "ldap/dirsync/create";
	}
		
	@JsonView(value={View.LdapDirSyncParameters.class})
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public String addDirSyncParametersPage(final ModelMap model, 
									@Valid @ModelAttribute("ldapDirSyncParams") final LdapDirSyncParameters formParameters, 
									final BindingResult bindingResult,
									SessionStatus status,
									final RedirectAttributes redirectAttributes) {
		logger.info("Requesting add ldap dir sync method");
		
		if (isNotValid(formParameters, bindingResult, 
					LdapDirSyncParameters.LdapDirSyncParamsStepOne.class, 
					LdapDirSyncParameters.LdapDirSyncParamsStepTwo.class)) {
			List<LdapCustomFilter> filters = ldapCustomFilterService.findAll();
			model.addAttribute("ldapCustomFilterAll", filters);
			return "ldap/dirsync/create";
		}
						
		ldapDirSyncParamsService.add(formParameters);
		redirectAttributes.addFlashAttribute("operationResult", "Successfully add");
		if(formParameters.isResyncFlag()) jobScheduler.addLdapDirSyncJob(formParameters);
		status.setComplete();
		return "redirect:/ldap/dirsync/index.html";
	}
	
	@JsonView(value={View.LdapDirSyncParameters.class})
	@RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewUpdateDirSyncParametersPage(ModelMap model, Locale locale, @PathVariable(value = "id") int id) {
		logger.info("Requesting update ldap dir sync page");
		LdapDirSyncParameters ldapDirSyncParams = ldapDirSyncParamsService.getLdapDirSyncParametersById(id);
		model.addAttribute("ldapDirSyncParams", ldapDirSyncParams);
		List<LdapCustomFilter> filters = ldapCustomFilterService.findAll();
		model.addAttribute("ldapCustomFilterAll", filters);
		model.addAttribute("resyncUnits", Arrays.asList(ResyncUnit.values()));
		model.addAttribute("phoneBookSyncSources", Arrays.asList(PhoneBookSyncSource.values()));
				
		Date nextFireTime = jobScheduler.getNextFireTime("dirSyncTrigger"+ldapDirSyncParams.getId(), "dirSyncTriggerGroup");
		
		String formattedDate="---";
		if(nextFireTime!=null){
			DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale);
			formattedDate = df.format(nextFireTime);
		}
		
		model.addAttribute("nextFireTime", formattedDate);
		
		return "ldap/dirsync/update";
	}
	
	@JsonView(value={View.LdapDirSyncParameters.class})	
	@RequestMapping(value = {"/update"}, method = RequestMethod.PUT)
	@Secured("ROLE_ADMIN")
	public String updateDirSyncParametersPage(final ModelMap model, 
									@Valid @ModelAttribute("ldapDirSyncParams") final LdapDirSyncParameters formParameters, 
									final BindingResult bindingResult,
									SessionStatus status,
									final RedirectAttributes redirectAttributes) {
		logger.info("Requesting update ldap dir sync method");
		
		if (isNotValid(formParameters, bindingResult, 
					LdapDirSyncParameters.LdapDirSyncParamsStepOne.class, 
					LdapDirSyncParameters.LdapDirSyncParamsStepTwo.class)) {
			List<LdapCustomFilter> filters = ldapCustomFilterService.findAll();
			model.addAttribute("ldapCustomFilterAll", filters);
			return "ldap/dirsync/update/"+formParameters.getId();
		}
		if(!formParameters.isResyncFlag())
			jobScheduler.deleteJob("dirSyncJob"+formParameters.getId(), "dirSyncJobGroup");
		else 
			jobScheduler.rescheduleLdapDirSyncJob(formParameters);
		
		//LdapDirSyncParameters ldapDir = ldapDirSyncParamsService.getLdapDirSyncParametersById(formParameters.getId());
		//formParameters.setResyncStatus(ldapDir.getResyncStatus());
		ldapDirSyncParamsService.edit(formParameters);
				
		
		
		status.setComplete();
		return "redirect:/ldap/dirsync/index.html";
	}
	
	@JsonView(value={View.LdapDirSyncParameters.class})
	@RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
	@Secured("ROLE_ADMIN")
	public String deleteLdapDirSync(int id, SessionStatus status, final RedirectAttributes redirectAttributes) {
		logger.info("Requesting delete ldap dir sync");
		jobScheduler.deleteJob("dirSyncJob"+id, "dirSyncJobGroup");
		ldapDirSyncParamsService.delete(id);
		redirectAttributes.addFlashAttribute("operationResult", "Successfully delete");
		status.setComplete();
		return "redirect:/ldap/dirsync/index.html";
	}
	
	@RequestMapping(value = {"/resync"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String resyncLdapDir(int id, SessionStatus status) {
		logger.info("Requesting resync Ldap Directory");
		LdapDirSyncParameters ldapDirSyncParams = ldapDirSyncParamsService.getLdapDirSyncParametersById(id);
		if(ldapDirSyncParams.getResyncStatus()!=ResyncStatus.ACTIVE)
			dirSyncUtils.refreshUsersFromLdap(id);
		return "redirect:/ldap/dirsync/index.html";
	}
	
	private boolean isNotValid(Object target, Errors errors, Class<?>... groups) {
		Set<ConstraintViolation<Object>> result = validator.validate(target, groups);
		for (ConstraintViolation<Object> violation : result) {
			String field = violation.getPropertyPath().toString();
			FieldError fieldError = errors.getFieldError(field);
			if (fieldError == null || !fieldError.isBindingFailure()) {
				ConstraintDescriptor<?> constraintDescriptor = violation.getConstraintDescriptor();
				errors.rejectValue(field, constraintDescriptor.getAnnotation().annotationType().getSimpleName(), getArgumentsForConstraint(errors.getObjectName(), field, constraintDescriptor), violation.getMessage());
			}
		}
		return errors.hasErrors();
	}

	private Object[] getArgumentsForConstraint(String objectName, String field, ConstraintDescriptor<?> descriptor) {
		List<Object> arguments = new LinkedList<Object>();
		String[] codes = new String[] { objectName + Errors.NESTED_PATH_SEPARATOR + field, field };
		arguments.add(new DefaultMessageSourceResolvable(codes, field));
		Map<String, Object> attributesToExpose = new TreeMap<String, Object>();
		for (Map.Entry<String, Object> entry : descriptor.getAttributes().entrySet()) {
			String attributeName = entry.getKey();
			Object attributeValue = entry.getValue();
			if ("message".equals(attributeName) || "groups".equals(attributeName) || "payload".equals(attributeName)) {
				attributesToExpose.put(attributeName, attributeValue);
			}
		}
		arguments.addAll(attributesToExpose.values());
		return arguments.toArray(new Object[arguments.size()]);
	}
}

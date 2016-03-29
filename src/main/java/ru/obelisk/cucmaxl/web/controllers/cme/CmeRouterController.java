package ru.obelisk.cucmaxl.web.controllers.cme;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.security.access.annotation.Secured;

import ru.obelisk.cucmaxl.cucm.service.CmeService;
import ru.obelisk.cucmaxl.cucm.service.CucmWithDBService;
import ru.obelisk.cucmaxl.cucm.utils.CucmExportDevice;
import ru.obelisk.cucmaxl.cucm.utils.CucmMigrationUtils;
import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.entity.User;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeCustomDevice;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeCustomExtension;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeCustomSipDevice;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeCustomSipExtension;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeDevice;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeExtMapStatus;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeExtension;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeSipDevice;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeSipExtMapStatus;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeSipExtension;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeVoiceHuntGroup;
import ru.obelisk.cucmaxl.database.models.entity.enums.CmeUserType;
import ru.obelisk.cucmaxl.database.models.entity.enums.CucmPhoneType;
import ru.obelisk.cucmaxl.database.models.service.CucmAxlPortService;
import ru.obelisk.cucmaxl.database.models.service.UserService;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeDeviceService;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeLocationService;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeRouterService;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeSipDeviceService;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeVoiceHuntGroupService;
import ru.obelisk.cucmaxl.database.models.views.View;
import ru.obelisk.cucmaxl.web.controllers.cme.viewtypes.RouterExportDetail;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesResponse;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

@Controller
@RequestMapping("/cme/routers")
public class CmeRouterController {
	private static Logger logger = LogManager.getLogger(CmeRouterController.class);
	
	@Autowired private CmeRouterService cmeRouterService;
	@Autowired private CmeVoiceHuntGroupService cmeVoiceHuntGroupService; 
	@Autowired private CmeDeviceService cmeDeviceService;
	@Autowired private CmeSipDeviceService cmeSipDeviceService;
	@Autowired private CmeLocationService cmeLocationService;
	@Autowired private CmeService cmeService;
	@Autowired private UserService userService;
	
	@Autowired private CucmAxlPortService cucmAxlPortService;
	@Autowired private CucmWithDBService cucmDbService;
	
	@Autowired private MessageSource messageSource;
	
	@Autowired private CucmMigrationUtils migrationUtils;
	
	@RequestMapping(value = {"/search"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public @ResponseBody List<Select2Result> searchCmeRouter(@RequestParam String searchString) {
		logger.info("Requesting search CME router with term: {}",searchString);
		return cmeRouterService.findByTerm(searchString);
	}
	
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public String indexPage(Model model) {
		logger.info("Requesting CME router page");
		model.addAttribute("cmeRouter", new CmeRouter());
		model.addAttribute("cmeRouterAll", new ArrayList<CmeRouter>());
		return "cme/routers/index";
	}
	
	/*@JsonView(View.CmeRouter.class)
	@RequestMapping(value = {"/ajax/serverside/cmerouters.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DatatablesResponse<CmeRouter> cmeRoutersDatatables(
			@DatatableCriterias DatatablesCriterias criterias, 
			Model model) {
		logger.info("Requesting CME router data for table on index page");
		List<CmeRouter> cmeRouters = cmeRouterService.findWithDatatablesCriterias(criterias);
		Long count = cmeRouterService.getTotalCount();
		Long countFiltered = cmeRouterService.getFilteredCount(criterias);
		return DatatablesResponse.build(new DataSet<CmeRouter>(cmeRouters,count,countFiltered), criterias);
	}*/
		
	@RequestMapping(value = {"/ajax/clientside/cmerouters.json"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public @ResponseBody DatatablesResponse<CmeRouter> cmeRoutersDataClientSide(Model model) {
		logger.info("Requesting CME router data for table on index page");
		List<CmeRouter> cmeRouters = cmeRouterService.findAll();
		return DatatablesResponse.clientSideBuild(cmeRouters);
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewCreateCmeRouterPage(Model model) {
		logger.info("Requesting create CME router page");
		model.addAttribute("cmeRouter", new CmeRouter());
		model.addAttribute("locationsAll", cmeLocationService.findAllWithoutRelations());
		return "cme/routers/create";
	}
		
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public String saveCreateCmeRouterPage(final ModelMap model, 
									@Valid @ModelAttribute("cmeRouter") final CmeRouter cmeRouter, 
									final BindingResult bindingResult) {
		logger.info("Requesting add CME router method");
		if(bindingResult.hasErrors()){
			model.addAttribute("locationsAll", cmeLocationService.findAll());
			return "cme/routers/create";
		}
		cmeRouterService.add(cmeRouter);
		model.clear();
        return "redirect:/cme/routers/";
	}
	
	@JsonView(View.CmeRouter.class)
	@RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewUpdateCmeRouterPage(ModelMap model, Locale locale, @PathVariable(value = "id") int id) {
		logger.info("Requesting update CME router page");
		CmeRouter cmeRouter = cmeRouterService.findById(id);
		model.addAttribute("cmeRouter", cmeRouter);
		model.addAttribute("locationsAll", cmeLocationService.findAll());
		return "cme/routers/update";
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.PUT)
	@Secured("ROLE_ADMIN")
	public String saveUpdateCmeRouterPage(final ModelMap model, 
									@Valid @ModelAttribute("cmeRouter") final CmeRouter cmeRouter, 
									final BindingResult bindingResult,
									SessionStatus status) {
		logger.info("Requesting save update CME router method");
		if(bindingResult.hasErrors()){
			model.addAttribute("locationsAll", cmeLocationService.findAll());
			return "cme/router/update";
		}
		cmeRouterService.edit(cmeRouter);
		status.setComplete();
		return "redirect:/cme/routers/";
	}
	
	@RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
	@Secured("ROLE_ADMIN")
	public String deleteCmeRouter(int id, SessionStatus status) {
		logger.info("Requesting delete CME router");
		cmeRouterService.delete(id);
		status.setComplete();
		return "redirect:/cme/routers/";
	}
	
	@JsonView(View.CmeRouter.class)
	@RequestMapping(value = {"/view/{id}"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public String viewDetailedCmeRouterPage(ModelMap model, Locale locale, @PathVariable(value = "id") int id) {
		logger.info("Requesting detail view CME router page");
		CmeRouter cmeRouter = cmeRouterService.findById(id);
		model.addAttribute("cmeRouter", cmeRouter);
		return "cme/routers/view";
	}
	
	@RequestMapping(value = {"/import"}, method = RequestMethod.POST)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public String importCmeRouter(int id, SessionStatus status) {
		logger.info("Requesting import CME router");
		cmeService.importCmeRouter(cmeRouterService.findById(id));
		status.setComplete();
		return "redirect:/cme/routers/";
	}
	
	/*@RequestMapping(value = {"/export"}, method = RequestMethod.POST)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public String exportPhones(int id, SessionStatus status) {
		logger.info("Requesting export phones");
		CucmAxlPort axlPort = cucmAxlPortService.getCucmAxlPortById(2);
		Iterator<CmeDevice> cmeDevicesIterator = cmeDeviceService.findAllForExportByRouterWithRelations(cmeRouterService.findById(id)).iterator();
		while(cmeDevicesIterator.hasNext()){
			CmeDevice cmeDevice = cmeDevicesIterator.next();
			CucmDevice cucmDevice = migrationUtils.getCucmDeviceFromCmeDevice(cmeDevice);
			logger.info("CmeDevice: {}", cmeDevice);
			logger.info("CucmDevice: {}", cucmDevice);
			cucmDbService.cucmAddPhone(axlPort, cucmDevice);
		}
		
		
		Iterator<CmeSipDevice> cmeSipDevicesIterator = cmeSipDeviceService.findAllForExportByRouterWithRelations(cmeRouterService.findById(id)).iterator();
		while(cmeSipDevicesIterator.hasNext()){
			CmeSipDevice cmeSipDevice = cmeSipDevicesIterator.next();
			CucmDevice cucmDevice = migrationUtils.getCucmDeviceFromCmeSipDevice(cmeSipDevice);
			logger.info("CmeDevice: {}", cmeSipDevice);
			logger.info("CucmDevice: {}", cucmDevice);
			cucmDbService.cucmAddPhone(axlPort, cucmDevice);
		}
		
		status.setComplete();
		return "redirect:/cme/routers/";
	}*/
	
	@JsonView(View.CmeRouter.class)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	@RequestMapping(value = "/ajax/serverside/cmerouters.json", method = RequestMethod.GET)
	public @ResponseBody DataTablesOutput<CmeRouter> getRouters(@Valid DataTablesInput input) {
		DataTablesOutput<CmeRouter> out = cmeRouterService.findAll(input);
		out.setData(idGenerate(out.getData(), input.getStart()));
		return out;
	}
	
	private List<CmeRouter> idGenerate(List<CmeRouter> cmeRouters, int start){
		
		for(int i=0;i<cmeRouters.size();i++){
			cmeRouters.get(i).setNumberLocalized(start+i+1);
			
		}
		return cmeRouters;
	}
	
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	@RequestMapping(value = "/ajax/serverside/{cmeId}/cmehuntgroups.json", method = RequestMethod.GET)
	public @ResponseBody DataTablesOutput<CmeVoiceHuntGroup> getRouterVoiceHuntGroups(@Valid DataTablesInput input, @PathVariable int cmeId) {
		return cmeVoiceHuntGroupService.findAllByRouter(input, cmeRouterService.findById(cmeId));
		
	}
	
	@JsonView(View.CmeDevice.class)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	@RequestMapping(value = "/ajax/serverside/{cmeId}/cmesccpdevices.json", method = RequestMethod.GET)
	public @ResponseBody DataTablesOutput<CmeDevice> getRouterSCCPDevices(@Valid DataTablesInput input, @PathVariable int cmeId) {
		return cmeDeviceService.findAllByRouter(input, cmeRouterService.findById(cmeId));
		
	}
	
	@JsonView(View.CmeSipDevice.class)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	@RequestMapping(value = "/ajax/serverside/{cmeId}/cmesipdevices.json", method = RequestMethod.GET)
	public @ResponseBody DataTablesOutput<CmeSipDevice> getRouterSIPDevices(@Valid DataTablesInput input, @PathVariable int cmeId, Locale locale) {
		DataTablesOutput<CmeSipDevice> result = cmeSipDeviceService.findAllByRouter(input, cmeRouterService.findById(cmeId));
		Iterator<CmeSipDevice> sipDevicesIterator = result.getData().iterator();
		while(sipDevicesIterator.hasNext()){
			CmeSipDevice sipDevice = sipDevicesIterator.next();
			
			String phoneTypeLocalized = "";
			if(sipDevice.getCustomSipDevice()!=null){
				if(sipDevice.getCustomSipDevice().getPhoneType()!=null){
					phoneTypeLocalized=messageSource.getMessage(sipDevice.getCustomSipDevice().getPhoneType().toString(), null, locale);
				}
				sipDevice.getCustomSipDevice().setPhoneTypeLocalized(phoneTypeLocalized);
			}
		
		}
		return result;
	}
	
	@JsonView(value={View.CmeDevice.class})
	@RequestMapping(value = {"/phone/sccp/{id}"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public String viewSCCPPhonePage(ModelMap model, @PathVariable(value = "id") int id) {
		logger.info("Requesting update SCCP PHONE page");
		
		CmeDevice device = cmeDeviceService.findByIdWithRelations(id);
		if(device.getCustomDevice()==null){
			CmeCustomDevice customDevice = new CmeCustomDevice();
			device.setCustomDevice(customDevice);
			customDevice.setCmeDevice(device);
		}
				
		Iterator<CmeExtMapStatus> extMapIterator = device.getLines().iterator();
		while(extMapIterator.hasNext()){
			CmeExtension extension = extMapIterator.next().getExtension();
			if(extension!=null){
				CmeCustomExtension customExtension = extension.getCustomExtension();
				if(customExtension==null){
					customExtension = new CmeCustomExtension();
					customExtension.setNumber(extension.getNumber());
					customExtension.setCmeExtension(extension);
					extension.setCustomExtension(customExtension);
				}
			}
		}
				
		model.addAttribute("device", device);
		logger.info(device);
		return "cme/routers/_modal";
	}
	
	
	/*@JsonView(value={View.CmeDevice.class})
	@RequestMapping(value = {"/phone/sccp/{id}/lines"}, method = RequestMethod.PUT, consumes="application/json")
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public String addLineSCCPPhonePage(ModelMap model, @RequestBody CmeDevice cmeDevice) {
		logger.info("Requesting add line for SCCP phone method");
		logger.info(cmeDevice);
				
		CmeCustomExtension customExtension = new CmeCustomExtension();

		CmeExtension extension = new CmeExtension();
		extension.setNumber("");
		extension.setCustomExtension(customExtension);
		
		customExtension.setCmeExtension(extension);
		
		CmeExtMapStatus extMap = new CmeExtMapStatus();
		extMap.setDevice(cmeDevice);
		extMap.setLineId(String.valueOf(cmeDevice.getLines().size()));
		extMap.setExtension(extension);
		
		cmeDevice.getLines().add(extMap);
		
		model.addAttribute("device", cmeDevice);
		
		logger.info(cmeDevice);
		
		/*User user = cmeCustomDevice.getUser().getId()!=null ? userService.getUserById(cmeCustomDevice.getUser().getId()) : null; 
		CmeDevice device = cmeDeviceService.findById(cmeCustomDevice.getCmeDevice().getId());
		
		cmeCustomDevice.setUser(user);
		device.setCustomDevice(cmeCustomDevice);
		cmeCustomDevice.setCmeDevice(device);
		cmeDeviceService.edit(device);*/
		
		
		
		
/*		return "cme/routers/_modal::deviceLinesContent";
		//return "cme/routers/_modal";
	}
	
	
	
	
	@JsonView(value={View.CmeDevice.class})
	@RequestMapping(value = {"/phone/sccp/{id}/lines/{delLineId}"}, method = RequestMethod.PUT, consumes="application/json")
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public String delLineSCCPPhonePage(ModelMap model, @RequestBody CmeDevice cmeDevice, @PathVariable(value = "delLineId") int delLineId) {
		logger.info("Requesting del line for SCCP phone method");
		logger.info(cmeDevice);
				
		Iterator<CmeExtMapStatus> iterator = cmeDevice.getLines().iterator();
		while(iterator.hasNext()){
			CmeExtMapStatus cmeExt = iterator.next();
			
			logger.info(cmeExt);
			if(Integer.parseInt(cmeExt.getLineId())==delLineId){
				logger.info("Delete {}",cmeExt);
				cmeExt.getExtension().getCustomExtension().setEnable(false);
			}
		}
		
		model.addAttribute("device", cmeDevice);
		
		logger.info(cmeDevice);
		
		return "cme/routers/_modal::deviceLinesContent";
	}
	
	*/
	
	
	
	
	
	
	
	
	@JsonView(value={View.CmeDevice.class})
	@RequestMapping(value = {"/phone/sccp"}, method = RequestMethod.PUT, consumes="application/json")
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public @ResponseBody CmeDevice saveUpdateSCCPPhonePage(@RequestBody CmeDevice cmeDevice) {
		logger.info("Requesting save custom SCCP phone method");
		
		CmeDevice device = cmeDeviceService.findById(cmeDevice.getId());
		
		User user = null;
		if(cmeDevice.getCustomDevice().getUser()!=null && cmeDevice.getCustomDevice().getUser().getId()!=null){
			user = userService.getUserById(cmeDevice.getCustomDevice().getUser().getId());
		}
				
		CmeCustomDevice customDevice = device.getCustomDevice();
		if(customDevice==null){
			customDevice = cmeDevice.getCustomDevice();
			customDevice.setCmeDevice(device);
			device.setCustomDevice(customDevice);
		}
		customDevice.setEnable(cmeDevice.getCustomDevice().isEnable());
		customDevice.setUser(user);
		customDevice.setUserType(user==null ? CmeUserType.ANONYMOUS : CmeUserType.USER);
		
		logger.trace("0. BaseDevice: {}", device);
		Iterator<CmeExtMapStatus> extMapIterator = device.getLines().iterator();
		while(extMapIterator.hasNext()){
			CmeExtension extension = extMapIterator.next().getExtension();
			logger.info("1. BaseExtension: {}", extension);
			if(extension!=null){
				
				logger.trace("2. GetDevice: {}", cmeDevice);
				CmeCustomExtension customExtension = null;
				Iterator<CmeExtMapStatus> customExtMapIterator = cmeDevice.getLines().iterator();
				while(customExtMapIterator.hasNext()){
					CmeExtMapStatus mapStatus = customExtMapIterator.next();
					logger.trace("3. GetCmeExtMapStatus: {}", mapStatus);
					logger.trace("4. BaseExtension.id({})==({})GetCmeExtMapStatus.getExtension.getId", extension.getId(), mapStatus.getExtension().getId());
					if(extension.getId().equals(mapStatus.getExtension().getId())){
						
						customExtension = mapStatus.getExtension().getCustomExtension();
						customExtension.setCmeExtension(extension);
						logger.trace("5. CustomExtension: {}",customExtension);
					}
				}
				
				logger.trace(extension);
				logger.trace("6. CustomExtension: {}",customExtension);
				if(extension.getCustomExtension()==null){
					extension.setCustomExtension(customExtension);
				} else {
					CmeCustomExtension custExt = extension.getCustomExtension();
					logger.trace(custExt);
					custExt.setEnable(customExtension.isEnable());
					custExt.setExternalPhoneNumberMask(customExtension.getExternalPhoneNumberMask());
					custExt.setLabel(customExtension.getLabel());
					custExt.setNumber(customExtension.getNumber());
					custExt.setPickupGroup(customExtension.getPickupGroup());
				}
			}
		}
						
		
		cmeDeviceService.edit(device);
		return device;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@JsonView(value={View.CmeSipDevice.class})
	@RequestMapping(value = {"/phone/sip/{id}"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public String viewSIPPhonePage(ModelMap model, @PathVariable(value = "id") int id, Locale locale) {
		logger.info("Requesting update SIP PHONE page");
		
		CmeSipDevice sipDevice = cmeSipDeviceService.findById(id);//findByIdWithRelations(id);
		if(sipDevice.getCustomSipDevice()==null){
			CmeCustomSipDevice customSipDevice = new CmeCustomSipDevice();
			sipDevice.setCustomSipDevice(customSipDevice);
			customSipDevice.setCmeSipDevice(sipDevice);
		}
				
		Iterator<CmeSipExtMapStatus> sipExtMapIterator = sipDevice.getLines().iterator();
		while(sipExtMapIterator.hasNext()){
			CmeSipExtension sipExtension = sipExtMapIterator.next().getExtension();
			if(sipExtension!=null){
				CmeCustomSipExtension customSipExtension = sipExtension.getCustomSipExtension();
				if(customSipExtension==null){
					customSipExtension = new CmeCustomSipExtension();
					customSipExtension.setNumber(sipExtension.getNumber());
					customSipExtension.setCmeSipExtension(sipExtension);
					sipExtension.setCustomSipExtension(customSipExtension);
				}
			}
		}
						
		model.addAttribute("sipPhoneType", Arrays.asList(CucmPhoneType.values()));
		model.addAttribute("sipPhoneTypeMap", i18nData(Arrays.asList(CucmPhoneType.values()), locale));
		model.addAttribute("device", sipDevice);
		logger.info(sipDevice);
		return "cme/routers/_modal_sip";
	}
	
	
	private Map<String, String> i18nData(List<CucmPhoneType> phoneTypes, Locale locale){
		Map<String, String> valueMap = new HashMap<String,String>();
		Iterator<CucmPhoneType> cucmPhoneTypeIterator = phoneTypes.iterator();
		while(cucmPhoneTypeIterator.hasNext()){
			CucmPhoneType phoneType = cucmPhoneTypeIterator.next();
			valueMap.put(phoneType.name(), messageSource.getMessage(phoneType.name().toString(),null, locale));
		}
		return valueMap;
	}
	
	@JsonView(value={View.CmeSipDevice.class})
	@RequestMapping(value = {"/phone/sip"}, method = RequestMethod.PUT, consumes="application/json")
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public @ResponseBody CmeSipDevice saveUpdateSIPPhonePage(@RequestBody CmeSipDevice cmeSipDevice) {
		logger.info("Requesting save custom SIP phone method");
		
		CmeSipDevice sipDevice = cmeSipDeviceService.findById(cmeSipDevice.getId());
		
		User user = null;
		if(cmeSipDevice.getCustomSipDevice().getUser()!=null && cmeSipDevice.getCustomSipDevice().getUser().getId()!=null){
			user = userService.getUserById(cmeSipDevice.getCustomSipDevice().getUser().getId());
		}
				
		CmeCustomSipDevice customSipDevice = sipDevice.getCustomSipDevice();
		if(customSipDevice==null){
			customSipDevice = cmeSipDevice.getCustomSipDevice();
			sipDevice.setCustomSipDevice(customSipDevice);
			customSipDevice.setCmeSipDevice(sipDevice);
		}
		customSipDevice.setEnable(cmeSipDevice.getCustomSipDevice().isEnable());
		customSipDevice.setUser(user);
		customSipDevice.setUserType(user==null ? CmeUserType.ANONYMOUS : CmeUserType.USER);
		customSipDevice.setPhoneType(cmeSipDevice.getCustomSipDevice().getPhoneType());
		
		Iterator<CmeSipExtMapStatus> sipExtMapIterator = sipDevice.getLines().iterator();
		while(sipExtMapIterator.hasNext()){
			CmeSipExtension sipExtension = sipExtMapIterator.next().getExtension();
			if(sipExtension!=null){
				
				CmeCustomSipExtension customSipExtension = null;
				Iterator<CmeSipExtMapStatus> customSipExtMapIterator = cmeSipDevice.getLines().iterator();
				while(customSipExtMapIterator.hasNext()){
					CmeSipExtMapStatus sipMapStatus = customSipExtMapIterator.next();
					if(sipExtension.getId().equals(sipMapStatus.getExtension().getId())){
						customSipExtension = sipMapStatus.getExtension().getCustomSipExtension();
						customSipExtension.setCmeSipExtension(sipExtension);
					}
				}
				
				if(sipExtension.getCustomSipExtension()==null){
					sipExtension.setCustomSipExtension(customSipExtension);
				} else {
					CmeCustomSipExtension sipCustExt = sipExtension.getCustomSipExtension();
					sipCustExt.setEnable(customSipExtension.isEnable());
					sipCustExt.setExternalPhoneNumberMask(customSipExtension.getExternalPhoneNumberMask());
					sipCustExt.setLabel(customSipExtension.getLabel());
					sipCustExt.setNumber(customSipExtension.getNumber());
					sipCustExt.setPickupGroup(customSipExtension.getPickupGroup());
				}
			}
		}
		cmeSipDeviceService.edit(sipDevice);
		return sipDevice;
	}
	
	@RequestMapping(value = {"/export/{id}"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public String viewExportModal(ModelMap model, @PathVariable(value = "id") int id, Locale locale) {
		logger.info("Requesting export phones for router");
		
		CmeRouter router = cmeRouterService.findById(id); 
		RouterExportDetail exportDetail = new RouterExportDetail();
		exportDetail.setSource(router);
				
		Map<Integer, String> axlPortDestinations = new HashMap<Integer, String>();
		List<CucmAxlPort> axlPorts = cucmAxlPortService.getAllCucmAxlPorts();
		Iterator<CucmAxlPort> axlPortsIterator =axlPorts.iterator();
		while(axlPortsIterator.hasNext()){
			CucmAxlPort axlPort = axlPortsIterator.next();
			axlPortDestinations.put(axlPort.getId(), axlPort.getName()+"("+axlPort.getFqdnName()+")");
		}
				
		model.addAttribute("exportDetail", exportDetail);
		model.addAttribute("axlPortDestinationsMap", axlPortDestinations);
		
		return "cme/routers/_modal_export";
	}
	
	
	@JsonView(View.RouterExportDetail.class)
	@RequestMapping(value = {"/exporttocucm"}, method = RequestMethod.PUT, consumes="application/json")
	@Secured({"ROLE_ADMIN"})
	public @ResponseBody RouterExportDetail exportRouterToCUCM(@RequestBody RouterExportDetail routerDetail) {
		logger.info("Requesting export phones to CUCM");
		routerDetail.setSource(cmeRouterService.findById(routerDetail.getSource().getId())); 
		routerDetail.setDestination(cucmAxlPortService.getCucmAxlPortById(routerDetail.getDestination().getId()));
		//logger.info(routerDetail);

		Iterator<CmeDevice> cmeDevicesIterator = cmeDeviceService.findAllForExportByRouterWithRelations(routerDetail.getSource()).iterator();
		while(cmeDevicesIterator.hasNext()){
			CmeDevice cmeDevice = cmeDevicesIterator.next();
			CucmExportDevice cucmDevice = migrationUtils.getCucmDeviceFromCmeDevice(cmeDevice);
			logger.info("CmeDevice: {}", cmeDevice);
			logger.info("CucmDevice: {}", cucmDevice);
			cucmDbService.cucmAddPhone(routerDetail.getDestination(), cucmDevice, routerDetail);
		}
		
		
		Iterator<CmeSipDevice> cmeSipDevicesIterator = cmeSipDeviceService.findAllForExportByRouterWithRelations(routerDetail.getSource()).iterator();
		while(cmeSipDevicesIterator.hasNext()){
			CmeSipDevice cmeSipDevice = cmeSipDevicesIterator.next();
			CucmExportDevice cucmDevice = migrationUtils.getCucmDeviceFromCmeSipDevice(cmeSipDevice);
			logger.info("CmeDevice: {}", cmeSipDevice);
			logger.info("CucmDevice: {}", cucmDevice);
			cucmDbService.cucmAddPhone(routerDetail.getDestination(), cucmDevice, routerDetail);
		}
			
		return routerDetail;
	}
}

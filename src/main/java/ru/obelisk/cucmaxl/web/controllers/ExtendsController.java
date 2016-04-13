package ru.obelisk.cucmaxl.web.controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j2;
import ru.obelisk.cucmaxl.backend.processors.JobProcessor;
import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.entity.Job;
import ru.obelisk.cucmaxl.database.models.entity.UploadFile;
import ru.obelisk.cucmaxl.database.models.entity.User;
import ru.obelisk.cucmaxl.database.models.entity.enums.JobFunction;
import ru.obelisk.cucmaxl.database.models.entity.enums.JobStatus;
import ru.obelisk.cucmaxl.database.models.entity.jobs.ChangeNumberDetail;
import ru.obelisk.cucmaxl.database.models.service.CucmAxlPortService;
import ru.obelisk.cucmaxl.database.models.service.JobService;
import ru.obelisk.cucmaxl.database.models.service.UploadFileService;
import ru.obelisk.cucmaxl.database.models.service.UserService;
import ru.obelisk.cucmaxl.web.controllers.utils.ChangeNumber;
import ru.obelisk.cucmaxl.web.databinding.AjaxOperationResult;

@Controller
@RequestMapping("/extends")
@Log4j2
public class ExtendsController {
	
	@Autowired private CucmAxlPortService cucmAxlPortService;
	@Autowired private UploadFileService uploadFileService;
		
	@Autowired private UserService userService;
	@Autowired private JobService jobsService;
	
	@Autowired private JobProcessor jobProcessor;
	
	
		
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String indexPage(Model model) {
		log.info("Requesting extends page");
		return "extends/index";
	}
	
	@RequestMapping(value = {"/changenum"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewCreateCmeLocationPage(Model model) {
		log.info("Requesting changenum modal");
		
		Map<Integer, String> axlPortDestinations = new HashMap<Integer, String>();
		List<CucmAxlPort> axlPorts = cucmAxlPortService.findAll();
		Iterator<CucmAxlPort> axlPortsIterator =axlPorts.iterator();
		while(axlPortsIterator.hasNext()){
			CucmAxlPort axlPort = axlPortsIterator.next();
			axlPortDestinations.put(axlPort.getId(), axlPort.getName()+" ("+axlPort.getFqdnName()+")");
		}
		
		Map<Integer, String> uploadSources = new HashMap<Integer, String>();
		List<UploadFile> uploadFiles = uploadFileService.findAll();
		Iterator<UploadFile> uploadsFileIterator =uploadFiles.iterator();
		while(uploadsFileIterator.hasNext()){
			UploadFile uploadFile = uploadsFileIterator.next();
			uploadSources.put(uploadFile.getId(), uploadFile.getName()+" ("+uploadFile.getDescription()+")");
		}
		
		Job job = new Job();
		job.setName("Change numbers");
		job.setDescription("Change old numbers on new numbers");
				
		ChangeNumber chNum = new ChangeNumber();
		chNum.setJob(job);
		
		model.addAttribute("changeNum", chNum);
		model.addAttribute("axlPortDestinationsMap", axlPortDestinations);
		model.addAttribute("uploadSourceMap", uploadSources);
		
		return "extends/_modal_changenum";
	}
	
	@RequestMapping(value = {"/changenum"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public @ResponseBody AjaxOperationResult changeNumberAction(final ModelMap model,
								@Valid @ModelAttribute("changeNum") final ChangeNumber changeNumber, 
								RedirectAttributes redirectAttributes, HttpServletRequest request,
								@AuthenticationPrincipal User activeUser) {
		log.info("Requesting change number method");
		
		boolean error = false;
		
		AjaxOperationResult ajaxOperationResult = new AjaxOperationResult();
		changeNumber.setDatasource(uploadFileService.findById(changeNumber.getDatasource()!=null ? changeNumber.getDatasource().getId() : 0));
		changeNumber.setDestination(cucmAxlPortService.findById(changeNumber.getDestination()!=null ? changeNumber.getDestination().getId() : 0));
		
		if(changeNumber.getPartition()==null || changeNumber.getPartition().isEmpty()){
			ajaxOperationResult.setStatus(-1);
			ajaxOperationResult.setMessage("Operation failed. Partition must be specified.");
			error = true;
		}
		if(changeNumber.getDatasource()==null){
			ajaxOperationResult.setStatus(-1);
			ajaxOperationResult.setMessage("Operation failed. Specified CSV file not found.");
			error = true;
		}
		if(changeNumber.getDestination()==null){
			ajaxOperationResult.setStatus(-1);
			ajaxOperationResult.setMessage("Operation failed. Specified CUCM AXL port not found.");
			error = true;
		}
		
		if(!error){
			
			/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null)
			{
				WebSecurityExpressionRoot sec = new WebSecurityExpressionRoot(authentication, filterInvocation);
				sec.setTrustResolver(new AuthenticationTrustResolverImpl());
				localPrincipal.getUser(sec.getAuthentication(), request.getSession());
			}*/
			
			
			
			Job job = changeNumber.getJob();
			if(job==null){
				job = new Job();
			}
			job.setCreateDate(LocalDateTime.now());
			if(job.getName().isEmpty()) job.setName("Change numbers");
			if(job.getDescription().isEmpty()) job.setDescription("Change old numbers on new numbers");
			job.setStatus(JobStatus.HOLD);
			job.setJobFunction(JobFunction.CHANGENUMBER);
			
			
			ChangeNumberDetail changeNumberDetail = new ChangeNumberDetail();
			changeNumberDetail.setDatasource(changeNumber.getDatasource());
			changeNumberDetail.setDestination(changeNumber.getDestination());
			changeNumberDetail.setPartition(changeNumber.getPartition());
			changeNumberDetail.setJob(job);
			job.setUploadFile(changeNumberDetail.getDatasource());
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			job.setUser(userService.getUserByUsername(authentication.getName()));
						
			job.setChangeNumberDetail(changeNumberDetail);
			
			jobsService.add(job);
			
			jobProcessor.processJob(job);
	        
	        
	        
	    }
		
		return ajaxOperationResult;
	}	
}

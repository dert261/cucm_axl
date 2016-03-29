package ru.obelisk.cucmaxl.web.controllers;

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
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.access.annotation.Secured;
import ru.obelisk.cucmaxl.annotations.DatatableCriterias;
import ru.obelisk.cucmaxl.database.models.entity.ScheduleJob;
import ru.obelisk.cucmaxl.database.models.service.ScheduleJobService;
import ru.obelisk.cucmaxl.database.models.views.View;
import ru.obelisk.cucmaxl.web.ui.datatables.DataSet;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesResponse;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

@Controller
@RequestMapping("/scheduler")
public class SchedulerController {
	
	private static Logger logger = LogManager.getLogger(SchedulerController.class);
	
	@Autowired private ScheduleJobService scheduleJobService;
			
	@RequestMapping(value = {"/search"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody List<Select2Result> searchJobs(@RequestParam String searchString) {
		logger.info("Requesting search scheduler jobs with term: {}",searchString);
		return scheduleJobService.findScheduleJobByTerm(searchString);
	}
	
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String indexPage(Model model) {
		logger.info("Requesting  scheduler jobs page");
		ScheduleJob scheduleJob = new ScheduleJob();
		model.addAttribute("scheduleJob", scheduleJob);
		model.addAttribute("scheduleJobAll", scheduleJobService.getAllScheduleJobs());
		return "scheduler/index";
	}
	
	@JsonView(value={View.ScheduleJob.class})
	@RequestMapping(value = {"/ajax/serverside/schedulerjob.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DatatablesResponse<ScheduleJob> schedulerJobsDatatables(
			@DatatableCriterias DatatablesCriterias criterias, 
			Model model) {
		logger.info("Requesting scheduler jobs data for table on index page");
		List<ScheduleJob> scheduleJobs = scheduleJobService.findScheduleJobWithDatatablesCriterias(criterias);
		Long count = scheduleJobService.getTotalCount();
		Long countFiltered = scheduleJobService.getFilteredCount(criterias);
		return DatatablesResponse.build(new DataSet<ScheduleJob>(scheduleJobs,count,countFiltered), criterias);
	}
	
	@JsonView(value={View.ScheduleJob.class})
	@RequestMapping(value = {"/ajax/clientside/schedulerjob.json"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public @ResponseBody DatatablesResponse<ScheduleJob> scheduleJobsDataClientSide(Model model) {
		logger.info("Requesting CUCM AXL port data for table on index page");
		List<ScheduleJob> scheduleJobs = scheduleJobService.getAllScheduleJobs();
		return DatatablesResponse.clientSideBuild(scheduleJobs);
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewCreateNewScheduleJobPage(Model model) {
		logger.info("Requesting create scheduleJob page");
		ScheduleJob scheduleJob = new ScheduleJob();
		model.addAttribute("scheduleJob", scheduleJob);
		return "scheduler/create";
	}
		
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public String saveCreateNewScheduleJobPage(final ModelMap model, 
									@Valid @ModelAttribute("scheduleJob") final ScheduleJob scheduleJob, 
									final BindingResult bindingResult) {
		logger.info("Requesting add sheduler job method");
		if(bindingResult.hasErrors()){
			return "scheduler/create";
		}
		scheduleJobService.add(scheduleJob);
		
		model.clear();
        return "redirect:/scheduler/index.html";
	}
	
	@RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewUpdateScheduleJobPage(ModelMap model, @PathVariable(value = "id") int id) {
		logger.info("Requesting update schedule job page");
		ScheduleJob scheduleJob = scheduleJobService.getScheduleJobById(id);
		model.addAttribute("scheduleJob", scheduleJob);
		return "scheduler/update";
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.PUT)
	@Secured("ROLE_ADMIN")
	public String saveUpdateScheduleJobPage(final ModelMap model, 
									@Valid @ModelAttribute("scheduleJob") final ScheduleJob scheduleJob, 
									final BindingResult bindingResult,
									SessionStatus status) {
		logger.info("Requesting save update scheduler job method");
		if(bindingResult.hasErrors()){
			return "scheduler/update";
		}
		scheduleJobService.edit(scheduleJob);
		
		status.setComplete();
		return "redirect:/scheduler/index.html";
	}
	
	@RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
	@Secured("ROLE_ADMIN")
	public String deleteScheduleJob(int id, SessionStatus status) {
		logger.info("Requesting delete scheduler job");
		scheduleJobService.delete(id);
		status.setComplete();
		return "redirect:/scheduler/index.html";
	}
}

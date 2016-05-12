package ru.obelisk.cucmaxl.web.controllers;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;
import ru.obelisk.database.models.entity.Job;
import ru.obelisk.database.models.entity.JobResult;
import ru.obelisk.database.models.service.JobService;
import ru.obelisk.database.models.views.View;
import ru.obelisk.cucmaxl.backend.processors.job.JobProcessor;
import ru.obelisk.cucmaxl.web.databinding.AjaxOperationResult;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

@Controller
@RequestMapping("/jobs")
@Log4j2
public class JobsController {
	
	@Autowired private JobService jobsService;
	@Autowired private JobProcessor jobProcessor;
	
	@JsonView(value={View.Job.class})
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String indexPage(Model model) {
		log.info("Requesting jobs page");
		return "jobs/index";
	}
		
	@JsonView(value={View.Job.class})
	@RequestMapping(value = "/ajax/serverside/jobs.json", method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public @ResponseBody DataTablesOutput<Job> getJobs(@Valid DataTablesInput input) {
		DataTablesOutput<Job> output = jobsService.findAllWithRelations(input);
		output.setData(idGenerate(output.getData(),input.getStart()));
		return output;
	}
		
	private List<Job> idGenerate(List<Job> files, int start){
		for(int i=0;i<files.size();i++){
			files.get(i).setNumberLocalized(start+i+1);
		}
		return files;
	}
	
	@JsonView(value={View.Job.class})
	@RequestMapping(value = {"/detail/{id}"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String detailJobPage(ModelMap model, @PathVariable(value = "id") int id) {
		log.info("Requesting job detail page");
		model.addAttribute("job", jobsService.findById(id));
		return "jobs/detail";
	}
	
	/*@JsonView(value={View.Job.class})
	@RequestMapping(value = "{id}/ajax/serverside/jobsdetail.json", method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public @ResponseBody DataTablesOutput<Job> getJobs(@Valid DataTablesInput input, @PathVariable(value = "id") int id) {
		DataTablesOutput<Job> output = jobsService.findAllWithRelations(input);
		output.setData(idGenerate(output.getData(),input.getStart()));
		return output;
	}*/
	
	
	/*@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String viewCreateCmeLocationPage(Model model) {
		log.info("Requesting upload new file modal");
		UploadFile uploadFile = new UploadFile();
		model.addAttribute("uploadFile", uploadFile);
		return "uploads/_modal_upload";
	}
		
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public @ResponseBody AjaxOperationResult saveCreateCmeLocationPage(final ModelMap model, 
									@Valid @ModelAttribute("uploadFile") final UploadFile uploadFile, 
									RedirectAttributes redirectAttributes) {
		log.info("Requesting save upload new file method");
		
		AjaxOperationResult ajaxOperationResult = new AjaxOperationResult();
		
		
		if (!uploadFile.getUploadedfile().isEmpty()) {
			if(uploadFile.getUploadedfile().getSize()>UPLOAD_SIZE_LIMIT){
				ajaxOperationResult.setStatus(-1);
				ajaxOperationResult.setMessage("You failed to upload " + uploadFile.getName() + ". File size exceeds the allowed limit of "+(UPLOAD_SIZE_LIMIT/1024/1024)+"MB.");
				log.info("You failed to upload " + uploadFile.getName() + ". File size exceeds the allowed limit of "+(UPLOAD_SIZE_LIMIT/1024/1024)+"MB.");
			} 	
			else if(uploadFile.getUploadedfile().getContentType().equals("application/vnd.ms-excel") || uploadFile.getUploadedfile().getContentType().equals("text/csv")){
				try {
					String fileName=DigestUtils.md5Hex(uploadFile.getUploadedfile().getOriginalFilename()+(new Date()));
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File(ROOT + "/" + fileName)));
	                FileCopyUtils.copy(uploadFile.getUploadedfile().getInputStream(), stream);
					stream.close();
					
					uploadFile.setFilename(fileName);
					uploadFile.setFilepath(ROOT);
					uploadFileService.add(uploadFile);
					ajaxOperationResult.setStatus(0);
					ajaxOperationResult.setMessage("You successfully uploaded " + uploadFile.getName() + "!");
					log.info("You successfully uploaded " + uploadFile.getName() + "!");
				}
				catch (Exception e) {
					ajaxOperationResult.setStatus(-1);
					ajaxOperationResult.setMessage("You failed to upload " + uploadFile.getName() + " => " + e.getMessage());
					log.info("You failed to upload " + uploadFile.getName() + " => " + e.getMessage());
				}
			} else {
				ajaxOperationResult.setStatus(-1);
				ajaxOperationResult.setMessage("You failed to upload " + uploadFile.getName() + " because the file was not csv");
				log.info("You failed to upload " + uploadFile.getName() + " because the file was not csv");
			}
		}
		else {
			ajaxOperationResult.setStatus(-1);
			ajaxOperationResult.setMessage("You failed to upload " + uploadFile.getName() + " because the file was empty");
			log.info("You failed to upload " + uploadFile.getName() + " because the file was empty");
		}
				
        return ajaxOperationResult;
	}
	*/
	
	@RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
	@Secured("ROLE_ADMIN")
	public String deleteJob(int id, SessionStatus status) {
		log.info("Requesting delete job");
		
		Job job = jobsService.findById(id);
		if(job!=null){
			Iterator<JobResult> jobResIterator = job.getResults().iterator();
			while(jobResIterator.hasNext()){
				JobResult jobResult = jobResIterator.next();
				try{
					File file = new File(jobResult.getLogfilepath()+"/"+jobResult.getLogfilename());
					if(file.exists() && file.delete()){
		        		log.info(file.getName() + " is deleted!");
		    		}else if(file.exists() && !file.canWrite()){
						log.info("File not can write. File open in other process");
		    		}else if(!file.exists()){
		    			log.info("File not exist. Delete DB record");
		    		} else {
		    			log.info("File not delete.");
		    		}
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}
			}
			jobsService.delete(id);
		}
		status.setComplete();
		return "redirect:/jobs/";
	}
	
	@RequestMapping(value = {"/run"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public @ResponseBody AjaxOperationResult runJob(int id, RedirectAttributes redirectAttributes) {
		log.info("Requesting run job");
		Job job = jobsService.findById(id);
		return jobProcessor.processJob(job);
	}
	
	@RequestMapping(value = {"/stop"}, method = RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public @ResponseBody AjaxOperationResult stopJob(int id, RedirectAttributes redirectAttributes) {
		log.info("Requesting stop job: {}");
		Job job = jobsService.findById(id);
		return jobProcessor.stopJob(job);
	}
}

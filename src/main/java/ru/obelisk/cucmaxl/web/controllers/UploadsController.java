package ru.obelisk.cucmaxl.web.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j2;
import ru.obelisk.cucmaxl.database.models.entity.UploadFile;
import ru.obelisk.cucmaxl.database.models.service.UploadFileService;
import ru.obelisk.cucmaxl.web.databinding.AjaxOperationResult;
import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

@Controller
@RequestMapping("/uploads")
@Log4j2
public class UploadsController {
	
	@Value("${upload.directory}") private String ROOT;
	@Value("${upload.maxsize}") private int UPLOAD_SIZE_LIMIT;
	
	@Autowired private UploadFileService uploadFileService;
		
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String indexPage(Model model) {
		log.info("Requesting upload files page");
		return "uploads/index";
	}
	
	
	@RequestMapping(value = "/ajax/serverside/uploadfiles.json", method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public @ResponseBody DataTablesOutput<UploadFile> getUploadedFiles(@Valid DataTablesInput input) {
		DataTablesOutput<UploadFile> output = uploadFileService.findAll(input);
		output.setData(idGenerate(output.getData(),input.getStart()));
		return output;
	}
		
	private List<UploadFile> idGenerate(List<UploadFile> files, int start){
		for(int i=0;i<files.size();i++){
			files.get(i).setNumberLocalized(start+i+1);
		}
		return files;
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
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
	
	@RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
	@Secured("ROLE_ADMIN")
	public String deleteCmeLocation(int id, SessionStatus status) {
		log.info("Requesting delete uploaded file");
		
		UploadFile uploadFile = uploadFileService.findById(id);
		if(uploadFile!=null){
			try{
				File file = new File(uploadFile.getFilepath()+"/"+uploadFile.getFilename());
				if(file.exists() && file.delete()){
	        		log.info(file.getName() + " is deleted!");
	    			uploadFileService.delete(id);
				}else if(file.exists() && !file.canWrite()){
	    			uploadFileService.delete(id);
	    			log.info("File not can write. File open in other process");
	    		}else if(!file.exists()){
	    			uploadFileService.delete(id);
	    			log.info("File not exist. Delete DB record");
	    		} else {
	    			log.info("File not delete.");
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
		}
		status.setComplete();
		return "redirect:/uploads/";
	}
}

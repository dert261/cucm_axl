package ru.obelisk.cucmaxl.web.controllers.monitor;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.database.models.entity.CallDetailRecord;
import ru.obelisk.database.models.service.CallDetailRecordService;
import ru.obelisk.database.models.views.View;
import ru.obelisk.database.view.CdrSearchForm;
import ru.obelisk.database.view.DataTablesCdrInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/monitor/cdrs")
@Log4j2
public class CdrController {
	
	@Autowired private CallDetailRecordService cdrService;
	//@Autowired private CallManagementRecordService cmrService;
		
	@JsonView(View.CdrView.class)
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String indexPage(Model model) {
		log.info("Requesting cdr page");
		model.addAttribute("cdrSearchForm", new CdrSearchForm());
		return "monitor/cdrs/index";
	}
		
	@JsonView(View.CdrView.class)
	@RequestMapping(value = "/ajax/serverside/cdrs.json", method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public @ResponseBody DataTablesOutput<CallDetailRecord> getCollectors(@Valid DataTablesCdrInput input) {
		DataTablesOutput<CallDetailRecord> output = cdrService.findAllWithRelations(input);
		output.setData(idGenerate(output.getData(), input.getStart()));
		return output;
	}
		
	private List<CallDetailRecord> idGenerate(List<CallDetailRecord> files, int start){
		for(int i=0;i<files.size();i++){
			files.get(i).setNumberLocalized(start+i+1);
		}
		return files;
	}
}

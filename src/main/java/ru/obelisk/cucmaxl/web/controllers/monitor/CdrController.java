package ru.obelisk.cucmaxl.web.controllers.monitor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.web.controllers.utils.EnumDictionaryEntry;
import ru.obelisk.database.models.entity.CallDetailRecord;
import ru.obelisk.database.models.entity.enums.CallTerminationCauseCode;
import ru.obelisk.database.models.service.CallDetailRecordService;
import ru.obelisk.database.models.views.View;
import ru.obelisk.database.view.CdrSearchCriteria;
import ru.obelisk.database.view.DataTablesCdrInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/monitor/cdrs")
@Log4j2
public class CdrController {
	
	@Autowired private CallDetailRecordService cdrService;
	@Autowired private MessageSource messageSource;
	
	private final static String CAUSE_CODE_MESSAGE_PREFIX = "enums.callTerminationCauseCode.";
	private final static String CAUSE_CODE_POPOVER_TITLE_PREFIX = "enums.callTerminationCauseCode.popover.title.";
	private final static String CAUSE_CODE_POPOVER_MESSAGE_PREFIX = "enums.callTerminationCauseCode.popover.message.";
		
	@JsonView(View.CdrView.class)
	@RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String indexPage(Model model, Locale locale) {
		log.info("Requesting cdr page");
		CdrSearchCriteria prereqSearch = new CdrSearchCriteria();
		prereqSearch.setStartTime((LocalDateTime.now()).minusDays(1));
		prereqSearch.setStopTime(LocalDateTime.now());
		
		model.addAttribute("cdrSearchCriteria", prereqSearch);
		model.addAttribute("callTerminationCauseCodes", getCauseCodeDictionary(locale));
		return "monitor/cdrs/index";
	}
	
	private Set<EnumDictionaryEntry> getCauseCodeDictionary(Locale locale){
		Set<EnumDictionaryEntry> dict = new HashSet<>();
		for(CallTerminationCauseCode code : CallTerminationCauseCode.values()){
			EnumDictionaryEntry entry = new EnumDictionaryEntry();
			entry.setLongValue(code.getNumber());
			entry.setTextValue(messageSource.getMessage(CAUSE_CODE_MESSAGE_PREFIX+code.toString(), null, code.toString(), locale));
			entry.setPopover(true);
			entry.setPopoverTitle(messageSource.getMessage(CAUSE_CODE_POPOVER_TITLE_PREFIX+code.toString(), null, code.toString(), locale));
			entry.setPopoverMessage(messageSource.getMessage(CAUSE_CODE_POPOVER_MESSAGE_PREFIX+code.toString(), null, code.toString(), locale));
			dict.add(entry);
		}
		return dict;
	}
		
	@JsonView(View.CdrView.class)
	@RequestMapping(value = "/ajax/serverside/cdrs.json", method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public @ResponseBody DataTablesOutput<CallDetailRecord> getCollectors(@Valid DataTablesCdrInput input) {
		log.info(input);			
		DataTablesOutput<CallDetailRecord> output = cdrService.findAllWithRelations(input);
		output.setData(idGenerate(output.getData(), input.getStart()));
		return output;
	}
		
	private List<CallDetailRecord> idGenerate(List<CallDetailRecord> collection, int start){
		if(collection==null) return Collections.emptyList();
		
		AtomicInteger index = new AtomicInteger(start);
		collection.forEach((n)->{
			if(n!=null) n.setNumberLocalized(index.incrementAndGet());
		});
		return collection;
	}
	
	@RequestMapping(value = {"/detail/{id}"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public String viewDetailCDRPage(ModelMap model, @PathVariable(value = "id") int id, Locale locale) {
		log.info("Requesting detail CDR modal page");
				
		model.addAttribute("cdrRecord", cdrService.findById(id));
		model.addAttribute("callTerminationCauseCodes", getCauseCodeDictionary(locale));
		
		return "monitor/cdrs/_modal_detail";
	}
}

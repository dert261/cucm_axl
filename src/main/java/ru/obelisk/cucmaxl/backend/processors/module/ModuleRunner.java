package ru.obelisk.cucmaxl.backend.processors.module;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cisco.cme.custom.xml.Report;

import lombok.extern.log4j.Log4j2;
import ru.obelisk.database.models.entity.Module;
import ru.obelisk.database.models.entity.enums.ModuleStatus;
import ru.obelisk.database.models.service.ModuleService;
import ru.obelisk.cucmaxl.utils.ObeliskStringUtils;
import ru.obelisk.cucmaxl.web.databinding.AjaxOperationResult;

@Component
@Log4j2
public class ModuleRunner {
	@Autowired private ModuleService moduleService;
	@Autowired private ModuleRepo moduleRepo;
	
	
	
	public AjaxOperationResult runModule(Module module){
		AjaxOperationResult result = new AjaxOperationResult();
		log.info("Try to start module");
		if(module==null){
			result.setStatus(-1);
			result.setMessage("Job must be not null!!!");
			return result;
		}
		if(!moduleRepo.contain(module)){
			Module persistModule = moduleService.findById(module.getId());
			Process process;
			try {
				process = runProcess(module);
				if(process!=null){
					persistModule.setProcess(process);
					persistModule.setStatus(ModuleStatus.RUN);
					moduleService.edit(persistModule);
					moduleRepo.add(persistModule);
					result.setStatus(0);
					result.setMessage("Module \""+persistModule.getName()+"\" run");
				} else {
					result.setStatus(-1);
					result.setMessage("Module \""+persistModule.getName()+"\" run (non IO exception rised), but get nulling process context.");
				}
			} catch (IOException e) {
				result.setStatus(-1);
				result.setMessage("Module \""+persistModule.getName()+"\" not runnig. IO exception is rised. "+ObeliskStringUtils.getTraceToLog(e));
			}
			
		} else {
			result.setStatus(-1);
			result.setMessage("Module \""+module.getName()+"\" now is running");
		}
		return result;
	}
	
	public AjaxOperationResult stopModule(Module module){
		AjaxOperationResult result = new AjaxOperationResult();
		log.info("Try to stop module");
		if(module==null){
			result.setStatus(-1);
			result.setMessage("Job must be not null!!!");
			return result;
		}
		if(moduleRepo.contain(module)){
			Module persistModule = moduleService.findById(module.getId());
			try {
				Module stopMod = moduleRepo.get(module);
				log.info(stopMod);
				if(stopMod!=null && killProcess(stopMod)){
					persistModule.setStatus(ModuleStatus.STOPPED);
					moduleService.edit(persistModule);
					moduleRepo.remove(stopMod);
					result.setStatus(0); 
					result.setMessage("Module \""+persistModule.getName()+"\" stopped");
				} else {
					result.setStatus(-1);
					result.setMessage("Module \""+persistModule.getName()+"\" not stopped. Exception not raised, but wait for stop process timeout is exced.");
				}
			} catch (InterruptedException e) {
				result.setStatus(-1);
				result.setMessage("Module \""+persistModule.getName()+"\" not stopped. Raised exception: InterruptedException: "+ObeliskStringUtils.getTraceToLog(e));
			}
		} else {
			result.setStatus(-1);
			result.setMessage("Module \""+module.getName()+"\" not found in running modules");
		}
		return result;
	}
	
	private Process runProcess(Module module) throws IOException{
		final ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", module.getFilepath()+"/"+module.getFilename());
		processBuilder.directory(new File(module.getFilepath()));
		Process process = null;
		try {
			process = processBuilder.start();
		} catch (IOException e) {
			ObeliskStringUtils.getTraceToLog(e);
			throw e;
		} 
		return process;
	}
	
	private boolean killProcess(Module module) throws InterruptedException {
		Process proc = module.getProcess();
		boolean retval=false;
		if(proc!=null){
			if(proc.isAlive()){
				try {
					log.info("Try to kill process");
					retval = proc.destroyForcibly().waitFor(5, TimeUnit.SECONDS);
					
				} catch (InterruptedException e) {
					log.warn(ObeliskStringUtils.getTraceToLog(e));
					throw e;
				}
			} else {
				log.info("Process is not active.");
				retval=true;
			}	
		}
		return retval;
	}
}

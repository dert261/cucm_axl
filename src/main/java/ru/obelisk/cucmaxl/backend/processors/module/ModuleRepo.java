package ru.obelisk.cucmaxl.backend.processors.module;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.ToString;
import ru.obelisk.database.models.entity.Module;

@Component
@ToString
public class ModuleRepo {
	
	@Getter
	private Set<Module> modules = new HashSet<>(0);
	
	synchronized public void add(Module module){
		if(!modules.contains(module)){
			if(module.getType()!=null){
				modules.add(module);
			}	
		}
	}
	
	synchronized public void remove(Module module){
		if(modules.contains(module)){
			modules.remove(module);
		}
	}
	
	synchronized public Module get(Module module){
		for(Module mod : modules){
			if(mod.equals(module)){
				return mod;
			}
		}
		return null;
	}
	
	synchronized public boolean contain(Module module){
		return modules.contains(module);
	}
	
	synchronized public void updateModule(Module module){
		for(Module mod : modules){
			if(mod.equals(module)){
				mod=module;
			}
				
		}
	}
}

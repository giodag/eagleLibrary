package com.univaq.eaglelibrary.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.model.Module;
import com.univaq.eaglelibrary.model.User;

@Component
public class ConvertModule {

	@Autowired
	private ConvertUser convertUser;
	
	public List<ModuleDTO> convert(List<Module> modules){
		List<ModuleDTO> modulesDTO = null;
		if(modules != null && !modules.isEmpty()) {
			modulesDTO = new ArrayList<ModuleDTO>();
			for (Module module : modules) {
				modulesDTO.add(convert(module));
			}
		}
		return modulesDTO;
	}
	
	public Module convert(ModuleDTO moduleDTO) {
		
		Module module = null;
		if(moduleDTO != null) {
			module = new Module();
			
			module.setComment(moduleDTO.getComment());
			module.setCreationDate(moduleDTO.getCreationDate());
			module.setId(moduleDTO.getId());
			module.setStatus(moduleDTO.getStatus());
			module.setYearOfTheStudy(moduleDTO.getYearOfTheStudy());
			module.setUser(convertUser.convert(moduleDTO.getUser()));
		}	
		return module;
	}
	
	public ModuleDTO convert(Module module) {
		
		ModuleDTO moduleDTO = null;
		if(module != null) {
			moduleDTO = new ModuleDTO();
			
			moduleDTO.setComment(module.getComment());
			moduleDTO.setCreationDate(module.getCreationDate());
			moduleDTO.setId(module.getId());
			moduleDTO.setStatus(module.getStatus());
			moduleDTO.setYearOfTheStudy(module.getYearOfTheStudy());
			moduleDTO.setUser(convertUser.convertNoTranscription(module.getUser()));
		}
		return moduleDTO;
	}
}

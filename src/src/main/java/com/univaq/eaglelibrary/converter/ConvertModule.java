package com.univaq.eaglelibrary.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.model.Module;

@Component
public class ConvertModule {

	@Autowired
	private ConvertUser convertUser;
	
	public Module convert(ModuleDTO moduleDTO) {
		
		Module module = null;
		if(moduleDTO != null) {
			module = new Module();
			
			module.setCode(moduleDTO.getCode());
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
			
			moduleDTO.setCode(module.getCode());
			moduleDTO.setComment(module.getComment());
			moduleDTO.setCreationDate(module.getCreationDate());
			moduleDTO.setId(module.getId());
			moduleDTO.setStatus(module.getStatus());
			moduleDTO.setYearOfTheStudy(module.getYearOfTheStudy());
			moduleDTO.setUser(convertUser.convert(module.getUser()));
		}
		return moduleDTO;
	}
}

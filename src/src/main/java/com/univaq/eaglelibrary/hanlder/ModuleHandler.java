package com.univaq.eaglelibrary.hanlder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.cj.util.StringUtils;
import com.univaq.eaglelibrary.converter.ConvertModule;
import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.model.Module;
import com.univaq.eaglelibrary.model.User;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.repository.ModuleRepository;
import com.univaq.eaglelibrary.repository.UserRepository;

@Component
public class ModuleHandler {
	
	@Autowired
	private ConvertModule convertModule;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private final Logger LOGGER = LoggerFactory.getLogger(ModuleHandler.class);

	public ResultDTO createUpdateModule(ModuleDTO moduleDTO) throws MandatoryFieldException {
		checkMandatory(moduleDTO);
		ModuleDTO moduleRead = readModule(moduleDTO);
		Module module = null;
		
		if(moduleRead != null) {
			moduleRead.setComment(!StringUtils.isNullOrEmpty(moduleDTO.getComment()) ? moduleDTO.getComment() : moduleRead.getComment());
			moduleRead.setCreationDate(moduleDTO.getCreationDate() != null ? moduleDTO.getCreationDate() : moduleRead.getCreationDate());
			moduleRead.setId(moduleDTO.getId() != null ? moduleDTO.getId() : moduleRead.getId());
			moduleRead.setStatus(!StringUtils.isNullOrEmpty(moduleDTO.getStatus()) ? moduleDTO.getStatus() : moduleRead.getStatus());
			moduleRead.setYearOfTheStudy(moduleDTO.getYearOfTheStudy() != null ? moduleDTO.getYearOfTheStudy() : moduleRead.getYearOfTheStudy());
			module = convertModule.convert(moduleRead);
			
		} else {
			module = convertModule.convert(moduleDTO);
		}
		
		Module result = moduleRepository.save(module);
		
		ResultDTO resultDTO = null;
		if (result != null) {
			resultDTO = new ResultDTO();
			resultDTO.setSuccessfullyOperation(Boolean.TRUE);
		}
		
		return resultDTO;
	}

	public ModuleDTO readModule(ModuleDTO moduleDTO) {
		Module module = null;
		if (moduleDTO.getId() != null) {
			module = moduleRepository.findOne(moduleDTO.getId());
		} else {
			if(moduleDTO.getUser() != null) {
				User user = userRepository.findByUsername(moduleDTO.getUser().getUsername());
				module = moduleRepository.findModuleByUser(user.getId());
			}
		}
		return convertModule.convert(module);
	}

	private void checkMandatory(ModuleDTO moduleDTO) throws MandatoryFieldException {
		if(moduleDTO == null || moduleDTO.getCreationDate() == null || StringUtils.isNullOrEmpty(moduleDTO.getComment()) 
				|| StringUtils.isNullOrEmpty(moduleDTO.getStatus()) || moduleDTO.getYearOfTheStudy() == null
				|| moduleDTO.getUser() == null) {
			throw new MandatoryFieldException();
		}
	}

}

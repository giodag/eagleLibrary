package com.univaq.eaglelibrary.controllerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.univaq.eaglelibrary.controller.ModuleController;
import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.hanlder.ModuleHandler;
import com.univaq.eaglelibrary.persistence.exceptions.CreateModuleException;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;

@Service
public class ModuleControllerImpl implements ModuleController {

	private final Logger logger = LoggerFactory.getLogger(ModuleControllerImpl.class);

	@Autowired
	private ModuleHandler moduleHandler;

	public ResultDTO submitModule(ModuleDTO moduleDTO) throws MandatoryFieldException, CreateModuleException {
		logger.debug("start submitModule");
		ResultDTO resultDTO = null;
		ModuleDTO moduleRead = this.moduleHandler.readModule(moduleDTO);
		if(moduleRead != null) {
			throw new CreateModuleException("Module already sent");
		}
		resultDTO = this.moduleHandler.createUpdateModule(moduleDTO);
		logger.debug("finish submitModule");
		return resultDTO;
	}

	public ModuleDTO getModule(ModuleDTO moduleDTO) {
		logger.debug("start getModule");
		ModuleDTO moduleDTORead = this.moduleHandler.readModule(moduleDTO);
		logger.debug("finish getModule");
		return moduleDTORead;
	}

	public ResultDTO validateModule(ModuleDTO moduleDTO) throws MandatoryFieldException {
		logger.debug("start validateModule");
		ResultDTO resultDTO = null;
		resultDTO = this.moduleHandler.createUpdateModule(moduleDTO);
		logger.debug("finish validateModule");
		return resultDTO;
	}

}

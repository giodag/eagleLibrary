package com.univaq.eaglelibrary.controllerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controller.ModuleController;
import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.hanlder.ModuleHandler;

public class ModuleControllerImpl implements ModuleController {
	
	private final Logger logger = LoggerFactory.getLogger(ModuleControllerImpl.class);
	private ModuleHandler moduleHandler;
	
	public ModuleControllerImpl() {
		this.moduleHandler = new ModuleHandler();
	}
	
	public ResultDTO submitModule(ModuleDTO moduleDTO) {
		logger.debug("start submitModule");
		ResultDTO resultDTO = this.moduleHandler.submitModule(moduleDTO);
		logger.debug("finish submitModule");
		return resultDTO;
	}

	public ModuleDTO getModule(ModuleDTO moduleDTO) {
		logger.debug("start getModule");
		ModuleDTO moduleDTORead = this.moduleHandler.getModule(moduleDTO);
		logger.debug("finish getModule");
		return moduleDTORead;
	}

	public ResultDTO validateModule(ModuleDTO moduleDTO) {
		logger.debug("start validateModule");
		ResultDTO resultDTO = this.moduleHandler.validateModule(moduleDTO);
		logger.debug("finish validateModule");
		return resultDTO;
	}

}

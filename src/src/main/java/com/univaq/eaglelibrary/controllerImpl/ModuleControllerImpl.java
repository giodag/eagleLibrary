package com.univaq.eaglelibrary.controllerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controller.ModuleController;
import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.hanlder.ModuleHandler;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;

public class ModuleControllerImpl implements ModuleController {
	
	private final Logger logger = LoggerFactory.getLogger(ModuleControllerImpl.class);
	private ModuleHandler moduleHandler;
	
	public ModuleControllerImpl() {
		this.moduleHandler = new ModuleHandler();
	}
	
	public ResultDTO submitModule(ModuleDTO moduleDTO) {
		logger.debug("start submitModule");
		ResultDTO resultDTO = null;
		try {
			resultDTO = this.moduleHandler.createUpdateModule(moduleDTO);
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("finish submitModule");
		return resultDTO;
	}

	public ModuleDTO getModule(ModuleDTO moduleDTO) {
		logger.debug("start getModule");
		ModuleDTO moduleDTORead = this.moduleHandler.readModule(moduleDTO);
		logger.debug("finish getModule");
		return moduleDTORead;
	}

	public ResultDTO validateModule(ModuleDTO moduleDTO) {
		logger.debug("start validateModule");
		ResultDTO resultDTO = null;
		try {
			resultDTO = this.moduleHandler.createUpdateModule(moduleDTO);
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("finish validateModule");
		return resultDTO;
	}

}

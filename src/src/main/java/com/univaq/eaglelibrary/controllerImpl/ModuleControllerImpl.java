package com.univaq.eaglelibrary.controllerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.univaq.eaglelibrary.controller.ModuleController;
import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.exceptions.CannotUpdateModuleException;
import com.univaq.eaglelibrary.exceptions.CreateModuleException;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.hanlder.ModuleHandler;

/**
 * L'implementazione dell'interfaccia controller, orchestra le chiamate verso il
 * core computazionale del sistema minimizzando così gli impatti tra la parte
 * view e la parte logica nel caso di change requests.
 */
@Service
public class ModuleControllerImpl implements ModuleController {

	@Autowired
	private ModuleHandler moduleHandler;

	public ResultDTO submitModule(ModuleDTO moduleDTO)
			throws MandatoryFieldException, CreateModuleException, CannotUpdateModuleException {
		ModuleDTO moduleRead = this.moduleHandler.readModule(moduleDTO);
		ResultDTO resultDTO = new ResultDTO(Boolean.FALSE);
		if (moduleRead != null) {
			return moduleHandler.createUpdateModule(moduleDTO);
		}
		return resultDTO;
	}

	public ModuleDTO getModule(ModuleDTO moduleDTO) {
		return this.moduleHandler.readModule(moduleDTO);
	}

	public ResultDTO validateModule(ModuleDTO moduleDTO) throws MandatoryFieldException, CannotUpdateModuleException {
		return this.moduleHandler.createUpdateModule(moduleDTO);
	}

}

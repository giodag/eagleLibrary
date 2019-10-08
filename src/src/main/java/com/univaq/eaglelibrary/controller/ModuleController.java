package com.univaq.eaglelibrary.controller;

import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.exceptions.CreateModuleException;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;

public interface ModuleController {

	public ResultDTO submitModule(ModuleDTO moduleDTO) throws MandatoryFieldException, CreateModuleException;
	public ModuleDTO getModule(ModuleDTO moduleDTO);
	public ResultDTO validateModule(ModuleDTO moduleDTO) throws MandatoryFieldException;
	
}

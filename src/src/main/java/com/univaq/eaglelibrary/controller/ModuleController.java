package com.univaq.eaglelibrary.controller;

import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;

public interface ModuleController {

	public ResultDTO submitModule(ModuleDTO moduleDTO);
	public ModuleDTO getModule(ModuleDTO moduleDTO);
	public ResultDTO validateModule(ModuleDTO moduleDTO);
	
}

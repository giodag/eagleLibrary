package com.univaq.eaglelibrary.controllerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.univaq.eaglelibrary.controller.LiteraryWorkController;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.hanlder.LiteraryWorkHanlder;
/**
 * L'implementazione dell'interfaccia controller, orchestra le chiamate verso il core computazionale 
 * del sistema minimizzando così gli impatti tra la parte view e la parte logica nel caso di change requests.
 */
@Service
public class LiteraryWorkControllerImpl implements LiteraryWorkController {

	@Autowired
	private LiteraryWorkHanlder literaryWorkHanlder; 
	
	public LiteraryWorkDTO getLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		return literaryWorkHanlder.readLiteraryWork(literaryWorkDTO);
	}

	public LiteraryWorkDTO getLiteraryWorkTranscribed(LiteraryWorkDTO literaryWorkDTO) {
		return literaryWorkHanlder.readLiteraryWorkTranscribed(literaryWorkDTO);
	}

	public LiteraryWorkListDTO getLiteraryWork(LiteraryWorkListFilterDTO literaryWorkListFilterDTO) {
		return literaryWorkHanlder.readLiteraryWorkList(literaryWorkListFilterDTO);
	}

	public ResultDTO createUpdateLiteraryWork(LiteraryWorkDTO literaryWorkDTO) throws MandatoryFieldException {
		return literaryWorkHanlder.createUpdateLiteraryWork(literaryWorkDTO);
	}

}

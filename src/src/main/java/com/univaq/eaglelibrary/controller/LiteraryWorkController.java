package com.univaq.eaglelibrary.controller;

import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
/**
 * Controller per le opere, funge da canale tra la parte view e la parte che fa logica computazionale.
 */
public interface LiteraryWorkController {
	
	public LiteraryWorkDTO getLiteraryWork(LiteraryWorkDTO literaryWorkDTO);
	public LiteraryWorkDTO getLiteraryWorkTranscribed(LiteraryWorkDTO literaryWorkDTO);
	public LiteraryWorkListDTO getLiteraryWork(LiteraryWorkListFilterDTO literaryWorkListFilterDTO);
	public ResultDTO createUpdateLiteraryWork(LiteraryWorkDTO literaryWorkDTO) throws MandatoryFieldException;
}

package com.univaq.eaglelibrary.controllerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.univaq.eaglelibrary.controller.LiteraryWork;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.hanlder.LiteraryWorkHanlder;

@Service
public class LiteraryWorkControllerImpl implements LiteraryWork {

	private final Logger logger = LoggerFactory.getLogger(LiteraryWorkControllerImpl.class);
	
	@Autowired
	private LiteraryWorkHanlder literaryWorkHanlder; 
	
	public LiteraryWorkDTO getLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		logger.debug("Start getLiteraryWork ");
		LiteraryWorkDTO literaryWorkDTORead = literaryWorkHanlder.getLiteraryWork(literaryWorkDTO);
		logger.debug("Finish getLiteraryWork ");
		return literaryWorkDTORead;
	}

	public LiteraryWorkDTO getLiteraryWorkTranscribed(LiteraryWorkDTO literaryWorkDTO) {
		logger.debug("Start getLiteraryWorkTranscribed ");
		LiteraryWorkDTO literaryWorkDTORead = literaryWorkHanlder.getLiteraryWorkTranscribed(literaryWorkDTO);
		logger.debug("Finish getLiteraryWorkTranscribed ");
		return literaryWorkDTORead;
	}

	public LiteraryWorkListDTO getLiteraryWork(LiteraryWorkListFilterDTO literaryWorkListFilterDTO) {
		logger.debug("Start getLiteraryWorkFilter ");
		LiteraryWorkListDTO literaryWorkListDTO = literaryWorkHanlder.getLiteraryWork(literaryWorkListFilterDTO);
		logger.debug("Finish getLiteraryWorkFilter ");
		return literaryWorkListDTO;
	}

	public ResultDTO createUpdateLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		logger.debug("Start createUpdateLiteraryWork ");
		ResultDTO resultDTO = literaryWorkHanlder.createUpdateLiteraryWork(literaryWorkDTO);
		logger.debug("Finish createUpdateLiteraryWork ");
		return resultDTO;
	}

}

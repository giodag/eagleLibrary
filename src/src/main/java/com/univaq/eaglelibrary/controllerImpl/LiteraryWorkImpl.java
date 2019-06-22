package com.univaq.eaglelibrary.controllerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controller.LiteraryWork;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.hanlder.LiteraryWorkHanlder;

public class LiteraryWorkImpl implements LiteraryWork {

	private final Logger logger = LoggerFactory.getLogger(LiteraryWorkImpl.class);
	private LiteraryWorkHanlder literaryWorkHanlder; 
	
	public LiteraryWorkImpl() {
		literaryWorkHanlder = new LiteraryWorkHanlder();
	}
	
	public LiteraryWorkDTO getLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		logger.debug("Start getLiteraryWork ");
		LiteraryWorkDTO literaryWorkDTORead = this.literaryWorkHanlder.getLiteraryWork(literaryWorkDTO);
		logger.debug("Finish getLiteraryWork ");
		return literaryWorkDTORead;
	}

	public LiteraryWorkDTO getLiteraryWorkTranscribed(LiteraryWorkDTO literaryWorkDTO) {
		logger.debug("Start getLiteraryWorkTranscribed ");
		LiteraryWorkDTO literaryWorkDTORead = this.literaryWorkHanlder.getLiteraryWorkTranscribed(literaryWorkDTO);
		logger.debug("Finish getLiteraryWorkTranscribed ");
		return literaryWorkDTORead;
	}

	public LiteraryWorkListDTO getLiteraryWork(LiteraryWorkListFilterDTO literaryWorkListFilterDTO) {
		logger.debug("Start getLiteraryWorkFilter ");
		LiteraryWorkListDTO literaryWorkListDTO = this.literaryWorkHanlder.getLiteraryWork(literaryWorkListFilterDTO);
		logger.debug("Finish getLiteraryWorkFilter ");
		return literaryWorkListDTO;
	}

	public ResultDTO createUpdateLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		logger.debug("Start createUpdateLiteraryWork ");
		ResultDTO resultDTO = this.literaryWorkHanlder.createUpdateLiteraryWork(literaryWorkDTO);
		logger.debug("Finish createUpdateLiteraryWork ");
		return resultDTO;
	}

}

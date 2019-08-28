package com.univaq.eaglelibrary.hanlder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.model.LiteraryWork;
import com.univaq.eaglelibrary.repository.LiteraryWorkRepository;

@Component
public class LiteraryWorkHanlder {

	@Autowired
	private LiteraryWorkRepository literaryWorkRepository;

	private final Logger logger = LoggerFactory.getLogger(LiteraryWorkHanlder.class);

	
	public LiteraryWorkDTO getLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		LiteraryWork literaryWork = literaryWorkRepository.findLiteraryWorkByFilter(literaryWorkDTO.getId(), literaryWorkDTO.getCategory(),
				literaryWorkDTO.getTitle(), literaryWorkDTO.getYear(), literaryWorkDTO.getAuthor());
		return literaryWorkDTO;
	}

	public LiteraryWorkDTO getLiteraryWorkTranscribed(LiteraryWorkDTO literaryWorkDTO) {
		return null;
	}

	public LiteraryWorkListDTO getLiteraryWork(LiteraryWorkListFilterDTO literaryWorkListFilterDTO) {
		return null;
	}

	public ResultDTO createUpdateLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		return null;
	}

}

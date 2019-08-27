package com.univaq.eaglelibrary.hanlder;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.repository.LiteraryWorkRepository;

@Component
public class LiteraryWorkHanlder {
	
	@Autowired
	private LiteraryWorkRepository literaryWorkRepository;
	
	private final Logger logger = LoggerFactory.getLogger(LiteraryWorkHanlder.class);
	
	// Qua andrebbe ovviamente passato un filtro, solo che prima delle generazione
	// dei TO
	// queste classi non si possono completare.
	public LiteraryWorkListDTO getLiteraryWorks(LiteraryWorkListFilterDTO literaryWorkListFilterDTO) {
		LiteraryWorkListDTO result = null;
		
		return result;
	}

	public LiteraryWorkDTO getLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		Set<Map<String, String>> result = null;
		return null;
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

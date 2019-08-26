package com.univaq.eaglelibrary.hanlder;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.persistence.PersistenceService;
import com.univaq.eaglelibrary.persistence.exceptions.DatabaseException;

@Component
public class LiteraryWorkHanlder {
	
	private PersistenceService persistenceService;
	private final Logger logger = LoggerFactory.getLogger(LiteraryWorkHanlder.class);
	
	// Qua andrebbe ovviamente passato un filtro, solo che prima delle generazione
	// dei TO
	// queste classi non si possono completare.
	public Set<Map<String, String>> getLiteraryWork() {
		
		Set<Map<String, String>> result = null;
		try {
			// test per andare a fare la lettura a secco, qua ovviamente devono essere passati i filtri
			result = persistenceService.search("user", "first_name = 'sab'");
		} catch (DatabaseException e) {
			logger.error("LiteraryWorkHanlder : Error in search literaryWork. StackTrace : ",e);
		}
		
		return result;
	}

	public LiteraryWorkDTO getLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		Set<Map<String, String>> result = null;
		try {
			// test per andare a fare la lettura a secco, qua ovviamente devono essere passati i filtri
			result = persistenceService.search("user", "first_name = 'sab'");
		} catch (DatabaseException e) {
			logger.error("LiteraryWorkHanlder : Error in search literaryWork. StackTrace : ",e);
		}
		
		return null;
	}

	public LiteraryWorkDTO getLiteraryWorkTranscribed(LiteraryWorkDTO literaryWorkDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public LiteraryWorkListDTO getLiteraryWork(LiteraryWorkListFilterDTO literaryWorkListFilterDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultDTO createUpdateLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}

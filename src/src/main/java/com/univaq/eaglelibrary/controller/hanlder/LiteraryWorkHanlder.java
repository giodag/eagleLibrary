package com.univaq.eaglelibrary.controller.hanlder;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controller.LiteraryWork;
import com.univaq.eaglelibrary.controller.PersistenceService;
import com.univaq.eaglelibrary.controller.exceptions.DatabaseException;

//@Slf4j
public class LiteraryWorkHanlder implements LiteraryWork {
	
	private PersistenceService persistenceService;
	private final Logger logger = LoggerFactory.getLogger(LiteraryWorkHanlder.class);
	
	public LiteraryWorkHanlder(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

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

}

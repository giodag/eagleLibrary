package com.univaq.eaglelibrary.hanlder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.dto.LoginRequestDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.persistence.PersistenceService;
import com.univaq.eaglelibrary.persistence.exceptions.DatabaseException;

public class UserHanlder {
	
	private final Logger logger = LoggerFactory.getLogger(UserHanlder.class);
	private PersistenceService persistenceService;
	
	public UserHanlder(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	public UserDTO login(LoginRequestDTO loginRequestDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultDTO registration(UserDTO userDTO) {
		ResultDTO resultDTO = null;
		//--Costruire la mappa chiave valore con i nome dei campi e valori dei campi
		//--della tabella user
		try {
			persistenceService.save("", null);
		} catch (DatabaseException e) {
			logger.error("Database Exception",e);
		}
		resultDTO = new ResultDTO();
		resultDTO.setSuccessfullyOperation(Boolean.TRUE);
		return resultDTO;
	}

	public ResultDTO logout(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}

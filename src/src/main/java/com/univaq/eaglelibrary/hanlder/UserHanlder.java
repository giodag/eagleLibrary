package com.univaq.eaglelibrary.hanlder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.dto.LoginRequestDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.persistence.MySQLConnection;
import com.univaq.eaglelibrary.persistence.PersistenceService;
import com.univaq.eaglelibrary.persistence.exceptions.DatabaseException;

public class UserHanlder {

	private final Logger logger = LoggerFactory.getLogger(UserHanlder.class);
	private static PersistenceService persistenceService;

	public UserDTO login(LoginRequestDTO loginRequestDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultDTO registration(UserDTO userDTO) {
		
		getPersistenceInterface();
		
		try {
			persistenceService = new MySQLConnection();
		} catch (DatabaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ResultDTO resultDTO = null;
		String kind = "user";
		String condition = "first_name = 'sab'";
		try {
			persistenceService.search(kind, condition);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
		
 		resultDTO = new ResultDTO();
		resultDTO.setSuccessfullyOperation(Boolean.TRUE);
		return resultDTO;
	}

	@SuppressWarnings("static-access")
	private PersistenceService getPersistenceInterface() {
		
		if(this.persistenceService == null) {
			try {
				this.persistenceService = new MySQLConnection();
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.persistenceService;
	}

	public ResultDTO logout(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}

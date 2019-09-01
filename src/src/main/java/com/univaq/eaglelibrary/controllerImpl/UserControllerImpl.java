package com.univaq.eaglelibrary.controllerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.univaq.eaglelibrary.controller.UserController;
import com.univaq.eaglelibrary.dto.LoginRequestDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.hanlder.UserHanlder;
import com.univaq.eaglelibrary.persistence.exceptions.CreateUserException;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;

@Service
public class UserControllerImpl implements UserController {
	
	@Autowired
	private UserHanlder userHanlder;
	private final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

	public UserDTO login(LoginRequestDTO loginRequestDTO) {
		logger.debug("Start login");
		UserDTO userDTO = this.userHanlder.login(loginRequestDTO); 
		return userDTO;
	}

	public ResultDTO registration(UserDTO userDTO) {
		logger.debug("Start registration");
		ResultDTO resultDTO = null;
		try {
			resultDTO = this.userHanlder.createUpdateUser(userDTO);
		} catch (MandatoryFieldException e) {
			e.printStackTrace();
		} catch (CreateUserException e) {
			e.printStackTrace();
		}
		return resultDTO;
	}

	public ResultDTO logout(UserDTO userDTO) {
		logger.debug("Start logout");
		ResultDTO resultDTO = this.userHanlder.logout(userDTO);
		return resultDTO;
	}
}

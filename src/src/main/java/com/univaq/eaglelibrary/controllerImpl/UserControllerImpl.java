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
import com.univaq.eaglelibrary.persistence.exceptions.UserNotFoundException;
import com.univaq.eaglelibrary.persistence.exceptions.WrongPasswordException;

@Service
public class UserControllerImpl implements UserController {
	
	@Autowired
	private UserHanlder userHanlder;
	private final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

	public UserDTO login(LoginRequestDTO loginRequestDTO) throws UserNotFoundException, MandatoryFieldException, WrongPasswordException {
		logger.debug("Start login");
		UserDTO userDTO = userHanlder.login(loginRequestDTO);
		return userDTO;
	}

	public ResultDTO registration(UserDTO userDTO) throws MandatoryFieldException, CreateUserException {
		logger.debug("Start registration");
		ResultDTO resultDTO = userHanlder.createUpdateUser(userDTO);
		return resultDTO;
	}

	public ResultDTO logout(UserDTO userDTO) {
		logger.debug("Start logout");
		ResultDTO resultDTO = this.userHanlder.logout(userDTO);
		return resultDTO;
	}
}

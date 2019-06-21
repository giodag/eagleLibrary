package com.univaq.eaglelibrary.controllerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controller.UserController;
import com.univaq.eaglelibrary.dto.LoginRequestDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.hanlder.UserHanlder;

public class UserControllerImpl implements UserController {
	
	private UserHanlder userHanlder;
	private final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);


	public UserControllerImpl() {
		userHanlder = new UserHanlder();
	}
	
	
	public UserDTO login(LoginRequestDTO loginRequestDTO) {
		logger.debug("Start login");
		UserDTO userDTO = this.userHanlder.login(loginRequestDTO); 
		return userDTO;
	}

	public ResultDTO registration(UserDTO userDTO) {
		logger.debug("Start registration");
		ResultDTO resultDTO = this.userHanlder.registration(userDTO);
		return resultDTO;
	}

	public ResultDTO logout(UserDTO userDTO) {
		logger.debug("Start logout");
		ResultDTO resultDTO = this.userHanlder.logout(userDTO);
		return resultDTO;
	}
}

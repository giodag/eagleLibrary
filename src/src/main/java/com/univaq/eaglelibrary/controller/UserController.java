package com.univaq.eaglelibrary.controller;

import com.univaq.eaglelibrary.dto.LoginRequestDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.persistence.exceptions.CreateUserException;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.persistence.exceptions.UserNotFoundException;
import com.univaq.eaglelibrary.persistence.exceptions.WrongPasswordException;

public interface UserController {
		
		public UserDTO login(LoginRequestDTO loginRequestDTO) throws UserNotFoundException, MandatoryFieldException, WrongPasswordException;
		public ResultDTO registration(UserDTO userDTO) throws MandatoryFieldException, CreateUserException;
		public ResultDTO logout(UserDTO userDTO);

}

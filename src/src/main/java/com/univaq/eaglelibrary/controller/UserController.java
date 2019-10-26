package com.univaq.eaglelibrary.controller;

import com.univaq.eaglelibrary.dto.LoginRequestDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.dto.UserFilterDTO;
import com.univaq.eaglelibrary.dto.UserListDTO;
import com.univaq.eaglelibrary.exceptions.CreateUserException;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.exceptions.UserNotFoundException;
import com.univaq.eaglelibrary.exceptions.WrongPasswordException;
/**
 * Controller per la gestione degli utenti, 
 * funge da canale tra la parte view e la parte che fa logica computazionale.
 */
public interface UserController {
		
		public UserDTO login(LoginRequestDTO loginRequestDTO) throws UserNotFoundException, MandatoryFieldException, WrongPasswordException;
		public ResultDTO registration(UserDTO userDTO) throws MandatoryFieldException, CreateUserException;
		public ResultDTO logout();
		public UserListDTO getUserList(UserFilterDTO userFilterDTO);
		public ResultDTO updateUser(UserDTO userDTO)throws MandatoryFieldException;
}

package com.univaq.eaglelibrary.controllerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.univaq.eaglelibrary.controller.UserController;
import com.univaq.eaglelibrary.dto.LoginRequestDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.dto.UserFilterDTO;
import com.univaq.eaglelibrary.dto.UserListDTO;
import com.univaq.eaglelibrary.exceptions.CreateUserException;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.exceptions.UserNotFoundException;
import com.univaq.eaglelibrary.exceptions.WrongPasswordException;
import com.univaq.eaglelibrary.hanlder.UserHanlder;
/**
 * L'implementazione dell'interfaccia controller, orchestra le chiamate verso il core computazionale 
 * del sistema minimizzando così gli impatti tra la parte view e la parte logica nel caso di change requests.
 */
@Service
public class UserControllerImpl implements UserController {
	
	@Autowired
	private UserHanlder userHanlder;

	public UserDTO login(LoginRequestDTO loginRequestDTO) throws UserNotFoundException, MandatoryFieldException, WrongPasswordException {
		return userHanlder.login(loginRequestDTO);
	}

	public ResultDTO registration(UserDTO userDTO) throws MandatoryFieldException, CreateUserException {
		return userHanlder.createUser(userDTO);
	}

	public ResultDTO logout() {
		return this.userHanlder.logout();
	}
	
	public UserDTO getUser(UserDTO userDTO) {
		return userHanlder.readUser(userDTO);
	}
	
	public UserListDTO getUserList(UserFilterDTO userFilterDTO) {
		return userHanlder.readUserListByFilter(userFilterDTO);
	}

	public ResultDTO updateUser(UserDTO userDTO) throws MandatoryFieldException {
		return userHanlder.updateUser(userDTO);
	}
}

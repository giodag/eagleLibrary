package com.univaq.eaglelibrary.controller;

import com.univaq.eaglelibrary.dto.LoginRequestDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.UserDTO;

public interface UserController {
	
	public UserDTO login(LoginRequestDTO loginRequestDTO);
	
	public ResultDTO registration(UserDTO userDTO);
	
	public ResultDTO logout(UserDTO userDTO);

}

package com.univaq.eaglelibrary.hanlder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.cj.util.StringUtils;
import com.univaq.eaglelibrary.converter.ConvertUser;
import com.univaq.eaglelibrary.dto.LoginRequestDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.model.User;
import com.univaq.eaglelibrary.persistence.exceptions.CreateUserException;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.persistence.exceptions.UserNotFoundException;
import com.univaq.eaglelibrary.persistence.exceptions.WrongPasswordException;
import com.univaq.eaglelibrary.repository.UserRepository;

@Component
public class UserHanlder {

	private final Logger logger = LoggerFactory.getLogger(UserHanlder.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ConvertUser convertUser;

	public UserDTO login(LoginRequestDTO loginRequestDTO) throws UserNotFoundException, MandatoryFieldException, WrongPasswordException {
		UserDTO userDTO = null;
		if(loginRequestDTO != null &&  !StringUtils.isNullOrEmpty(loginRequestDTO.getUser()) 
				&& !StringUtils.isNullOrEmpty(loginRequestDTO.getPassword())) {
			userDTO = new UserDTO();
			userDTO.setUsername(loginRequestDTO.getUser());
			UserDTO userRead = readUser(userDTO);
			
			if(userRead != null) {
				if(!userRead.getPassword().equals(loginRequestDTO.getPassword())) {
					logger.error("Wrong password");
					throw new WrongPasswordException("Wrong password");
				}
			} else {
				logger.error("User"+ loginRequestDTO.getUser() + "does not exist");
				throw new UserNotFoundException("User "+ loginRequestDTO.getUser() + " does not exist");
			}
		} else  {
			logger.error("Username or Password missing");
			throw new MandatoryFieldException("Username and Password required");
		}
		return userDTO;
	}
	
	public UserDTO readUser(UserDTO userDTO) {
		UserDTO userRead = null;
		if(userDTO != null) {
			User user = null;
			if(userDTO.getId() != null) {
				user = userRepository.findOne(userDTO.getId());
			}else {
				user = userRepository.findByUsername(userDTO.getUsername());
			}
			if(user != null) {
				userRead = convertUser.convert(user);
			}
		}
		return userRead;
	}

	public ResultDTO createUpdateUser(UserDTO userDTO) throws MandatoryFieldException, CreateUserException {
		ResultDTO resultDTO = null;
		checkMandatory(userDTO);
		User user = userRepository.save(convertUser.convert(userDTO));
		if(user != null) {
			resultDTO = new ResultDTO();
			resultDTO.setSuccessfullyOperation(Boolean.TRUE);
		}else {
			logger.error("Error in creation User :"+userDTO.getUsername());
			throw new CreateUserException("Error in creation User :"+userDTO.getUsername());
		}
		
		return resultDTO;
	}

	private void checkMandatory(UserDTO userDTO)throws MandatoryFieldException{
		if(userDTO == null
				|| StringUtils.isNullOrEmpty(userDTO.getEmail())
				|| StringUtils.isNullOrEmpty(userDTO.getFirstName())
				|| StringUtils.isNullOrEmpty(userDTO.getLastName())
				|| StringUtils.isNullOrEmpty(userDTO.getUsername())
				|| StringUtils.isNullOrEmpty(userDTO.getPassword())
				|| userDTO.getPermission() == null) {
			throw new MandatoryFieldException();
		}
		
	}

	public ResultDTO logout(UserDTO userDTO) {
		return null;
	}
}

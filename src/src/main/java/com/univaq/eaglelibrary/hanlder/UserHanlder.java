package com.univaq.eaglelibrary.hanlder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.converter.ConvertUser;
import com.univaq.eaglelibrary.dto.LoginRequestDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.model.User;
import com.univaq.eaglelibrary.persistence.exceptions.CreateUserException;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.repository.UserRepository;

@Component
public class UserHanlder {

	private final Logger logger = LoggerFactory.getLogger(UserHanlder.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ConvertUser convertUser;

	public UserDTO login(LoginRequestDTO loginRequestDTO) {
		UserDTO user = new UserDTO();
		
		
		return user;
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
				|| StringUtils.isEmpty(userDTO.getEmail())
				|| StringUtils.isEmpty(userDTO.getFirstName())
				|| StringUtils.isEmpty(userDTO.getLastName())
				|| StringUtils.isEmpty(userDTO.getUsername())
				|| StringUtils.isEmpty(userDTO.getPassword())
				|| userDTO.getPermission() == null) {
			throw new MandatoryFieldException();
		}
		
	}

	public ResultDTO logout(UserDTO userDTO) {
		return null;
	}
}

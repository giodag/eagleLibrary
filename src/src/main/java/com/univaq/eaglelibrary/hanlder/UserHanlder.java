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
import com.univaq.eaglelibrary.exceptions.CreateUserException;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.exceptions.UserNotFoundException;
import com.univaq.eaglelibrary.exceptions.WrongPasswordException;
import com.univaq.eaglelibrary.model.User;
import com.univaq.eaglelibrary.repository.UserRepository;

@Component
public class UserHanlder {

	private static final String PASSWORD = "Password";
	private static final String USERNAME = "Username";
	private static final String LAST_NAME = "LastName";
	private static final String FIRST_NAME = "FirstName";
	private static final String EMAIL = "Email";
	private static final String ALL = "All";
	private static final String MISSED_PARAMETER = "Missed parameter :{}";

	private final Logger logger = LoggerFactory.getLogger(UserHanlder.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ConvertUser convertUser;

	public UserDTO login(LoginRequestDTO loginRequestDTO) throws UserNotFoundException, MandatoryFieldException, WrongPasswordException {
		UserDTO userRead = null;
		if(loginRequestDTO != null &&  !StringUtils.isNullOrEmpty(loginRequestDTO.getUser()) 
				&& !StringUtils.isNullOrEmpty(loginRequestDTO.getPassword())) {
			UserDTO userDTO = new UserDTO();
			userDTO.setUsername(loginRequestDTO.getUser());
			userRead = readUser(userDTO);
			
			if(userRead != null) {
				if(!userRead.getPassword().equals(loginRequestDTO.getPassword())) {
					logger.error("Wrong password");
					throw new WrongPasswordException("Wrong password");
				}
			} else {
				logger.error("User {} does not exist",loginRequestDTO.getUser());
				throw new UserNotFoundException("User "+ loginRequestDTO.getUser() + " does not exist");
			}
		} else  {
			logger.error("Username or Password missing");
			throw new MandatoryFieldException("Username and Password required");
		}
		return userRead;
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
			logger.error("Error in creation User : {}",userDTO.getUsername());
			throw new CreateUserException("Error in creation User :"+userDTO.getUsername());
		}
		
		return resultDTO;
	}

	private void checkMandatory(UserDTO userDTO)throws MandatoryFieldException{
		
			if(userDTO == null) {
				throw new MandatoryFieldException(MISSED_PARAMETER, ALL);
			}else if(StringUtils.isNullOrEmpty(userDTO.getEmail())) {
				throw new MandatoryFieldException(MISSED_PARAMETER, EMAIL);
			}else if(StringUtils.isNullOrEmpty(userDTO.getFirstName())) {
				throw new MandatoryFieldException(MISSED_PARAMETER, FIRST_NAME);
			}else if(StringUtils.isNullOrEmpty(userDTO.getLastName())) {
				throw new MandatoryFieldException(MISSED_PARAMETER, LAST_NAME);
			}else if(StringUtils.isNullOrEmpty(userDTO.getUsername())) {
				throw new MandatoryFieldException(MISSED_PARAMETER, USERNAME);
			}else if(StringUtils.isNullOrEmpty(userDTO.getPassword())) {
				throw new MandatoryFieldException(MISSED_PARAMETER, PASSWORD);
			}else if(userDTO.getPermission() == null) {
				throw new MandatoryFieldException();
			}
	}

	public ResultDTO logout(UserDTO userDTO) {
		return new ResultDTO(Boolean.TRUE);
	}
}

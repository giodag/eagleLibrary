package com.univaq.eaglelibrary.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.model.User;

@Component
public class ConvertUser {
	
	@Autowired
	private ConvertTranscription convertTranscription;
	
	public User convert(UserDTO userDTO) {
		User user = null;
		if(userDTO != null) {
			user = new User();
			
			user.setActivated(userDTO.getActivated());
			user.setEmail(userDTO.getEmail());
			user.setFirstName(userDTO.getFirstName());
			user.setId(userDTO.getId());
			user.setLastName(userDTO.getLastName());
			user.setPassword(userDTO.getPassword());
			user.setUsername(userDTO.getUsername());
			user.setListTranscription(convertTranscription.convert(userDTO.getTranscriptionList()));
		}
		return user;
	}
	
	public UserDTO convert(User user) {
		UserDTO userDTO = null;
		if(user != null) {
			userDTO = new UserDTO();
			
			userDTO.setActivated(user.isActivated());
			userDTO.setEmail(user.getEmail());
			userDTO.setFirstName(user.getFirstName());
			userDTO.setId(user.getId());
			userDTO.setLastName(user.getLastName());
			userDTO.setPassword(user.getPassword());
			userDTO.setUsername(user.getUsername());
		}
		return userDTO;
	}
	
	public List<User> convertToModel(List<UserDTO> userDTOs){
		List<User> users = null;
		if(userDTOs != null && !userDTOs.isEmpty()) {
			users = new ArrayList<User>();
			for (UserDTO userDTO : userDTOs) {
				users.add(convert(userDTO));
			}
		}
		return users;
	}
	
	public List<UserDTO> convert(List<User> users){
		List<UserDTO> usersDTO = null;
		if(users != null && !users.isEmpty()) {
			usersDTO = new ArrayList<UserDTO>();
			for (User user : users) {
				usersDTO.add(convert(user));
			}
		}
		return usersDTO;
	}
}

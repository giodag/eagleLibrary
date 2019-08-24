package com.univaq.eaglelibrary.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.model.User;

@Component
public class ConvertUserToModel {
	
	@Autowired
	private ConvertTranscriptionToModel convertTranscriptionToModel;
	
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
			user.setUserName(userDTO.getUsername());
			user.setListTranscription(convertTranscriptionToModel.convert(userDTO.getTranscriptionList()));
		}
		return user;
	}
}

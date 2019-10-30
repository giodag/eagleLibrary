package com.univaq.eaglelibrary.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.ProfileDTO;
import com.univaq.eaglelibrary.model.Profile;

@Component
public class ConvertProfile {
	
	@Autowired
	private ConvertUser convertUser;
	
	public Profile convert(ProfileDTO profileDTO) {
		Profile profile = null;
		if(profileDTO != null) {
			profile = new Profile();
			
			profile.setAddress(profileDTO.getAddress());
			profile.setDateOfBirth(profileDTO.getDateOfBirth());
			profile.setDegreeCourse(profileDTO.getDegreeCourse());
			profile.setId(profileDTO.getId());
			profile.setMatriculationNumber(profileDTO.getMatriculationNumber());
			profile.setUser(convertUser.convert(profileDTO.getUser()));
		}	
		return profile;
	}

	public ProfileDTO convert(Profile profile) {
		ProfileDTO profileDTO = null;
		if(profile != null) {
			profileDTO = new ProfileDTO();
			
			profileDTO.setAddress(profile.getAddress());
			profileDTO.setDateOfBirth(profile.getDateOfBirth());
			profileDTO.setDegreeCourse(profile.getDegreeCourse());
			profileDTO.setId(profile.getId());
			profileDTO.setMatriculationNumber(profile.getMatriculationNumber());
			profileDTO.setUser(convertUser.convertNoTranscription(profile.getUser()));
		}	
		return profileDTO;
	}
}

package com.univaq.eaglelibrary.hanlder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mysql.cj.util.StringUtils;
import com.univaq.eaglelibrary.dto.ProfileDTO;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;

@Component
public class ProfileHandler{
	
	private final Logger logger = LoggerFactory.getLogger(ProfileHandler.class);

	public ProfileDTO createUpdateProfile(ProfileDTO profileDTO) throws MandatoryFieldException {
		
		
		checkMandatory(profileDTO);
		
		return profileDTO;
	}

	public ProfileDTO getProfile(ProfileDTO profileDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void checkMandatory(ProfileDTO profileDTO) throws MandatoryFieldException {
		if(profileDTO == null || StringUtils.isNullOrEmpty(profileDTO.getAddress())
				|| profileDTO.getDateOfBirth() == null || StringUtils.isNullOrEmpty(profileDTO.getDegreeCourse()) 
				|| StringUtils.isNullOrEmpty(profileDTO.getEmail()) || StringUtils.isNullOrEmpty(profileDTO.getMatriculationNumber())
				|| profileDTO.getUser() == null) {
			throw new MandatoryFieldException();
		}
	}
		
}

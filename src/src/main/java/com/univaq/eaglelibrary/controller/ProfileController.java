package com.univaq.eaglelibrary.controller;

import com.univaq.eaglelibrary.dto.ProfileDTO;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;

public interface ProfileController {
	
	public ProfileDTO createUpdateProfile(ProfileDTO profileDTO) throws MandatoryFieldException;
	public ProfileDTO getProfile(ProfileDTO profileDTO);

}

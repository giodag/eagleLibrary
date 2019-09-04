package com.univaq.eaglelibrary.controllerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controller.ProfileController;
import com.univaq.eaglelibrary.dto.ProfileDTO;
import com.univaq.eaglelibrary.hanlder.ProfileHandler;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;

public class ProfileControllerImpl implements ProfileController {
	
	private final Logger logger = LoggerFactory.getLogger(ProfileControllerImpl.class);
	private ProfileHandler profileHandler;
	
	public ProfileControllerImpl () {
		this.profileHandler = new ProfileHandler();
	}

	public ProfileDTO createUpdateProfile(ProfileDTO profileDTO) throws MandatoryFieldException {
		logger.debug("start createUpdateProfile");
		ProfileDTO profileDTOCreated = this.profileHandler.createUpdateProfile(profileDTO);
		logger.debug("finish createUpdateProfile");
		return profileDTOCreated;
	}

	public ProfileDTO getProfile(ProfileDTO profileDTO) {
		logger.debug("start getProfile");
		ProfileDTO profileDTORead = this.profileHandler.readProfile(profileDTO);
		logger.debug("finish getProfile");
		return profileDTORead;
	}
}

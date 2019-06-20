package com.univaq.eaglelibrary.controller;

import com.univaq.eaglelibrary.dto.ProfileDTO;

public interface ProfileController {
	
	public ProfileDTO createUpdateProfile(ProfileDTO profileDTO);
	public ProfileDTO getProfile(ProfileDTO profileDTO);

}

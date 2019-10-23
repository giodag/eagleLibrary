package com.univaq.eaglelibrary.controller;

import com.univaq.eaglelibrary.dto.ProfileDTO;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
/**
 * Controller per i profili utenti, funge da canale tra la parte view e la parte che fa logica computazionale.
 */
public interface ProfileController {
	
	public ProfileDTO createUpdateProfile(ProfileDTO profileDTO) throws MandatoryFieldException;
	public ProfileDTO getProfile(ProfileDTO profileDTO);

}

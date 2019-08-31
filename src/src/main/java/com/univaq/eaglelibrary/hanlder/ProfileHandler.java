package com.univaq.eaglelibrary.hanlder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.cj.util.StringUtils;
import com.univaq.eaglelibrary.converter.ConvertProfile;
import com.univaq.eaglelibrary.dto.ProfileDTO;
import com.univaq.eaglelibrary.model.Profile;
import com.univaq.eaglelibrary.model.User;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.repository.ProfileRepository;
import com.univaq.eaglelibrary.repository.UserRepository;

@Component
public class ProfileHandler{
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ConvertProfile convertProfile;
	
	private final Logger logger = LoggerFactory.getLogger(ProfileHandler.class);

	public ProfileDTO createUpdateProfile(ProfileDTO profileDTO) throws MandatoryFieldException {
		
		Profile profile = convertProfile.convert(profileDTO);
		profileRepository.save(profile);
		//checkMandatory(profileDTO);
		
		return profileDTO;
	}
	
	/**
	 * This method return a profile, you need to set id or user in profileDTO.
	 * @param profileDTO
	 * @return profileDTO
	 */
	public ProfileDTO getProfile(ProfileDTO profileDTO) {
		Profile profile = null;
		if (profileDTO.getId() != null) {
			profile = profileRepository.findOne(profileDTO.getId());
		} else {
			if(profileDTO.getUser() != null) {
				// TODO non so come ricavarmi il profilo dallo user
				User user = userRepository.findByUsername(profileDTO.getUser().getUsername());
				profile = profileRepository.findOne(user.getId());
			}
		}
		return convertProfile.convert(profile);
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

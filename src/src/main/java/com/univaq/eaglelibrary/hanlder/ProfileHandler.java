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
		
		checkMandatory(profileDTO);
		ProfileDTO profileRead = readProfile(profileDTO);
		Profile profile = null;
		
		if(profileRead != null) {
			profileRead.setAddress(StringUtils.isNullOrEmpty(profileDTO.getAddress()) ? profileDTO.getAddress() : profileRead.getAddress());
			profileRead.setDateOfBirth(profileDTO.getDateOfBirth() != null ? profileDTO.getDateOfBirth() : profileRead.getDateOfBirth());
			profileRead.setDegreeCourse(StringUtils.isNullOrEmpty(profileDTO.getDegreeCourse()) ? profileDTO.getDegreeCourse() : profileRead.getDegreeCourse());
			profileRead.setEmail(StringUtils.isNullOrEmpty(profileDTO.getEmail()) ? profileDTO.getEmail() : profileRead.getEmail());
			profileRead.setId(profileDTO.getId() != null ? profileDTO.getId() : profileRead.getId());
			profileRead.setMatriculationNumber(StringUtils.isNullOrEmpty(profileDTO.getMatriculationNumber()) 
					? profileDTO.getMatriculationNumber() : profileRead.getMatriculationNumber());
			profile = convertProfile.convert(profileRead);
			
		} else {
			profile = convertProfile.convert(profileDTO);
		}
		profileRepository.save(profile);
		
		return profileDTO;
	}
	
	/**
	 * This method return a profile, you need to set id or user in profileDTO.
	 * @param profileDTO
	 * @return profileDTO
	 */
	public ProfileDTO readProfile(ProfileDTO profileDTO) {
		Profile profile = null;
		if (profileDTO.getId() != null) {
			profile = profileRepository.findOne(profileDTO.getId());
		} else {
			if(profileDTO.getUser() != null) {
				User user = userRepository.findByUsername(profileDTO.getUser().getUsername());
				profile = profileRepository.findProfileByUser(user.getId());
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

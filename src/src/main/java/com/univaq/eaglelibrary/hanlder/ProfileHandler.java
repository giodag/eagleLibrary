package com.univaq.eaglelibrary.hanlder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.cj.util.StringUtils;
import com.univaq.eaglelibrary.converter.ConvertProfile;
import com.univaq.eaglelibrary.dto.ProfileDTO;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.model.Profile;
import com.univaq.eaglelibrary.model.User;
import com.univaq.eaglelibrary.repository.ProfileRepository;
import com.univaq.eaglelibrary.repository.UserRepository;

@Component
public class ProfileHandler{
	
	private static final String USER = "User";

	private static final String MATRICULATION_NUMBER = "MatriculationNumber";

	private static final String DEGREE_COURSE = "DegreeCourse";

	private static final String DATE_OF_BIRTH = "DateOfBirth";

	private static final String ADDRESS = "Address";

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ConvertProfile convertProfile;
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProfileHandler.class);
	private static final String MISSED_PARAMETER = "Missed parameter : ";
	private static final String ALL = "All";

	/**
	 * 
	 * @param profileDTO
	 * @return
	 * @throws MandatoryFieldException
	 */
	public ProfileDTO createUpdateProfile(ProfileDTO profileDTO) throws MandatoryFieldException {
		
		checkMandatory(profileDTO);
		ProfileDTO profileRead = readProfile(profileDTO);
		Profile profile = null;
		
		if(profileRead != null) {
			profileRead.setAddress(!StringUtils.isNullOrEmpty(profileDTO.getAddress()) ? profileDTO.getAddress() : profileRead.getAddress());
			profileRead.setDateOfBirth(profileDTO.getDateOfBirth() != null ? profileDTO.getDateOfBirth() : profileRead.getDateOfBirth());
			profileRead.setDegreeCourse(!StringUtils.isNullOrEmpty(profileDTO.getDegreeCourse()) ? profileDTO.getDegreeCourse() : profileRead.getDegreeCourse());
			profileRead.setId(profileDTO.getId() != null ? profileDTO.getId() : profileRead.getId());
			profileRead.setMatriculationNumber(!StringUtils.isNullOrEmpty(profileDTO.getMatriculationNumber()) 
					? profileDTO.getMatriculationNumber() : profileRead.getMatriculationNumber());
			profileRead.setUser(profileDTO.getUser());
			profile = convertProfile.convert(profileRead);
			
		} else {
			profile = convertProfile.convert(profileDTO);
		}
		userRepository.save(profile.getUser());
		profileRepository.save(profile);
		
		return profileDTO;
	}
	
	/**
	 * This method return a profile, you need to set id or user in profileDTO.
	 * @param profileDTO
	 * @return profileDTO
	 */
	@Transactional
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
	
	/**
	 * 
	 * @param profileDTO
	 * @throws MandatoryFieldException
	 */
	private void checkMandatory(ProfileDTO profileDTO) throws MandatoryFieldException {
		if(profileDTO == null) {
			throw new MandatoryFieldException(MISSED_PARAMETER, ALL);
		}else if(StringUtils.isNullOrEmpty(profileDTO.getAddress())) {
			throw new MandatoryFieldException(MISSED_PARAMETER, ADDRESS);
		}else if(profileDTO.getDateOfBirth() == null) {
			throw new MandatoryFieldException(MISSED_PARAMETER, DATE_OF_BIRTH);
		}else if(StringUtils.isNullOrEmpty(profileDTO.getDegreeCourse()) ) {
			throw new MandatoryFieldException(MISSED_PARAMETER, DEGREE_COURSE);
		}else if(StringUtils.isNullOrEmpty(profileDTO.getMatriculationNumber())) {
			throw new MandatoryFieldException(MISSED_PARAMETER, MATRICULATION_NUMBER);
		}else if(profileDTO.getUser() == null) {
			throw new MandatoryFieldException(MISSED_PARAMETER, USER);
		}
	}
}

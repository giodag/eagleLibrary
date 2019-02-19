package com.univaq.eaglelibraryapp.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.univaq.eaglelibraryapp.domain.Profile;
import com.univaq.eaglelibraryapp.service.dto.ProfileDTO;

/**
 * Mapper for the entity Profile and its DTO ProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {

	@Mapping(source = "user.id", target = "userId")
	ProfileDTO toDto(Profile profile);
	
	@Mapping(source = "userId", target = "user")
	Profile toEntity(ProfileDTO profileDTO);

    default Profile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }
}

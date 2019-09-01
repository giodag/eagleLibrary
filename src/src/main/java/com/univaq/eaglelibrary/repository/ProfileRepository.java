package com.univaq.eaglelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.univaq.eaglelibrary.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

	@Query("select p from Profile p "
			+ "where (coalesce(:idUser, null) is null or p.user.id =:idUser) ")
	public Profile findProfileByUser(@Param("idUser")Long idUser);
}

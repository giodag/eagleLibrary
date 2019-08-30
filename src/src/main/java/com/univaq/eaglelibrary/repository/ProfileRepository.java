package com.univaq.eaglelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.univaq.eaglelibrary.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

}

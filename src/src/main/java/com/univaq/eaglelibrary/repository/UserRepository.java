package com.univaq.eaglelibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.univaq.eaglelibrary.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT * FROM USER u WHERE u.activated = 1", nativeQuery = true)
	public List<User> findAllActiveUsers();
	
	public User findByUsername(String username);

	@Query("select u from FROM USER u inner join u.user_transcription ut"
		    + "where u.activated = true "
		    + "and (coalesce(:idsUser, null) is null or u.id in (:idsUser)) ")
	public List<User> findUsersByFilter(List<Long> ids);
}

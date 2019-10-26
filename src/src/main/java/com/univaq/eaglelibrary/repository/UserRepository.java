package com.univaq.eaglelibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.univaq.eaglelibrary.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u WHERE u.activated = 1")
	public List<User> findAllActiveUsers();
	
	public User findByUsername(String username);

	@Query("select u from User u "
		    + "WHERE u.activated = 1 "
		    + "and (coalesce(:idsUser, null) is null or u.id in (:idsUser)) ")
	public List<User> findUsersByFilter( @Param("idsUser") List<Long> idsUser);
}

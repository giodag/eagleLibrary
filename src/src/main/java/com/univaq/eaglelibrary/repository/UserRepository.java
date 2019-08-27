package com.univaq.eaglelibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.univaq.eaglelibrary.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT * FROM USER u WHERE u.activated = 1", nativeQuery = true)
	public List<User> findAllActiveUsers();

}

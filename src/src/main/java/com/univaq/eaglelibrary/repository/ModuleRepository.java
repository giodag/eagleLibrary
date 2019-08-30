package com.univaq.eaglelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.univaq.eaglelibrary.model.User;

public interface ModuleRepository extends JpaRepository<User, Long>{

}

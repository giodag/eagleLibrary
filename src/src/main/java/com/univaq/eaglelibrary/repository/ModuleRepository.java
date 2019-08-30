package com.univaq.eaglelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.univaq.eaglelibrary.model.Module;

public interface ModuleRepository extends JpaRepository<Module, Long>{

}

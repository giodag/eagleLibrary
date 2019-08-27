package com.univaq.eaglelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.univaq.eaglelibrary.model.LiteraryWork;

public interface LiteraryWorkRepository extends JpaRepository<LiteraryWork, Long> {
	
}

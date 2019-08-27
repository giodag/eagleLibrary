package com.univaq.eaglelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.univaq.eaglelibrary.model.Page;

public interface PageRepository extends JpaRepository<Page, Long> {

}

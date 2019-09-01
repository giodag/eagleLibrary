package com.univaq.eaglelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.univaq.eaglelibrary.model.Page;

public interface PageRepository extends JpaRepository<Page, Long> {
	
	@Query("select lt from Page p "
			+ "where 1=1 "
			+ "and (coalesce(:id, null) is null or p.id =:id) "
			+ "and (coalesce(:pageNumber, null) is null or p.pageNumber  =:pageNumber) "
			+ "and (coalesce(:chapter, null) is null  or p.chapter =:chapter) ")
	public Page finaPageByFilter(@Param("pageNumber") Integer pageNumber,
			@Param("chapter") Integer chapter,
			@Param("id") Long id);

}

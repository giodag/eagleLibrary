package com.univaq.eaglelibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.univaq.eaglelibrary.model.LiteraryWork;

public interface LiteraryWorkRepository extends JpaRepository<LiteraryWork, Long> {

	@Query("select lt from LiteraryWork lt "
			+ "where 1=1 "
			+ "and (coalesce(:id, null) is null or lt.id =:id) "
			+ "and (coalesce(:category, null) is null or lt.category  =:category) "
			+ "and (coalesce(:title, null) is null  or lt.title =:title) "
			+ "and (coalesce(:author, null) is null  or lt.author =:author) "
			+ "and (coalesce(:year, null) is null  or lt.year =:year) ")
	public LiteraryWork findLiteraryWorkByFilter(
			@Param("id") Long id,
			@Param("category") String category, 
			@Param("title") String title,
			@Param("year") Integer year,
			@Param("author") String author);
	
	@Query("select lt from LiteraryWork lt "
			+ "where 1=1 "
			+ "and (coalesce(:id, null) is null or lt.id in (:listId)) "
			+ "and (coalesce(:category, null) is null or lt.category  =:category) "
			+ "and (coalesce(:title, null) is null  or lt.title =:title) "
			+ "and (coalesce(:author, null) is null  or lt.author =:author) "
			+ "and (coalesce(:year, null) is null  or lt.year =:year) ")
	public List<LiteraryWork> findListLiteraryWorkByFilter(
			@Param("listId") List<Long> listId,
			@Param("category") String category, 
			@Param("title") String title,
			@Param("year") Integer year,
			@Param("author") String author);
	
}

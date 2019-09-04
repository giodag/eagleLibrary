package com.univaq.eaglelibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.univaq.eaglelibrary.model.Transcription;

public interface TranscriptionRepository extends JpaRepository<Transcription, Long> {
	
	@Query("select t from Transcription t "
			+ "where (coalesce(:idPage, null) is null or t.page.id =:idPage) ")
	public Transcription findTranscriptionByPage(@Param("idPage")Long idPage);
	
	public List<Transcription> findByTranscriptionLike(String partOfText);
	
	@Query("select t from Transcription t "
			+ "where 1=1 "
			+ "and (coalesce(:transcription, null) is null or t.transcription in (:transcription)) "
			+ "and (coalesce(:status, null) is null or t.status  =:status) "
			+ "and (coalesce(:page_id, null) is null  or t.page.id =:page_id) "
			+ "and (coalesce(:lockByUser, null) is null  or t.lockByUser =:lockByUser) ")
	public Transcription findByFilter(
			@Param("transcription")String transcription,
			@Param("status")String status,
			@Param("page_id")Long page_id,
			@Param("lockByUser")Long lockByUser);

}

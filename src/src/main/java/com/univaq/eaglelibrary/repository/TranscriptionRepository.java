package com.univaq.eaglelibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.univaq.eaglelibrary.model.Transcription;

public interface TranscriptionRepository extends JpaRepository<Transcription, Long> {
	
	@Query("select t from Transcription t "
			+ "where (coalesce(:idPage, null) is null or t.page.id =:idPage) ")
	public Transcription findTranscriptionByPage(Long idPage);
	
	public List<Transcription> findByTranscriptionLike(String partOfText);

}

package com.univaq.eaglelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.univaq.eaglelibrary.model.Transcription;

public interface TranscriptionRepository extends JpaRepository<Transcription, Long> {

}

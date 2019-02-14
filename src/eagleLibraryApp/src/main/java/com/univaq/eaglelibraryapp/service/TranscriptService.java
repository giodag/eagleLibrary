package com.univaq.eaglelibraryapp.service;

import com.univaq.eaglelibraryapp.service.dto.TranscriptDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Transcript.
 */
public interface TranscriptService {

    /**
     * Save a transcript.
     *
     * @param transcriptDTO the entity to save
     * @return the persisted entity
     */
    TranscriptDTO save(TranscriptDTO transcriptDTO);

    /**
     * Get all the transcripts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TranscriptDTO> findAll(Pageable pageable);


    /**
     * Get the "id" transcript.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TranscriptDTO> findOne(Long id);

    /**
     * Delete the "id" transcript.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

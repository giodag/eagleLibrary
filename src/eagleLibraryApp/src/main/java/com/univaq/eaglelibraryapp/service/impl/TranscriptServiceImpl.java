package com.univaq.eaglelibraryapp.service.impl;

import com.univaq.eaglelibraryapp.service.TranscriptService;
import com.univaq.eaglelibraryapp.domain.Transcript;
import com.univaq.eaglelibraryapp.repository.TranscriptRepository;
import com.univaq.eaglelibraryapp.repository.PageRepository;
import com.univaq.eaglelibraryapp.service.dto.TranscriptDTO;
import com.univaq.eaglelibraryapp.service.mapper.TranscriptMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Transcript.
 */
@Service
@Transactional
public class TranscriptServiceImpl implements TranscriptService {

    private final Logger log = LoggerFactory.getLogger(TranscriptServiceImpl.class);

    private final TranscriptRepository transcriptRepository;

    private final TranscriptMapper transcriptMapper;

    private final PageRepository pageRepository;

    public TranscriptServiceImpl(TranscriptRepository transcriptRepository, TranscriptMapper transcriptMapper, PageRepository pageRepository) {
        this.transcriptRepository = transcriptRepository;
        this.transcriptMapper = transcriptMapper;
        this.pageRepository = pageRepository;
    }

    /**
     * Save a transcript.
     *
     * @param transcriptDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TranscriptDTO save(TranscriptDTO transcriptDTO) {
        log.debug("Request to save Transcript : {}", transcriptDTO);
        Transcript transcript = transcriptMapper.toEntity(transcriptDTO);
        long pageId = transcriptDTO.getPageId();
        pageRepository.findById(pageId).ifPresent(transcript::page);
        transcript = transcriptRepository.save(transcript);
        return transcriptMapper.toDto(transcript);
    }

    /**
     * Get all the transcripts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TranscriptDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Transcripts");
        return transcriptRepository.findAll(pageable)
            .map(transcriptMapper::toDto);
    }

    /**
     * Get all the Transcript with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<TranscriptDTO> findAllWithEagerRelationships(Pageable pageable) {
        return transcriptRepository.findAllWithEagerRelationships(pageable).map(transcriptMapper::toDto);
    }
    

    /**
     * Get one transcript by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TranscriptDTO> findOne(Long id) {
        log.debug("Request to get Transcript : {}", id);
        return transcriptRepository.findOneWithEagerRelationships(id)
            .map(transcriptMapper::toDto);
    }

    /**
     * Delete the transcript by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transcript : {}", id);        transcriptRepository.deleteById(id);
    }
}

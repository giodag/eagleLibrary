package com.univaq.eaglelibraryapp.service;

import com.univaq.eaglelibraryapp.service.dto.Literary_workDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Literary_work.
 */
public interface Literary_workService {

    /**
     * Save a literary_work.
     *
     * @param literary_workDTO the entity to save
     * @return the persisted entity
     */
    Literary_workDTO save(Literary_workDTO literary_workDTO);

    /**
     * Get all the literary_works.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Literary_workDTO> findAll(Pageable pageable);


    /**
     * Get the "id" literary_work.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Literary_workDTO> findOne(Long id);

    /**
     * Delete the "id" literary_work.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

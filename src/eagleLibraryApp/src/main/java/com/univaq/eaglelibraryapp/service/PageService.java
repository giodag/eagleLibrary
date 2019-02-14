package com.univaq.eaglelibraryapp.service;

import com.univaq.eaglelibraryapp.service.dto.PageDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Page.
 */
public interface PageService {

    /**
     * Save a page.
     *
     * @param pageDTO the entity to save
     * @return the persisted entity
     */
    PageDTO save(PageDTO pageDTO);

    /**
     * Get all the pages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PageDTO> findAll(Pageable pageable);


    /**
     * Get the "id" page.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PageDTO> findOne(Long id);

    /**
     * Delete the "id" page.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

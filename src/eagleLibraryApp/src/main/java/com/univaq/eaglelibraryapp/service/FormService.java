package com.univaq.eaglelibraryapp.service;

import com.univaq.eaglelibraryapp.service.dto.FormDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Form.
 */
public interface FormService {

    /**
     * Save a form.
     *
     * @param formDTO the entity to save
     * @return the persisted entity
     */
    FormDTO save(FormDTO formDTO);

    /**
     * Get all the forms.
     *
     * @return the list of entities
     */
    List<FormDTO> findAll();


    /**
     * Get the "id" form.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FormDTO> findOne(Long id);

    /**
     * Delete the "id" form.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

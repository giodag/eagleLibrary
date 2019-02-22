package com.univaq.eaglelibraryapp.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.univaq.eaglelibraryapp.domain.Form;
import com.univaq.eaglelibraryapp.repository.FormRepository;
import com.univaq.eaglelibraryapp.repository.UserRepository;
import com.univaq.eaglelibraryapp.service.FormService;
import com.univaq.eaglelibraryapp.service.dto.FormDTO;
import com.univaq.eaglelibraryapp.service.mapper.FormMapper;

/**
 * Service Implementation for managing Form.
 */
@Service
@Transactional
public class FormServiceImpl implements FormService {

    private final Logger log = LoggerFactory.getLogger(FormServiceImpl.class);

    private final FormRepository formRepository;

    private final FormMapper formMapper;

    private final UserRepository userRepository;

    public FormServiceImpl(FormRepository formRepository, FormMapper formMapper, UserRepository userRepository) {
        this.formRepository = formRepository;
        this.formMapper = formMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a form.
     *
     * @param formDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FormDTO save(FormDTO formDTO) {
        log.debug("Request to save Form : {}", formDTO);
        Form form = formMapper.toEntity(formDTO);
        long userId = formDTO.getUserId();
        userRepository.findById(userId).ifPresent(form::user);
        form = formRepository.save(form);
        return formMapper.toDto(form);
    }

    /**
     * Get all the forms.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FormDTO> findAll() {
        log.debug("Request to get all Forms");
        return formRepository.findAll().stream()
            .map(formMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one form by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FormDTO> findOne(Long id) {
        log.debug("Request to get Form : {}", id);
        return formRepository.findById(id)
            .map(formMapper::toDto);
    }

    /**
     * Delete the form by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Form : {}", id);        formRepository.deleteById(id);
    }
}

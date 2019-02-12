package com.univaq.eaglelibraryapp.service.impl;

import com.univaq.eaglelibraryapp.service.Literary_workService;
import com.univaq.eaglelibraryapp.domain.Literary_work;
import com.univaq.eaglelibraryapp.repository.Literary_workRepository;
import com.univaq.eaglelibraryapp.service.dto.Literary_workDTO;
import com.univaq.eaglelibraryapp.service.mapper.Literary_workMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Literary_work.
 */
@Service
@Transactional
public class Literary_workServiceImpl implements Literary_workService {

    private final Logger log = LoggerFactory.getLogger(Literary_workServiceImpl.class);

    private final Literary_workRepository literary_workRepository;

    private final Literary_workMapper literary_workMapper;

    public Literary_workServiceImpl(Literary_workRepository literary_workRepository, Literary_workMapper literary_workMapper) {
        this.literary_workRepository = literary_workRepository;
        this.literary_workMapper = literary_workMapper;
    }

    /**
     * Save a literary_work.
     *
     * @param literary_workDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Literary_workDTO save(Literary_workDTO literary_workDTO) {
        log.debug("Request to save Literary_work : {}", literary_workDTO);

        Literary_work literary_work = literary_workMapper.toEntity(literary_workDTO);
        literary_work = literary_workRepository.save(literary_work);
        return literary_workMapper.toDto(literary_work);
    }

    /**
     * Get all the literary_works.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Literary_workDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Literary_works");
        return literary_workRepository.findAll(pageable)
            .map(literary_workMapper::toDto);
    }


    /**
     * Get one literary_work by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Literary_workDTO> findOne(Long id) {
        log.debug("Request to get Literary_work : {}", id);
        return literary_workRepository.findById(id)
            .map(literary_workMapper::toDto);
    }

    /**
     * Delete the literary_work by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Literary_work : {}", id);
        literary_workRepository.deleteById(id);
    }
}

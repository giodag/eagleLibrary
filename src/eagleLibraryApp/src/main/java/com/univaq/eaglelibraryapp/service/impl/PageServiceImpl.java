package com.univaq.eaglelibraryapp.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.univaq.eaglelibraryapp.domain.Page;
import com.univaq.eaglelibraryapp.repository.PageRepository;
import com.univaq.eaglelibraryapp.service.PageService;
import com.univaq.eaglelibraryapp.service.dto.PageDTO;
import com.univaq.eaglelibraryapp.service.mapper.PageMapper;

/**
 * Service Implementation for managing Page.
 */
@Service
@Transactional
public class PageServiceImpl implements PageService {

    private final Logger log = LoggerFactory.getLogger(PageServiceImpl.class);

    private final PageRepository pageRepository;

    private final PageMapper pageMapper;

    public PageServiceImpl(PageRepository pageRepository, PageMapper pageMapper) {
        this.pageRepository = pageRepository;
        this.pageMapper = pageMapper;
    }

    /**
     * Save a page.
     *
     * @param pageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PageDTO save(PageDTO pageDTO) {
        log.debug("Request to save Page : {}", pageDTO);

        Page page = pageMapper.toEntity(pageDTO);
        page = pageRepository.save(page);
        return pageMapper.toDto(page);
    }

    /**
     * Get one page by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PageDTO> findOne(Long id) {
        log.debug("Request to get Page : {}", id);
        return pageRepository.findById(id)
            .map(pageMapper::toDto);
    }

    /**
     * Delete the page by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Page : {}", id);
        pageRepository.deleteById(id);
    }

    
    /**
     * Get all the pages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
        
	@Override
	public org.springframework.data.domain.Page<PageDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Pages");
        return pageRepository.findAll(pageable)
            .map(pageMapper::toDto);
	}
}

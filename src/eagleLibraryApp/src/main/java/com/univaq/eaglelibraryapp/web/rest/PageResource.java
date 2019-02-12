package com.univaq.eaglelibraryapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.univaq.eaglelibraryapp.service.PageService;
import com.univaq.eaglelibraryapp.web.rest.errors.BadRequestAlertException;
import com.univaq.eaglelibraryapp.web.rest.util.HeaderUtil;
import com.univaq.eaglelibraryapp.web.rest.util.PaginationUtil;
import com.univaq.eaglelibraryapp.service.dto.PageDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Page.
 */
@RestController
@RequestMapping("/api")
public class PageResource {

    private final Logger log = LoggerFactory.getLogger(PageResource.class);

    private static final String ENTITY_NAME = "eagleLibraryAppPage";

    private final PageService pageService;

    public PageResource(PageService pageService) {
        this.pageService = pageService;
    }

    /**
     * POST  /pages : Create a new page.
     *
     * @param pageDTO the pageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pageDTO, or with status 400 (Bad Request) if the page has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pages")
    @Timed
    public ResponseEntity<PageDTO> createPage(@Valid @RequestBody PageDTO pageDTO) throws URISyntaxException {
        log.debug("REST request to save Page : {}", pageDTO);
        if (pageDTO.getId() != null) {
            throw new BadRequestAlertException("A new page cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageDTO result = pageService.save(pageDTO);
        return ResponseEntity.created(new URI("/api/pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pages : Updates an existing page.
     *
     * @param pageDTO the pageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pageDTO,
     * or with status 400 (Bad Request) if the pageDTO is not valid,
     * or with status 500 (Internal Server Error) if the pageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pages")
    @Timed
    public ResponseEntity<PageDTO> updatePage(@Valid @RequestBody PageDTO pageDTO) throws URISyntaxException {
        log.debug("REST request to update Page : {}", pageDTO);
        if (pageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PageDTO result = pageService.save(pageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pages : get all the pages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pages in body
     */
    @GetMapping("/pages")
    @Timed
    public ResponseEntity<List<PageDTO>> getAllPages(Pageable pageable) {
        log.debug("REST request to get a page of Pages");
        Page<PageDTO> page = pageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pages");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /pages/:id : get the "id" page.
     *
     * @param id the id of the pageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pages/{id}")
    @Timed
    public ResponseEntity<PageDTO> getPage(@PathVariable Long id) {
        log.debug("REST request to get Page : {}", id);
        Optional<PageDTO> pageDTO = pageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pageDTO);
    }

    /**
     * DELETE  /pages/:id : delete the "id" page.
     *
     * @param id the id of the pageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pages/{id}")
    @Timed
    public ResponseEntity<Void> deletePage(@PathVariable Long id) {
        log.debug("REST request to delete Page : {}", id);
        pageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

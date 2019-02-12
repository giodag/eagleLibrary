package com.univaq.eaglelibraryapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.univaq.eaglelibraryapp.service.Literary_workService;
import com.univaq.eaglelibraryapp.web.rest.errors.BadRequestAlertException;
import com.univaq.eaglelibraryapp.web.rest.util.HeaderUtil;
import com.univaq.eaglelibraryapp.web.rest.util.PaginationUtil;
import com.univaq.eaglelibraryapp.service.dto.Literary_workDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Literary_work.
 */
@RestController
@RequestMapping("/api")
public class Literary_workResource {

    private final Logger log = LoggerFactory.getLogger(Literary_workResource.class);

    private static final String ENTITY_NAME = "eagleLibraryAppLiteraryWork";

    private final Literary_workService literary_workService;

    public Literary_workResource(Literary_workService literary_workService) {
        this.literary_workService = literary_workService;
    }

    /**
     * POST  /literary-works : Create a new literary_work.
     *
     * @param literary_workDTO the literary_workDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new literary_workDTO, or with status 400 (Bad Request) if the literary_work has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/literary-works")
    @Timed
    public ResponseEntity<Literary_workDTO> createLiterary_work(@RequestBody Literary_workDTO literary_workDTO) throws URISyntaxException {
        log.debug("REST request to save Literary_work : {}", literary_workDTO);
        if (literary_workDTO.getId() != null) {
            throw new BadRequestAlertException("A new literary_work cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Literary_workDTO result = literary_workService.save(literary_workDTO);
        return ResponseEntity.created(new URI("/api/literary-works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /literary-works : Updates an existing literary_work.
     *
     * @param literary_workDTO the literary_workDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated literary_workDTO,
     * or with status 400 (Bad Request) if the literary_workDTO is not valid,
     * or with status 500 (Internal Server Error) if the literary_workDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/literary-works")
    @Timed
    public ResponseEntity<Literary_workDTO> updateLiterary_work(@RequestBody Literary_workDTO literary_workDTO) throws URISyntaxException {
        log.debug("REST request to update Literary_work : {}", literary_workDTO);
        if (literary_workDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Literary_workDTO result = literary_workService.save(literary_workDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, literary_workDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /literary-works : get all the literary_works.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of literary_works in body
     */
    @GetMapping("/literary-works")
    @Timed
    public ResponseEntity<List<Literary_workDTO>> getAllLiterary_works(Pageable pageable) {
        log.debug("REST request to get a page of Literary_works");
        Page<Literary_workDTO> page = literary_workService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/literary-works");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /literary-works/:id : get the "id" literary_work.
     *
     * @param id the id of the literary_workDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the literary_workDTO, or with status 404 (Not Found)
     */
    @GetMapping("/literary-works/{id}")
    @Timed
    public ResponseEntity<Literary_workDTO> getLiterary_work(@PathVariable Long id) {
        log.debug("REST request to get Literary_work : {}", id);
        Optional<Literary_workDTO> literary_workDTO = literary_workService.findOne(id);
        return ResponseUtil.wrapOrNotFound(literary_workDTO);
    }

    /**
     * DELETE  /literary-works/:id : delete the "id" literary_work.
     *
     * @param id the id of the literary_workDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/literary-works/{id}")
    @Timed
    public ResponseEntity<Void> deleteLiterary_work(@PathVariable Long id) {
        log.debug("REST request to delete Literary_work : {}", id);
        literary_workService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

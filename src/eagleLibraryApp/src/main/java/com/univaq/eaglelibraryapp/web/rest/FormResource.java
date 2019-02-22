package com.univaq.eaglelibraryapp.web.rest;
import com.univaq.eaglelibraryapp.service.FormService;
import com.univaq.eaglelibraryapp.web.rest.errors.BadRequestAlertException;
import com.univaq.eaglelibraryapp.web.rest.util.HeaderUtil;
import com.univaq.eaglelibraryapp.service.dto.FormDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing Form.
 */
@RestController
@RequestMapping("/api")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

    private static final String ENTITY_NAME = "eagleLibraryAppForm";

    private final FormService formService;

    public FormResource(FormService formService) {
        this.formService = formService;
    }

    /**
     * POST  /forms : Create a new form.
     *
     * @param formDTO the formDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formDTO, or with status 400 (Bad Request) if the form has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/forms")
    public ResponseEntity<FormDTO> createForm(@RequestBody FormDTO formDTO) throws URISyntaxException {
        log.debug("REST request to save Form : {}", formDTO);
        if (formDTO.getId() != null) {
            throw new BadRequestAlertException("A new form cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(formDTO.getUserId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        FormDTO result = formService.save(formDTO);
        return ResponseEntity.created(new URI("/api/forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forms : Updates an existing form.
     *
     * @param formDTO the formDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formDTO,
     * or with status 400 (Bad Request) if the formDTO is not valid,
     * or with status 500 (Internal Server Error) if the formDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/forms")
    public ResponseEntity<FormDTO> updateForm(@RequestBody FormDTO formDTO) throws URISyntaxException {
        log.debug("REST request to update Form : {}", formDTO);
        if (formDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormDTO result = formService.save(formDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forms : get all the forms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of forms in body
     */
    @GetMapping("/forms")
    public List<FormDTO> getAllForms() {
        log.debug("REST request to get all Forms");
        return formService.findAll();
    }

    /**
     * GET  /forms/:id : get the "id" form.
     *
     * @param id the id of the formDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formDTO, or with status 404 (Not Found)
     */
    @GetMapping("/forms/{id}")
    public ResponseEntity<FormDTO> getForm(@PathVariable Long id) {
        log.debug("REST request to get Form : {}", id);
        Optional<FormDTO> formDTO = formService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formDTO);
    }

    /**
     * DELETE  /forms/:id : delete the "id" form.
     *
     * @param id the id of the formDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/forms/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable Long id) {
        log.debug("REST request to delete Form : {}", id);
        formService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

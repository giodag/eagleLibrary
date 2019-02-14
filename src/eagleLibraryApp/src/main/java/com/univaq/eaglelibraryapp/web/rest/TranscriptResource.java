package com.univaq.eaglelibraryapp.web.rest;
import com.univaq.eaglelibraryapp.service.TranscriptService;
import com.univaq.eaglelibraryapp.web.rest.errors.BadRequestAlertException;
import com.univaq.eaglelibraryapp.web.rest.util.HeaderUtil;
import com.univaq.eaglelibraryapp.web.rest.util.PaginationUtil;
import com.univaq.eaglelibraryapp.service.dto.TranscriptDTO;
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
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing Transcript.
 */
@RestController
@RequestMapping("/api")
public class TranscriptResource {

    private final Logger log = LoggerFactory.getLogger(TranscriptResource.class);

    private static final String ENTITY_NAME = "eagleLibraryAppTranscript";

    private final TranscriptService transcriptService;

    public TranscriptResource(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    /**
     * POST  /transcripts : Create a new transcript.
     *
     * @param transcriptDTO the transcriptDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transcriptDTO, or with status 400 (Bad Request) if the transcript has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transcripts")
    public ResponseEntity<TranscriptDTO> createTranscript(@RequestBody TranscriptDTO transcriptDTO) throws URISyntaxException {
        log.debug("REST request to save Transcript : {}", transcriptDTO);
        if (transcriptDTO.getId() != null) {
            throw new BadRequestAlertException("A new transcript cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(transcriptDTO.getPageId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        TranscriptDTO result = transcriptService.save(transcriptDTO);
        return ResponseEntity.created(new URI("/api/transcripts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transcripts : Updates an existing transcript.
     *
     * @param transcriptDTO the transcriptDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transcriptDTO,
     * or with status 400 (Bad Request) if the transcriptDTO is not valid,
     * or with status 500 (Internal Server Error) if the transcriptDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transcripts")
    public ResponseEntity<TranscriptDTO> updateTranscript(@RequestBody TranscriptDTO transcriptDTO) throws URISyntaxException {
        log.debug("REST request to update Transcript : {}", transcriptDTO);
        if (transcriptDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TranscriptDTO result = transcriptService.save(transcriptDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transcriptDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transcripts : get all the transcripts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of transcripts in body
     */
    @GetMapping("/transcripts")
    public ResponseEntity<List<TranscriptDTO>> getAllTranscripts(Pageable pageable) {
        log.debug("REST request to get a page of Transcripts");
        Page<TranscriptDTO> page = transcriptService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/transcripts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /transcripts/:id : get the "id" transcript.
     *
     * @param id the id of the transcriptDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transcriptDTO, or with status 404 (Not Found)
     */
    @GetMapping("/transcripts/{id}")
    public ResponseEntity<TranscriptDTO> getTranscript(@PathVariable Long id) {
        log.debug("REST request to get Transcript : {}", id);
        Optional<TranscriptDTO> transcriptDTO = transcriptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transcriptDTO);
    }

    /**
     * DELETE  /transcripts/:id : delete the "id" transcript.
     *
     * @param id the id of the transcriptDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transcripts/{id}")
    public ResponseEntity<Void> deleteTranscript(@PathVariable Long id) {
        log.debug("REST request to delete Transcript : {}", id);
        transcriptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

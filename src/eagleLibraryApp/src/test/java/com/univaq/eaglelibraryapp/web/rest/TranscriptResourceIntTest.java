package com.univaq.eaglelibraryapp.web.rest;

import com.univaq.eaglelibraryapp.EagleLibraryApp;

import com.univaq.eaglelibraryapp.domain.Transcript;
import com.univaq.eaglelibraryapp.domain.Page;
import com.univaq.eaglelibraryapp.repository.TranscriptRepository;
import com.univaq.eaglelibraryapp.service.TranscriptService;
import com.univaq.eaglelibraryapp.service.dto.TranscriptDTO;
import com.univaq.eaglelibraryapp.service.mapper.TranscriptMapper;
import com.univaq.eaglelibraryapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.univaq.eaglelibraryapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TranscriptResource REST controller.
 *
 * @see TranscriptResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EagleLibraryApp.class)
public class TranscriptResourceIntTest {

    private static final String DEFAULT_TEI_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_TEI_FORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private TranscriptRepository transcriptRepository;

    @Mock
    private TranscriptRepository transcriptRepositoryMock;

    @Autowired
    private TranscriptMapper transcriptMapper;

    @Mock
    private TranscriptService transcriptServiceMock;

    @Autowired
    private TranscriptService transcriptService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTranscriptMockMvc;

    private Transcript transcript;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TranscriptResource transcriptResource = new TranscriptResource(transcriptService);
        this.restTranscriptMockMvc = MockMvcBuilders.standaloneSetup(transcriptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transcript createEntity(EntityManager em) {
        Transcript transcript = new Transcript()
            .tei_format(DEFAULT_TEI_FORMAT)
            .status(DEFAULT_STATUS);
        // Add required entity
        Page page = PageResourceIntTest.createEntity(em);
        em.persist(page);
        em.flush();
        transcript.setPage(page);
        return transcript;
    }

    @Before
    public void initTest() {
        transcript = createEntity(em);
    }

    @Test
    @Transactional
    public void createTranscript() throws Exception {
        int databaseSizeBeforeCreate = transcriptRepository.findAll().size();

        // Create the Transcript
        TranscriptDTO transcriptDTO = transcriptMapper.toDto(transcript);
        restTranscriptMockMvc.perform(post("/api/transcripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transcriptDTO)))
            .andExpect(status().isCreated());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeCreate + 1);
        Transcript testTranscript = transcriptList.get(transcriptList.size() - 1);
        assertThat(testTranscript.getTei_format()).isEqualTo(DEFAULT_TEI_FORMAT);
        assertThat(testTranscript.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the id for MapsId, the ids must be same
        assertThat(testTranscript.getId()).isEqualTo(testTranscript.getPage().getId());
    }

    @Test
    @Transactional
    public void createTranscriptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transcriptRepository.findAll().size();

        // Create the Transcript with an existing ID
        transcript.setId(1L);
        TranscriptDTO transcriptDTO = transcriptMapper.toDto(transcript);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTranscriptMockMvc.perform(post("/api/transcripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transcriptDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTranscripts() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);

        // Get all the transcriptList
        restTranscriptMockMvc.perform(get("/api/transcripts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transcript.getId().intValue())))
            .andExpect(jsonPath("$.[*].tei_format").value(hasItem(DEFAULT_TEI_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTranscriptsWithEagerRelationshipsIsEnabled() throws Exception {
        TranscriptResource transcriptResource = new TranscriptResource(transcriptServiceMock);
        when(transcriptServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTranscriptMockMvc = MockMvcBuilders.standaloneSetup(transcriptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTranscriptMockMvc.perform(get("/api/transcripts?eagerload=true"))
        .andExpect(status().isOk());

        verify(transcriptServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTranscriptsWithEagerRelationshipsIsNotEnabled() throws Exception {
        TranscriptResource transcriptResource = new TranscriptResource(transcriptServiceMock);
            when(transcriptServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTranscriptMockMvc = MockMvcBuilders.standaloneSetup(transcriptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTranscriptMockMvc.perform(get("/api/transcripts?eagerload=true"))
        .andExpect(status().isOk());

            verify(transcriptServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTranscript() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);

        // Get the transcript
        restTranscriptMockMvc.perform(get("/api/transcripts/{id}", transcript.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transcript.getId().intValue()))
            .andExpect(jsonPath("$.tei_format").value(DEFAULT_TEI_FORMAT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTranscript() throws Exception {
        // Get the transcript
        restTranscriptMockMvc.perform(get("/api/transcripts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTranscript() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);

        int databaseSizeBeforeUpdate = transcriptRepository.findAll().size();

        // Update the transcript
        Transcript updatedTranscript = transcriptRepository.findById(transcript.getId()).get();
        // Disconnect from session so that the updates on updatedTranscript are not directly saved in db
        em.detach(updatedTranscript);
        updatedTranscript
            .tei_format(UPDATED_TEI_FORMAT)
            .status(UPDATED_STATUS);
        TranscriptDTO transcriptDTO = transcriptMapper.toDto(updatedTranscript);

        restTranscriptMockMvc.perform(put("/api/transcripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transcriptDTO)))
            .andExpect(status().isOk());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeUpdate);
        Transcript testTranscript = transcriptList.get(transcriptList.size() - 1);
        assertThat(testTranscript.getTei_format()).isEqualTo(UPDATED_TEI_FORMAT);
        assertThat(testTranscript.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingTranscript() throws Exception {
        int databaseSizeBeforeUpdate = transcriptRepository.findAll().size();

        // Create the Transcript
        TranscriptDTO transcriptDTO = transcriptMapper.toDto(transcript);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranscriptMockMvc.perform(put("/api/transcripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transcriptDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTranscript() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);

        int databaseSizeBeforeDelete = transcriptRepository.findAll().size();

        // Delete the transcript
        restTranscriptMockMvc.perform(delete("/api/transcripts/{id}", transcript.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transcript.class);
        Transcript transcript1 = new Transcript();
        transcript1.setId(1L);
        Transcript transcript2 = new Transcript();
        transcript2.setId(transcript1.getId());
        assertThat(transcript1).isEqualTo(transcript2);
        transcript2.setId(2L);
        assertThat(transcript1).isNotEqualTo(transcript2);
        transcript1.setId(null);
        assertThat(transcript1).isNotEqualTo(transcript2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TranscriptDTO.class);
        TranscriptDTO transcriptDTO1 = new TranscriptDTO();
        transcriptDTO1.setId(1L);
        TranscriptDTO transcriptDTO2 = new TranscriptDTO();
        assertThat(transcriptDTO1).isNotEqualTo(transcriptDTO2);
        transcriptDTO2.setId(transcriptDTO1.getId());
        assertThat(transcriptDTO1).isEqualTo(transcriptDTO2);
        transcriptDTO2.setId(2L);
        assertThat(transcriptDTO1).isNotEqualTo(transcriptDTO2);
        transcriptDTO1.setId(null);
        assertThat(transcriptDTO1).isNotEqualTo(transcriptDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transcriptMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transcriptMapper.fromId(null)).isNull();
    }
}

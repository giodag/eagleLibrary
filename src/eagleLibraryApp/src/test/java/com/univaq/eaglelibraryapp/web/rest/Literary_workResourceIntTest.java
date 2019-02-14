package com.univaq.eaglelibraryapp.web.rest;

import com.univaq.eaglelibraryapp.EagleLibraryApp;

import com.univaq.eaglelibraryapp.domain.Literary_work;
import com.univaq.eaglelibraryapp.repository.Literary_workRepository;
import com.univaq.eaglelibraryapp.service.Literary_workService;
import com.univaq.eaglelibraryapp.service.dto.Literary_workDTO;
import com.univaq.eaglelibraryapp.service.mapper.Literary_workMapper;
import com.univaq.eaglelibraryapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.univaq.eaglelibraryapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Literary_workResource REST controller.
 *
 * @see Literary_workResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EagleLibraryApp.class)
public class Literary_workResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_PAGES = 1;
    private static final Integer UPDATED_NUMBER_OF_PAGES = 2;

    @Autowired
    private Literary_workRepository literary_workRepository;

    @Autowired
    private Literary_workMapper literary_workMapper;

    @Autowired
    private Literary_workService literary_workService;

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

    private MockMvc restLiterary_workMockMvc;

    private Literary_work literary_work;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Literary_workResource literary_workResource = new Literary_workResource(literary_workService);
        this.restLiterary_workMockMvc = MockMvcBuilders.standaloneSetup(literary_workResource)
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
    public static Literary_work createEntity(EntityManager em) {
        Literary_work literary_work = new Literary_work()
            .title(DEFAULT_TITLE)
            .author(DEFAULT_AUTHOR)
            .year(DEFAULT_YEAR)
            .category(DEFAULT_CATEGORY)
            .number_of_pages(DEFAULT_NUMBER_OF_PAGES);
        return literary_work;
    }

    @Before
    public void initTest() {
        literary_work = createEntity(em);
    }

    @Test
    @Transactional
    public void createLiterary_work() throws Exception {
        int databaseSizeBeforeCreate = literary_workRepository.findAll().size();

        // Create the Literary_work
        Literary_workDTO literary_workDTO = literary_workMapper.toDto(literary_work);
        restLiterary_workMockMvc.perform(post("/api/literary-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(literary_workDTO)))
            .andExpect(status().isCreated());

        // Validate the Literary_work in the database
        List<Literary_work> literary_workList = literary_workRepository.findAll();
        assertThat(literary_workList).hasSize(databaseSizeBeforeCreate + 1);
        Literary_work testLiterary_work = literary_workList.get(literary_workList.size() - 1);
        assertThat(testLiterary_work.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLiterary_work.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testLiterary_work.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLiterary_work.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testLiterary_work.getNumber_of_pages()).isEqualTo(DEFAULT_NUMBER_OF_PAGES);
    }

    @Test
    @Transactional
    public void createLiterary_workWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = literary_workRepository.findAll().size();

        // Create the Literary_work with an existing ID
        literary_work.setId(1L);
        Literary_workDTO literary_workDTO = literary_workMapper.toDto(literary_work);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLiterary_workMockMvc.perform(post("/api/literary-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(literary_workDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Literary_work in the database
        List<Literary_work> literary_workList = literary_workRepository.findAll();
        assertThat(literary_workList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLiterary_works() throws Exception {
        // Initialize the database
        literary_workRepository.saveAndFlush(literary_work);

        // Get all the literary_workList
        restLiterary_workMockMvc.perform(get("/api/literary-works?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(literary_work.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].number_of_pages").value(hasItem(DEFAULT_NUMBER_OF_PAGES)));
    }
    
    @Test
    @Transactional
    public void getLiterary_work() throws Exception {
        // Initialize the database
        literary_workRepository.saveAndFlush(literary_work);

        // Get the literary_work
        restLiterary_workMockMvc.perform(get("/api/literary-works/{id}", literary_work.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(literary_work.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.number_of_pages").value(DEFAULT_NUMBER_OF_PAGES));
    }

    @Test
    @Transactional
    public void getNonExistingLiterary_work() throws Exception {
        // Get the literary_work
        restLiterary_workMockMvc.perform(get("/api/literary-works/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLiterary_work() throws Exception {
        // Initialize the database
        literary_workRepository.saveAndFlush(literary_work);

        int databaseSizeBeforeUpdate = literary_workRepository.findAll().size();

        // Update the literary_work
        Literary_work updatedLiterary_work = literary_workRepository.findById(literary_work.getId()).get();
        // Disconnect from session so that the updates on updatedLiterary_work are not directly saved in db
        em.detach(updatedLiterary_work);
        updatedLiterary_work
            .title(UPDATED_TITLE)
            .author(UPDATED_AUTHOR)
            .year(UPDATED_YEAR)
            .category(UPDATED_CATEGORY)
            .number_of_pages(UPDATED_NUMBER_OF_PAGES);
        Literary_workDTO literary_workDTO = literary_workMapper.toDto(updatedLiterary_work);

        restLiterary_workMockMvc.perform(put("/api/literary-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(literary_workDTO)))
            .andExpect(status().isOk());

        // Validate the Literary_work in the database
        List<Literary_work> literary_workList = literary_workRepository.findAll();
        assertThat(literary_workList).hasSize(databaseSizeBeforeUpdate);
        Literary_work testLiterary_work = literary_workList.get(literary_workList.size() - 1);
        assertThat(testLiterary_work.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLiterary_work.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testLiterary_work.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLiterary_work.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testLiterary_work.getNumber_of_pages()).isEqualTo(UPDATED_NUMBER_OF_PAGES);
    }

    @Test
    @Transactional
    public void updateNonExistingLiterary_work() throws Exception {
        int databaseSizeBeforeUpdate = literary_workRepository.findAll().size();

        // Create the Literary_work
        Literary_workDTO literary_workDTO = literary_workMapper.toDto(literary_work);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLiterary_workMockMvc.perform(put("/api/literary-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(literary_workDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Literary_work in the database
        List<Literary_work> literary_workList = literary_workRepository.findAll();
        assertThat(literary_workList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLiterary_work() throws Exception {
        // Initialize the database
        literary_workRepository.saveAndFlush(literary_work);

        int databaseSizeBeforeDelete = literary_workRepository.findAll().size();

        // Get the literary_work
        restLiterary_workMockMvc.perform(delete("/api/literary-works/{id}", literary_work.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Literary_work> literary_workList = literary_workRepository.findAll();
        assertThat(literary_workList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Literary_work.class);
        Literary_work literary_work1 = new Literary_work();
        literary_work1.setId(1L);
        Literary_work literary_work2 = new Literary_work();
        literary_work2.setId(literary_work1.getId());
        assertThat(literary_work1).isEqualTo(literary_work2);
        literary_work2.setId(2L);
        assertThat(literary_work1).isNotEqualTo(literary_work2);
        literary_work1.setId(null);
        assertThat(literary_work1).isNotEqualTo(literary_work2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Literary_workDTO.class);
        Literary_workDTO literary_workDTO1 = new Literary_workDTO();
        literary_workDTO1.setId(1L);
        Literary_workDTO literary_workDTO2 = new Literary_workDTO();
        assertThat(literary_workDTO1).isNotEqualTo(literary_workDTO2);
        literary_workDTO2.setId(literary_workDTO1.getId());
        assertThat(literary_workDTO1).isEqualTo(literary_workDTO2);
        literary_workDTO2.setId(2L);
        assertThat(literary_workDTO1).isNotEqualTo(literary_workDTO2);
        literary_workDTO1.setId(null);
        assertThat(literary_workDTO1).isNotEqualTo(literary_workDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(literary_workMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(literary_workMapper.fromId(null)).isNull();
    }
}

package com.univaq.eaglelibraryapp.web.rest;

import static com.univaq.eaglelibraryapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;

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

import com.univaq.eaglelibraryapp.EagleLibraryApp;
import com.univaq.eaglelibraryapp.domain.Form;
import com.univaq.eaglelibraryapp.domain.User;
import com.univaq.eaglelibraryapp.repository.FormRepository;
import com.univaq.eaglelibraryapp.service.FormService;
import com.univaq.eaglelibraryapp.service.dto.FormDTO;
import com.univaq.eaglelibraryapp.service.mapper.FormMapper;
import com.univaq.eaglelibraryapp.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the FormResource REST controller.
 *
 * @see FormResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EagleLibraryApp.class)
public class FormResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR_OF_THE_STUDY = 1;
    private static final Integer UPDATED_YEAR_OF_THE_STUDY = 2;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private FormMapper formMapper;

    @Autowired
    private FormService formService;

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

    private MockMvc restFormMockMvc;

    private Form form;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormResource formResource = new FormResource(formService);
        this.restFormMockMvc = MockMvcBuilders.standaloneSetup(formResource)
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
    public static Form createEntity(EntityManager em) {
        Form form = new Form()
            .code(DEFAULT_CODE)
            .year_of_the_study(DEFAULT_YEAR_OF_THE_STUDY)
            .comment(DEFAULT_COMMENT)
            .creation_date(DEFAULT_CREATION_DATE);
//        // Add required entity
//        User user = UserResourceIntTest.createEntity(em);
//        em.persist(user);
//        em.flush();
//        form.setUser(user);
        return form;
    }

    @Before
    public void initTest() {
        form = createEntity(em);
    }

    @Test
    @Transactional
    public void createForm() throws Exception {
        int databaseSizeBeforeCreate = formRepository.findAll().size();

        // Create the Form
        FormDTO formDTO = formMapper.toDto(form);
        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isCreated());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeCreate + 1);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testForm.getYear_of_the_study()).isEqualTo(DEFAULT_YEAR_OF_THE_STUDY);
        assertThat(testForm.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testForm.getCreation_date()).isEqualTo(DEFAULT_CREATION_DATE);

        // Validate the id for MapsId, the ids must be same
        assertThat(testForm.getId()).isEqualTo(testForm.getUser().getId());
    }

    @Test
    @Transactional
    public void createFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formRepository.findAll().size();

        // Create the Form with an existing ID
        form.setId(1L);
        FormDTO formDTO = formMapper.toDto(form);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllForms() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList
        restFormMockMvc.perform(get("/api/forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(form.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].year_of_the_study").value(hasItem(DEFAULT_YEAR_OF_THE_STUDY)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].creation_date").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get the form
        restFormMockMvc.perform(get("/api/forms/{id}", form.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(form.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.year_of_the_study").value(DEFAULT_YEAR_OF_THE_STUDY))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.creation_date").value(DEFAULT_CREATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingForm() throws Exception {
        // Get the form
        restFormMockMvc.perform(get("/api/forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        int databaseSizeBeforeUpdate = formRepository.findAll().size();

        // Update the form
        Form updatedForm = formRepository.findById(form.getId()).get();
        // Disconnect from session so that the updates on updatedForm are not directly saved in db
        em.detach(updatedForm);
        updatedForm
            .code(UPDATED_CODE)
            .year_of_the_study(UPDATED_YEAR_OF_THE_STUDY)
            .comment(UPDATED_COMMENT)
            .creation_date(UPDATED_CREATION_DATE);
        FormDTO formDTO = formMapper.toDto(updatedForm);

        restFormMockMvc.perform(put("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isOk());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testForm.getYear_of_the_study()).isEqualTo(UPDATED_YEAR_OF_THE_STUDY);
        assertThat(testForm.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testForm.getCreation_date()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingForm() throws Exception {
        int databaseSizeBeforeUpdate = formRepository.findAll().size();

        // Create the Form
        FormDTO formDTO = formMapper.toDto(form);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormMockMvc.perform(put("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        int databaseSizeBeforeDelete = formRepository.findAll().size();

        // Delete the form
        restFormMockMvc.perform(delete("/api/forms/{id}", form.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Form.class);
        Form form1 = new Form();
        form1.setId(1L);
        Form form2 = new Form();
        form2.setId(form1.getId());
        assertThat(form1).isEqualTo(form2);
        form2.setId(2L);
        assertThat(form1).isNotEqualTo(form2);
        form1.setId(null);
        assertThat(form1).isNotEqualTo(form2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormDTO.class);
        FormDTO formDTO1 = new FormDTO();
        formDTO1.setId(1L);
        FormDTO formDTO2 = new FormDTO();
        assertThat(formDTO1).isNotEqualTo(formDTO2);
        formDTO2.setId(formDTO1.getId());
        assertThat(formDTO1).isEqualTo(formDTO2);
        formDTO2.setId(2L);
        assertThat(formDTO1).isNotEqualTo(formDTO2);
        formDTO1.setId(null);
        assertThat(formDTO1).isNotEqualTo(formDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(formMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(formMapper.fromId(null)).isNull();
    }
}

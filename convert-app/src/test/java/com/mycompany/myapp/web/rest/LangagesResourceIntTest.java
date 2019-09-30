package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ConvertappApp;

import com.mycompany.myapp.domain.Langages;
import com.mycompany.myapp.repository.LangagesRepository;
import com.mycompany.myapp.service.LangagesService;
import com.mycompany.myapp.service.dto.LangagesDTO;
import com.mycompany.myapp.service.mapper.LangagesMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LangagesResource REST controller.
 *
 * @see LangagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConvertappApp.class)
public class LangagesResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private LangagesRepository langagesRepository;

    @Autowired
    private LangagesMapper langagesMapper;

    @Autowired
    private LangagesService langagesService;

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

    private MockMvc restLangagesMockMvc;

    private Langages langages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LangagesResource langagesResource = new LangagesResource(langagesService);
        this.restLangagesMockMvc = MockMvcBuilders.standaloneSetup(langagesResource)
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
    public static Langages createEntity(EntityManager em) {
        Langages langages = new Langages()
            .nom(DEFAULT_NOM)
            .code(DEFAULT_CODE);
        return langages;
    }

    @Before
    public void initTest() {
        langages = createEntity(em);
    }

    @Test
    @Transactional
    public void createLangages() throws Exception {
        int databaseSizeBeforeCreate = langagesRepository.findAll().size();

        // Create the Langages
        LangagesDTO langagesDTO = langagesMapper.toDto(langages);
        restLangagesMockMvc.perform(post("/api/langages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(langagesDTO)))
            .andExpect(status().isCreated());

        // Validate the Langages in the database
        List<Langages> langagesList = langagesRepository.findAll();
        assertThat(langagesList).hasSize(databaseSizeBeforeCreate + 1);
        Langages testLangages = langagesList.get(langagesList.size() - 1);
        assertThat(testLangages.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testLangages.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createLangagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = langagesRepository.findAll().size();

        // Create the Langages with an existing ID
        langages.setId(1L);
        LangagesDTO langagesDTO = langagesMapper.toDto(langages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLangagesMockMvc.perform(post("/api/langages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(langagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Langages in the database
        List<Langages> langagesList = langagesRepository.findAll();
        assertThat(langagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLangages() throws Exception {
        // Initialize the database
        langagesRepository.saveAndFlush(langages);

        // Get all the langagesList
        restLangagesMockMvc.perform(get("/api/langages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(langages.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getLangages() throws Exception {
        // Initialize the database
        langagesRepository.saveAndFlush(langages);

        // Get the langages
        restLangagesMockMvc.perform(get("/api/langages/{id}", langages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(langages.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLangages() throws Exception {
        // Get the langages
        restLangagesMockMvc.perform(get("/api/langages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLangages() throws Exception {
        // Initialize the database
        langagesRepository.saveAndFlush(langages);

        int databaseSizeBeforeUpdate = langagesRepository.findAll().size();

        // Update the langages
        Langages updatedLangages = langagesRepository.findById(langages.getId()).get();
        // Disconnect from session so that the updates on updatedLangages are not directly saved in db
        em.detach(updatedLangages);
        updatedLangages
            .nom(UPDATED_NOM)
            .code(UPDATED_CODE);
        LangagesDTO langagesDTO = langagesMapper.toDto(updatedLangages);

        restLangagesMockMvc.perform(put("/api/langages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(langagesDTO)))
            .andExpect(status().isOk());

        // Validate the Langages in the database
        List<Langages> langagesList = langagesRepository.findAll();
        assertThat(langagesList).hasSize(databaseSizeBeforeUpdate);
        Langages testLangages = langagesList.get(langagesList.size() - 1);
        assertThat(testLangages.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testLangages.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingLangages() throws Exception {
        int databaseSizeBeforeUpdate = langagesRepository.findAll().size();

        // Create the Langages
        LangagesDTO langagesDTO = langagesMapper.toDto(langages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLangagesMockMvc.perform(put("/api/langages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(langagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Langages in the database
        List<Langages> langagesList = langagesRepository.findAll();
        assertThat(langagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLangages() throws Exception {
        // Initialize the database
        langagesRepository.saveAndFlush(langages);

        int databaseSizeBeforeDelete = langagesRepository.findAll().size();

        // Delete the langages
        restLangagesMockMvc.perform(delete("/api/langages/{id}", langages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Langages> langagesList = langagesRepository.findAll();
        assertThat(langagesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Langages.class);
        Langages langages1 = new Langages();
        langages1.setId(1L);
        Langages langages2 = new Langages();
        langages2.setId(langages1.getId());
        assertThat(langages1).isEqualTo(langages2);
        langages2.setId(2L);
        assertThat(langages1).isNotEqualTo(langages2);
        langages1.setId(null);
        assertThat(langages1).isNotEqualTo(langages2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LangagesDTO.class);
        LangagesDTO langagesDTO1 = new LangagesDTO();
        langagesDTO1.setId(1L);
        LangagesDTO langagesDTO2 = new LangagesDTO();
        assertThat(langagesDTO1).isNotEqualTo(langagesDTO2);
        langagesDTO2.setId(langagesDTO1.getId());
        assertThat(langagesDTO1).isEqualTo(langagesDTO2);
        langagesDTO2.setId(2L);
        assertThat(langagesDTO1).isNotEqualTo(langagesDTO2);
        langagesDTO1.setId(null);
        assertThat(langagesDTO1).isNotEqualTo(langagesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(langagesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(langagesMapper.fromId(null)).isNull();
    }
}

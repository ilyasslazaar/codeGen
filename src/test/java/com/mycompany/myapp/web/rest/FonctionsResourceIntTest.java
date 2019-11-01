package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ConvertappApp;

import com.mycompany.myapp.domain.Fonctions;
import com.mycompany.myapp.repository.FonctionsRepository;
import com.mycompany.myapp.service.FonctionsService;
import com.mycompany.myapp.service.dto.FonctionsDTO;
import com.mycompany.myapp.service.mapper.FonctionsMapper;
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
 * Test class for the FonctionsResource REST controller.
 *
 * @see FonctionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConvertappApp.class)
public class FonctionsResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private FonctionsRepository fonctionsRepository;

    @Autowired
    private FonctionsMapper fonctionsMapper;

    @Autowired
    private FonctionsService fonctionsService;

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

    private MockMvc restFonctionsMockMvc;

    private Fonctions fonctions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FonctionsResource fonctionsResource = new FonctionsResource(fonctionsService);
        this.restFonctionsMockMvc = MockMvcBuilders.standaloneSetup(fonctionsResource)
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
    public static Fonctions createEntity(EntityManager em) {
        Fonctions fonctions = new Fonctions()
            .nom(DEFAULT_NOM)
            .type(DEFAULT_TYPE);
        return fonctions;
    }

    @Before
    public void initTest() {
        fonctions = createEntity(em);
    }

    @Test
    @Transactional
    public void createFonctions() throws Exception {
        int databaseSizeBeforeCreate = fonctionsRepository.findAll().size();

        // Create the Fonctions
        FonctionsDTO fonctionsDTO = fonctionsMapper.toDto(fonctions);
        restFonctionsMockMvc.perform(post("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fonctionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Fonctions in the database
        List<Fonctions> fonctionsList = fonctionsRepository.findAll();
        assertThat(fonctionsList).hasSize(databaseSizeBeforeCreate + 1);
        Fonctions testFonctions = fonctionsList.get(fonctionsList.size() - 1);
        assertThat(testFonctions.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFonctions.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createFonctionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fonctionsRepository.findAll().size();

        // Create the Fonctions with an existing ID
        fonctions.setId(1L);
        FonctionsDTO fonctionsDTO = fonctionsMapper.toDto(fonctions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFonctionsMockMvc.perform(post("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fonctionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fonctions in the database
        List<Fonctions> fonctionsList = fonctionsRepository.findAll();
        assertThat(fonctionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFonctions() throws Exception {
        // Initialize the database
        fonctionsRepository.saveAndFlush(fonctions);

        // Get all the fonctionsList
        restFonctionsMockMvc.perform(get("/api/fonctions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fonctions.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getFonctions() throws Exception {
        // Initialize the database
        fonctionsRepository.saveAndFlush(fonctions);

        // Get the fonctions
        restFonctionsMockMvc.perform(get("/api/fonctions/{id}", fonctions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fonctions.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFonctions() throws Exception {
        // Get the fonctions
        restFonctionsMockMvc.perform(get("/api/fonctions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFonctions() throws Exception {
        // Initialize the database
        fonctionsRepository.saveAndFlush(fonctions);

        int databaseSizeBeforeUpdate = fonctionsRepository.findAll().size();

        // Update the fonctions
        Fonctions updatedFonctions = fonctionsRepository.findById(fonctions.getId()).get();
        // Disconnect from session so that the updates on updatedFonctions are not directly saved in db
        em.detach(updatedFonctions);
        updatedFonctions
            .nom(UPDATED_NOM)
            .type(UPDATED_TYPE);
        FonctionsDTO fonctionsDTO = fonctionsMapper.toDto(updatedFonctions);

        restFonctionsMockMvc.perform(put("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fonctionsDTO)))
            .andExpect(status().isOk());

        // Validate the Fonctions in the database
        List<Fonctions> fonctionsList = fonctionsRepository.findAll();
        assertThat(fonctionsList).hasSize(databaseSizeBeforeUpdate);
        Fonctions testFonctions = fonctionsList.get(fonctionsList.size() - 1);
        assertThat(testFonctions.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFonctions.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingFonctions() throws Exception {
        int databaseSizeBeforeUpdate = fonctionsRepository.findAll().size();

        // Create the Fonctions
        FonctionsDTO fonctionsDTO = fonctionsMapper.toDto(fonctions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFonctionsMockMvc.perform(put("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fonctionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fonctions in the database
        List<Fonctions> fonctionsList = fonctionsRepository.findAll();
        assertThat(fonctionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFonctions() throws Exception {
        // Initialize the database
        fonctionsRepository.saveAndFlush(fonctions);

        int databaseSizeBeforeDelete = fonctionsRepository.findAll().size();

        // Delete the fonctions
        restFonctionsMockMvc.perform(delete("/api/fonctions/{id}", fonctions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fonctions> fonctionsList = fonctionsRepository.findAll();
        assertThat(fonctionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fonctions.class);
        Fonctions fonctions1 = new Fonctions();
        fonctions1.setId(1L);
        Fonctions fonctions2 = new Fonctions();
        fonctions2.setId(fonctions1.getId());
        assertThat(fonctions1).isEqualTo(fonctions2);
        fonctions2.setId(2L);
        assertThat(fonctions1).isNotEqualTo(fonctions2);
        fonctions1.setId(null);
        assertThat(fonctions1).isNotEqualTo(fonctions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FonctionsDTO.class);
        FonctionsDTO fonctionsDTO1 = new FonctionsDTO();
        fonctionsDTO1.setId(1L);
        FonctionsDTO fonctionsDTO2 = new FonctionsDTO();
        assertThat(fonctionsDTO1).isNotEqualTo(fonctionsDTO2);
        fonctionsDTO2.setId(fonctionsDTO1.getId());
        assertThat(fonctionsDTO1).isEqualTo(fonctionsDTO2);
        fonctionsDTO2.setId(2L);
        assertThat(fonctionsDTO1).isNotEqualTo(fonctionsDTO2);
        fonctionsDTO1.setId(null);
        assertThat(fonctionsDTO1).isNotEqualTo(fonctionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fonctionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fonctionsMapper.fromId(null)).isNull();
    }
}

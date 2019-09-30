package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ConvertappApp;

import com.mycompany.myapp.domain.Proprietes;
import com.mycompany.myapp.repository.ProprietesRepository;
import com.mycompany.myapp.service.ProprietesService;
import com.mycompany.myapp.service.dto.ProprietesDTO;
import com.mycompany.myapp.service.mapper.ProprietesMapper;
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
 * Test class for the ProprietesResource REST controller.
 *
 * @see ProprietesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConvertappApp.class)
public class ProprietesResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private ProprietesRepository proprietesRepository;

    @Autowired
    private ProprietesMapper proprietesMapper;

    @Autowired
    private ProprietesService proprietesService;

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

    private MockMvc restProprietesMockMvc;

    private Proprietes proprietes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProprietesResource proprietesResource = new ProprietesResource(proprietesService);
        this.restProprietesMockMvc = MockMvcBuilders.standaloneSetup(proprietesResource)
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
    public static Proprietes createEntity(EntityManager em) {
        Proprietes proprietes = new Proprietes()
            .nom(DEFAULT_NOM)
            .type(DEFAULT_TYPE);
        return proprietes;
    }

    @Before
    public void initTest() {
        proprietes = createEntity(em);
    }

    @Test
    @Transactional
    public void createProprietes() throws Exception {
        int databaseSizeBeforeCreate = proprietesRepository.findAll().size();

        // Create the Proprietes
        ProprietesDTO proprietesDTO = proprietesMapper.toDto(proprietes);
        restProprietesMockMvc.perform(post("/api/proprietes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proprietesDTO)))
            .andExpect(status().isCreated());

        // Validate the Proprietes in the database
        List<Proprietes> proprietesList = proprietesRepository.findAll();
        assertThat(proprietesList).hasSize(databaseSizeBeforeCreate + 1);
        Proprietes testProprietes = proprietesList.get(proprietesList.size() - 1);
        assertThat(testProprietes.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProprietes.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createProprietesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proprietesRepository.findAll().size();

        // Create the Proprietes with an existing ID
        proprietes.setId(1L);
        ProprietesDTO proprietesDTO = proprietesMapper.toDto(proprietes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProprietesMockMvc.perform(post("/api/proprietes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proprietesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Proprietes in the database
        List<Proprietes> proprietesList = proprietesRepository.findAll();
        assertThat(proprietesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProprietes() throws Exception {
        // Initialize the database
        proprietesRepository.saveAndFlush(proprietes);

        // Get all the proprietesList
        restProprietesMockMvc.perform(get("/api/proprietes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proprietes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getProprietes() throws Exception {
        // Initialize the database
        proprietesRepository.saveAndFlush(proprietes);

        // Get the proprietes
        restProprietesMockMvc.perform(get("/api/proprietes/{id}", proprietes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proprietes.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProprietes() throws Exception {
        // Get the proprietes
        restProprietesMockMvc.perform(get("/api/proprietes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProprietes() throws Exception {
        // Initialize the database
        proprietesRepository.saveAndFlush(proprietes);

        int databaseSizeBeforeUpdate = proprietesRepository.findAll().size();

        // Update the proprietes
        Proprietes updatedProprietes = proprietesRepository.findById(proprietes.getId()).get();
        // Disconnect from session so that the updates on updatedProprietes are not directly saved in db
        em.detach(updatedProprietes);
        updatedProprietes
            .nom(UPDATED_NOM)
            .type(UPDATED_TYPE);
        ProprietesDTO proprietesDTO = proprietesMapper.toDto(updatedProprietes);

        restProprietesMockMvc.perform(put("/api/proprietes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proprietesDTO)))
            .andExpect(status().isOk());

        // Validate the Proprietes in the database
        List<Proprietes> proprietesList = proprietesRepository.findAll();
        assertThat(proprietesList).hasSize(databaseSizeBeforeUpdate);
        Proprietes testProprietes = proprietesList.get(proprietesList.size() - 1);
        assertThat(testProprietes.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProprietes.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProprietes() throws Exception {
        int databaseSizeBeforeUpdate = proprietesRepository.findAll().size();

        // Create the Proprietes
        ProprietesDTO proprietesDTO = proprietesMapper.toDto(proprietes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProprietesMockMvc.perform(put("/api/proprietes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proprietesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Proprietes in the database
        List<Proprietes> proprietesList = proprietesRepository.findAll();
        assertThat(proprietesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProprietes() throws Exception {
        // Initialize the database
        proprietesRepository.saveAndFlush(proprietes);

        int databaseSizeBeforeDelete = proprietesRepository.findAll().size();

        // Delete the proprietes
        restProprietesMockMvc.perform(delete("/api/proprietes/{id}", proprietes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Proprietes> proprietesList = proprietesRepository.findAll();
        assertThat(proprietesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proprietes.class);
        Proprietes proprietes1 = new Proprietes();
        proprietes1.setId(1L);
        Proprietes proprietes2 = new Proprietes();
        proprietes2.setId(proprietes1.getId());
        assertThat(proprietes1).isEqualTo(proprietes2);
        proprietes2.setId(2L);
        assertThat(proprietes1).isNotEqualTo(proprietes2);
        proprietes1.setId(null);
        assertThat(proprietes1).isNotEqualTo(proprietes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProprietesDTO.class);
        ProprietesDTO proprietesDTO1 = new ProprietesDTO();
        proprietesDTO1.setId(1L);
        ProprietesDTO proprietesDTO2 = new ProprietesDTO();
        assertThat(proprietesDTO1).isNotEqualTo(proprietesDTO2);
        proprietesDTO2.setId(proprietesDTO1.getId());
        assertThat(proprietesDTO1).isEqualTo(proprietesDTO2);
        proprietesDTO2.setId(2L);
        assertThat(proprietesDTO1).isNotEqualTo(proprietesDTO2);
        proprietesDTO1.setId(null);
        assertThat(proprietesDTO1).isNotEqualTo(proprietesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(proprietesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(proprietesMapper.fromId(null)).isNull();
    }
}

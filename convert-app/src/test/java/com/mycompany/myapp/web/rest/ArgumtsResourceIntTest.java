package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ConvertappApp;

import com.mycompany.myapp.domain.Argumts;
import com.mycompany.myapp.repository.ArgumtsRepository;
import com.mycompany.myapp.service.ArgumtsService;
import com.mycompany.myapp.service.dto.ArgumtsDTO;
import com.mycompany.myapp.service.mapper.ArgumtsMapper;
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
 * Test class for the ArgumtsResource REST controller.
 *
 * @see ArgumtsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConvertappApp.class)
public class ArgumtsResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private ArgumtsRepository argumtsRepository;

    @Autowired
    private ArgumtsMapper argumtsMapper;

    @Autowired
    private ArgumtsService argumtsService;

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

    private MockMvc restArgumtsMockMvc;

    private Argumts argumts;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArgumtsResource argumtsResource = new ArgumtsResource(argumtsService);
        this.restArgumtsMockMvc = MockMvcBuilders.standaloneSetup(argumtsResource)
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
    public static Argumts createEntity(EntityManager em) {
        Argumts argumts = new Argumts()
            .nom(DEFAULT_NOM)
            .type(DEFAULT_TYPE);
        return argumts;
    }

    @Before
    public void initTest() {
        argumts = createEntity(em);
    }

    @Test
    @Transactional
    public void createArgumts() throws Exception {
        int databaseSizeBeforeCreate = argumtsRepository.findAll().size();

        // Create the Argumts
        ArgumtsDTO argumtsDTO = argumtsMapper.toDto(argumts);
        restArgumtsMockMvc.perform(post("/api/argumts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(argumtsDTO)))
            .andExpect(status().isCreated());

        // Validate the Argumts in the database
        List<Argumts> argumtsList = argumtsRepository.findAll();
        assertThat(argumtsList).hasSize(databaseSizeBeforeCreate + 1);
        Argumts testArgumts = argumtsList.get(argumtsList.size() - 1);
        assertThat(testArgumts.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testArgumts.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createArgumtsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = argumtsRepository.findAll().size();

        // Create the Argumts with an existing ID
        argumts.setId(1L);
        ArgumtsDTO argumtsDTO = argumtsMapper.toDto(argumts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArgumtsMockMvc.perform(post("/api/argumts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(argumtsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Argumts in the database
        List<Argumts> argumtsList = argumtsRepository.findAll();
        assertThat(argumtsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArgumts() throws Exception {
        // Initialize the database
        argumtsRepository.saveAndFlush(argumts);

        // Get all the argumtsList
        restArgumtsMockMvc.perform(get("/api/argumts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(argumts.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getArgumts() throws Exception {
        // Initialize the database
        argumtsRepository.saveAndFlush(argumts);

        // Get the argumts
        restArgumtsMockMvc.perform(get("/api/argumts/{id}", argumts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(argumts.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArgumts() throws Exception {
        // Get the argumts
        restArgumtsMockMvc.perform(get("/api/argumts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArgumts() throws Exception {
        // Initialize the database
        argumtsRepository.saveAndFlush(argumts);

        int databaseSizeBeforeUpdate = argumtsRepository.findAll().size();

        // Update the argumts
        Argumts updatedArgumts = argumtsRepository.findById(argumts.getId()).get();
        // Disconnect from session so that the updates on updatedArgumts are not directly saved in db
        em.detach(updatedArgumts);
        updatedArgumts
            .nom(UPDATED_NOM)
            .type(UPDATED_TYPE);
        ArgumtsDTO argumtsDTO = argumtsMapper.toDto(updatedArgumts);

        restArgumtsMockMvc.perform(put("/api/argumts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(argumtsDTO)))
            .andExpect(status().isOk());

        // Validate the Argumts in the database
        List<Argumts> argumtsList = argumtsRepository.findAll();
        assertThat(argumtsList).hasSize(databaseSizeBeforeUpdate);
        Argumts testArgumts = argumtsList.get(argumtsList.size() - 1);
        assertThat(testArgumts.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testArgumts.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingArgumts() throws Exception {
        int databaseSizeBeforeUpdate = argumtsRepository.findAll().size();

        // Create the Argumts
        ArgumtsDTO argumtsDTO = argumtsMapper.toDto(argumts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArgumtsMockMvc.perform(put("/api/argumts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(argumtsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Argumts in the database
        List<Argumts> argumtsList = argumtsRepository.findAll();
        assertThat(argumtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArgumts() throws Exception {
        // Initialize the database
        argumtsRepository.saveAndFlush(argumts);

        int databaseSizeBeforeDelete = argumtsRepository.findAll().size();

        // Delete the argumts
        restArgumtsMockMvc.perform(delete("/api/argumts/{id}", argumts.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Argumts> argumtsList = argumtsRepository.findAll();
        assertThat(argumtsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Argumts.class);
        Argumts argumts1 = new Argumts();
        argumts1.setId(1L);
        Argumts argumts2 = new Argumts();
        argumts2.setId(argumts1.getId());
        assertThat(argumts1).isEqualTo(argumts2);
        argumts2.setId(2L);
        assertThat(argumts1).isNotEqualTo(argumts2);
        argumts1.setId(null);
        assertThat(argumts1).isNotEqualTo(argumts2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArgumtsDTO.class);
        ArgumtsDTO argumtsDTO1 = new ArgumtsDTO();
        argumtsDTO1.setId(1L);
        ArgumtsDTO argumtsDTO2 = new ArgumtsDTO();
        assertThat(argumtsDTO1).isNotEqualTo(argumtsDTO2);
        argumtsDTO2.setId(argumtsDTO1.getId());
        assertThat(argumtsDTO1).isEqualTo(argumtsDTO2);
        argumtsDTO2.setId(2L);
        assertThat(argumtsDTO1).isNotEqualTo(argumtsDTO2);
        argumtsDTO1.setId(null);
        assertThat(argumtsDTO1).isNotEqualTo(argumtsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(argumtsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(argumtsMapper.fromId(null)).isNull();
    }
}

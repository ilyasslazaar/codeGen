package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ConvertappApp;

import com.mycompany.myapp.domain.BuilderPlate;
import com.mycompany.myapp.repository.BuilderPlateRepository;
import com.mycompany.myapp.service.BuilderPlateService;
import com.mycompany.myapp.service.dto.BuilderPlateDTO;
import com.mycompany.myapp.service.mapper.BuilderPlateMapper;
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
 * Test class for the BuilderPlateResource REST controller.
 *
 * @see BuilderPlateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConvertappApp.class)
public class BuilderPlateResourceIntTest {

    private static final String DEFAULT_DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_CODE = "BBBBBBBBBB";

    @Autowired
    private BuilderPlateRepository builderPlateRepository;

    @Autowired
    private BuilderPlateMapper builderPlateMapper;

    @Autowired
    private BuilderPlateService builderPlateService;

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

    private MockMvc restBuilderPlateMockMvc;

    private BuilderPlate builderPlate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BuilderPlateResource builderPlateResource = new BuilderPlateResource(builderPlateService);
        this.restBuilderPlateMockMvc = MockMvcBuilders.standaloneSetup(builderPlateResource)
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
    public static BuilderPlate createEntity(EntityManager em) {
        BuilderPlate builderPlate = new BuilderPlate()
            .defaultCode(DEFAULT_DEFAULT_CODE);
        return builderPlate;
    }

    @Before
    public void initTest() {
        builderPlate = createEntity(em);
    }

    @Test
    @Transactional
    public void createBuilderPlate() throws Exception {
        int databaseSizeBeforeCreate = builderPlateRepository.findAll().size();

        // Create the BuilderPlate
        BuilderPlateDTO builderPlateDTO = builderPlateMapper.toDto(builderPlate);
        restBuilderPlateMockMvc.perform(post("/api/builder-plates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(builderPlateDTO)))
            .andExpect(status().isCreated());

        // Validate the BuilderPlate in the database
        List<BuilderPlate> builderPlateList = builderPlateRepository.findAll();
        assertThat(builderPlateList).hasSize(databaseSizeBeforeCreate + 1);
        BuilderPlate testBuilderPlate = builderPlateList.get(builderPlateList.size() - 1);
        assertThat(testBuilderPlate.getDefaultCode()).isEqualTo(DEFAULT_DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createBuilderPlateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = builderPlateRepository.findAll().size();

        // Create the BuilderPlate with an existing ID
        builderPlate.setId(1L);
        BuilderPlateDTO builderPlateDTO = builderPlateMapper.toDto(builderPlate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuilderPlateMockMvc.perform(post("/api/builder-plates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(builderPlateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BuilderPlate in the database
        List<BuilderPlate> builderPlateList = builderPlateRepository.findAll();
        assertThat(builderPlateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBuilderPlates() throws Exception {
        // Initialize the database
        builderPlateRepository.saveAndFlush(builderPlate);

        // Get all the builderPlateList
        restBuilderPlateMockMvc.perform(get("/api/builder-plates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(builderPlate.getId().intValue())))
            .andExpect(jsonPath("$.[*].defaultCode").value(hasItem(DEFAULT_DEFAULT_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getBuilderPlate() throws Exception {
        // Initialize the database
        builderPlateRepository.saveAndFlush(builderPlate);

        // Get the builderPlate
        restBuilderPlateMockMvc.perform(get("/api/builder-plates/{id}", builderPlate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(builderPlate.getId().intValue()))
            .andExpect(jsonPath("$.defaultCode").value(DEFAULT_DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBuilderPlate() throws Exception {
        // Get the builderPlate
        restBuilderPlateMockMvc.perform(get("/api/builder-plates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuilderPlate() throws Exception {
        // Initialize the database
        builderPlateRepository.saveAndFlush(builderPlate);

        int databaseSizeBeforeUpdate = builderPlateRepository.findAll().size();

        // Update the builderPlate
        BuilderPlate updatedBuilderPlate = builderPlateRepository.findById(builderPlate.getId()).get();
        // Disconnect from session so that the updates on updatedBuilderPlate are not directly saved in db
        em.detach(updatedBuilderPlate);
        updatedBuilderPlate
            .defaultCode(UPDATED_DEFAULT_CODE);
        BuilderPlateDTO builderPlateDTO = builderPlateMapper.toDto(updatedBuilderPlate);

        restBuilderPlateMockMvc.perform(put("/api/builder-plates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(builderPlateDTO)))
            .andExpect(status().isOk());

        // Validate the BuilderPlate in the database
        List<BuilderPlate> builderPlateList = builderPlateRepository.findAll();
        assertThat(builderPlateList).hasSize(databaseSizeBeforeUpdate);
        BuilderPlate testBuilderPlate = builderPlateList.get(builderPlateList.size() - 1);
        assertThat(testBuilderPlate.getDefaultCode()).isEqualTo(UPDATED_DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingBuilderPlate() throws Exception {
        int databaseSizeBeforeUpdate = builderPlateRepository.findAll().size();

        // Create the BuilderPlate
        BuilderPlateDTO builderPlateDTO = builderPlateMapper.toDto(builderPlate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuilderPlateMockMvc.perform(put("/api/builder-plates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(builderPlateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BuilderPlate in the database
        List<BuilderPlate> builderPlateList = builderPlateRepository.findAll();
        assertThat(builderPlateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBuilderPlate() throws Exception {
        // Initialize the database
        builderPlateRepository.saveAndFlush(builderPlate);

        int databaseSizeBeforeDelete = builderPlateRepository.findAll().size();

        // Delete the builderPlate
        restBuilderPlateMockMvc.perform(delete("/api/builder-plates/{id}", builderPlate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BuilderPlate> builderPlateList = builderPlateRepository.findAll();
        assertThat(builderPlateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuilderPlate.class);
        BuilderPlate builderPlate1 = new BuilderPlate();
        builderPlate1.setId(1L);
        BuilderPlate builderPlate2 = new BuilderPlate();
        builderPlate2.setId(builderPlate1.getId());
        assertThat(builderPlate1).isEqualTo(builderPlate2);
        builderPlate2.setId(2L);
        assertThat(builderPlate1).isNotEqualTo(builderPlate2);
        builderPlate1.setId(null);
        assertThat(builderPlate1).isNotEqualTo(builderPlate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuilderPlateDTO.class);
        BuilderPlateDTO builderPlateDTO1 = new BuilderPlateDTO();
        builderPlateDTO1.setId(1L);
        BuilderPlateDTO builderPlateDTO2 = new BuilderPlateDTO();
        assertThat(builderPlateDTO1).isNotEqualTo(builderPlateDTO2);
        builderPlateDTO2.setId(builderPlateDTO1.getId());
        assertThat(builderPlateDTO1).isEqualTo(builderPlateDTO2);
        builderPlateDTO2.setId(2L);
        assertThat(builderPlateDTO1).isNotEqualTo(builderPlateDTO2);
        builderPlateDTO1.setId(null);
        assertThat(builderPlateDTO1).isNotEqualTo(builderPlateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(builderPlateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(builderPlateMapper.fromId(null)).isNull();
    }
}

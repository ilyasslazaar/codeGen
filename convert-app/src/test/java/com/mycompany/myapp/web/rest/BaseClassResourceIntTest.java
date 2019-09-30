package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ConvertappApp;

import com.mycompany.myapp.domain.BaseClass;
import com.mycompany.myapp.repository.BaseClassRepository;
import com.mycompany.myapp.service.BaseClassService;
import com.mycompany.myapp.service.dto.BaseClassDTO;
import com.mycompany.myapp.service.mapper.BaseClassMapper;
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
 * Test class for the BaseClassResource REST controller.
 *
 * @see BaseClassResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConvertappApp.class)
public class BaseClassResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_IMPORTS = "AAAAAAAAAA";
    private static final String UPDATED_IMPORTS = "BBBBBBBBBB";

    @Autowired
    private BaseClassRepository baseClassRepository;

    @Autowired
    private BaseClassMapper baseClassMapper;

    @Autowired
    private BaseClassService baseClassService;

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

    private MockMvc restBaseClassMockMvc;

    private BaseClass baseClass;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BaseClassResource baseClassResource = new BaseClassResource(baseClassService);
        this.restBaseClassMockMvc = MockMvcBuilders.standaloneSetup(baseClassResource)
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
    public static BaseClass createEntity(EntityManager em) {
        BaseClass baseClass = new BaseClass()
            .nom(DEFAULT_NOM)
            .imports(DEFAULT_IMPORTS);
        return baseClass;
    }

    @Before
    public void initTest() {
        baseClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createBaseClass() throws Exception {
        int databaseSizeBeforeCreate = baseClassRepository.findAll().size();

        // Create the BaseClass
        BaseClassDTO baseClassDTO = baseClassMapper.toDto(baseClass);
        restBaseClassMockMvc.perform(post("/api/base-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseClassDTO)))
            .andExpect(status().isCreated());

        // Validate the BaseClass in the database
        List<BaseClass> baseClassList = baseClassRepository.findAll();
        assertThat(baseClassList).hasSize(databaseSizeBeforeCreate + 1);
        BaseClass testBaseClass = baseClassList.get(baseClassList.size() - 1);
        assertThat(testBaseClass.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testBaseClass.getImports()).isEqualTo(DEFAULT_IMPORTS);
    }

    @Test
    @Transactional
    public void createBaseClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = baseClassRepository.findAll().size();

        // Create the BaseClass with an existing ID
        baseClass.setId(1L);
        BaseClassDTO baseClassDTO = baseClassMapper.toDto(baseClass);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBaseClassMockMvc.perform(post("/api/base-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BaseClass in the database
        List<BaseClass> baseClassList = baseClassRepository.findAll();
        assertThat(baseClassList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBaseClasses() throws Exception {
        // Initialize the database
        baseClassRepository.saveAndFlush(baseClass);

        // Get all the baseClassList
        restBaseClassMockMvc.perform(get("/api/base-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(baseClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].imports").value(hasItem(DEFAULT_IMPORTS.toString())));
    }
    
    @Test
    @Transactional
    public void getBaseClass() throws Exception {
        // Initialize the database
        baseClassRepository.saveAndFlush(baseClass);

        // Get the baseClass
        restBaseClassMockMvc.perform(get("/api/base-classes/{id}", baseClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(baseClass.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.imports").value(DEFAULT_IMPORTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBaseClass() throws Exception {
        // Get the baseClass
        restBaseClassMockMvc.perform(get("/api/base-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBaseClass() throws Exception {
        // Initialize the database
        baseClassRepository.saveAndFlush(baseClass);

        int databaseSizeBeforeUpdate = baseClassRepository.findAll().size();

        // Update the baseClass
        BaseClass updatedBaseClass = baseClassRepository.findById(baseClass.getId()).get();
        // Disconnect from session so that the updates on updatedBaseClass are not directly saved in db
        em.detach(updatedBaseClass);
        updatedBaseClass
            .nom(UPDATED_NOM)
            .imports(UPDATED_IMPORTS);
        BaseClassDTO baseClassDTO = baseClassMapper.toDto(updatedBaseClass);

        restBaseClassMockMvc.perform(put("/api/base-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseClassDTO)))
            .andExpect(status().isOk());

        // Validate the BaseClass in the database
        List<BaseClass> baseClassList = baseClassRepository.findAll();
        assertThat(baseClassList).hasSize(databaseSizeBeforeUpdate);
        BaseClass testBaseClass = baseClassList.get(baseClassList.size() - 1);
        assertThat(testBaseClass.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testBaseClass.getImports()).isEqualTo(UPDATED_IMPORTS);
    }

    @Test
    @Transactional
    public void updateNonExistingBaseClass() throws Exception {
        int databaseSizeBeforeUpdate = baseClassRepository.findAll().size();

        // Create the BaseClass
        BaseClassDTO baseClassDTO = baseClassMapper.toDto(baseClass);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBaseClassMockMvc.perform(put("/api/base-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BaseClass in the database
        List<BaseClass> baseClassList = baseClassRepository.findAll();
        assertThat(baseClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBaseClass() throws Exception {
        // Initialize the database
        baseClassRepository.saveAndFlush(baseClass);

        int databaseSizeBeforeDelete = baseClassRepository.findAll().size();

        // Delete the baseClass
        restBaseClassMockMvc.perform(delete("/api/base-classes/{id}", baseClass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BaseClass> baseClassList = baseClassRepository.findAll();
        assertThat(baseClassList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseClass.class);
        BaseClass baseClass1 = new BaseClass();
        baseClass1.setId(1L);
        BaseClass baseClass2 = new BaseClass();
        baseClass2.setId(baseClass1.getId());
        assertThat(baseClass1).isEqualTo(baseClass2);
        baseClass2.setId(2L);
        assertThat(baseClass1).isNotEqualTo(baseClass2);
        baseClass1.setId(null);
        assertThat(baseClass1).isNotEqualTo(baseClass2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseClassDTO.class);
        BaseClassDTO baseClassDTO1 = new BaseClassDTO();
        baseClassDTO1.setId(1L);
        BaseClassDTO baseClassDTO2 = new BaseClassDTO();
        assertThat(baseClassDTO1).isNotEqualTo(baseClassDTO2);
        baseClassDTO2.setId(baseClassDTO1.getId());
        assertThat(baseClassDTO1).isEqualTo(baseClassDTO2);
        baseClassDTO2.setId(2L);
        assertThat(baseClassDTO1).isNotEqualTo(baseClassDTO2);
        baseClassDTO1.setId(null);
        assertThat(baseClassDTO1).isNotEqualTo(baseClassDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(baseClassMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(baseClassMapper.fromId(null)).isNull();
    }
}

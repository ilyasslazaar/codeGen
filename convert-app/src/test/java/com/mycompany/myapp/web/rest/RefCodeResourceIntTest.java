package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ConvertappApp;

import com.mycompany.myapp.domain.RefCode;
import com.mycompany.myapp.repository.RefCodeRepository;
import com.mycompany.myapp.service.RefCodeService;
import com.mycompany.myapp.service.dto.RefCodeDTO;
import com.mycompany.myapp.service.mapper.RefCodeMapper;
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
 * Test class for the RefCodeResource REST controller.
 *
 * @see RefCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConvertappApp.class)
public class RefCodeResourceIntTest {

    private static final String DEFAULT_LANGAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGAGE = "BBBBBBBBBB";

    @Autowired
    private RefCodeRepository refCodeRepository;

    @Autowired
    private RefCodeMapper refCodeMapper;

    @Autowired
    private RefCodeService refCodeService;

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

    private MockMvc restRefCodeMockMvc;

    private RefCode refCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RefCodeResource refCodeResource = new RefCodeResource(refCodeService);
        this.restRefCodeMockMvc = MockMvcBuilders.standaloneSetup(refCodeResource)
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
    public static RefCode createEntity(EntityManager em) {
        RefCode refCode = new RefCode()
            .langage(DEFAULT_LANGAGE);
        return refCode;
    }

    @Before
    public void initTest() {
        refCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefCode() throws Exception {
        int databaseSizeBeforeCreate = refCodeRepository.findAll().size();

        // Create the RefCode
        RefCodeDTO refCodeDTO = refCodeMapper.toDto(refCode);
        restRefCodeMockMvc.perform(post("/api/ref-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refCodeDTO)))
            .andExpect(status().isCreated());

        // Validate the RefCode in the database
        List<RefCode> refCodeList = refCodeRepository.findAll();
        assertThat(refCodeList).hasSize(databaseSizeBeforeCreate + 1);
        RefCode testRefCode = refCodeList.get(refCodeList.size() - 1);
        assertThat(testRefCode.getLangage()).isEqualTo(DEFAULT_LANGAGE);
    }

    @Test
    @Transactional
    public void createRefCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refCodeRepository.findAll().size();

        // Create the RefCode with an existing ID
        refCode.setId(1L);
        RefCodeDTO refCodeDTO = refCodeMapper.toDto(refCode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefCodeMockMvc.perform(post("/api/ref-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refCodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefCode in the database
        List<RefCode> refCodeList = refCodeRepository.findAll();
        assertThat(refCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRefCodes() throws Exception {
        // Initialize the database
        refCodeRepository.saveAndFlush(refCode);

        // Get all the refCodeList
        restRefCodeMockMvc.perform(get("/api/ref-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].langage").value(hasItem(DEFAULT_LANGAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getRefCode() throws Exception {
        // Initialize the database
        refCodeRepository.saveAndFlush(refCode);

        // Get the refCode
        restRefCodeMockMvc.perform(get("/api/ref-codes/{id}", refCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refCode.getId().intValue()))
            .andExpect(jsonPath("$.langage").value(DEFAULT_LANGAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefCode() throws Exception {
        // Get the refCode
        restRefCodeMockMvc.perform(get("/api/ref-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefCode() throws Exception {
        // Initialize the database
        refCodeRepository.saveAndFlush(refCode);

        int databaseSizeBeforeUpdate = refCodeRepository.findAll().size();

        // Update the refCode
        RefCode updatedRefCode = refCodeRepository.findById(refCode.getId()).get();
        // Disconnect from session so that the updates on updatedRefCode are not directly saved in db
        em.detach(updatedRefCode);
        updatedRefCode
            .langage(UPDATED_LANGAGE);
        RefCodeDTO refCodeDTO = refCodeMapper.toDto(updatedRefCode);

        restRefCodeMockMvc.perform(put("/api/ref-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refCodeDTO)))
            .andExpect(status().isOk());

        // Validate the RefCode in the database
        List<RefCode> refCodeList = refCodeRepository.findAll();
        assertThat(refCodeList).hasSize(databaseSizeBeforeUpdate);
        RefCode testRefCode = refCodeList.get(refCodeList.size() - 1);
        assertThat(testRefCode.getLangage()).isEqualTo(UPDATED_LANGAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingRefCode() throws Exception {
        int databaseSizeBeforeUpdate = refCodeRepository.findAll().size();

        // Create the RefCode
        RefCodeDTO refCodeDTO = refCodeMapper.toDto(refCode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefCodeMockMvc.perform(put("/api/ref-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refCodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RefCode in the database
        List<RefCode> refCodeList = refCodeRepository.findAll();
        assertThat(refCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRefCode() throws Exception {
        // Initialize the database
        refCodeRepository.saveAndFlush(refCode);

        int databaseSizeBeforeDelete = refCodeRepository.findAll().size();

        // Delete the refCode
        restRefCodeMockMvc.perform(delete("/api/ref-codes/{id}", refCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RefCode> refCodeList = refCodeRepository.findAll();
        assertThat(refCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefCode.class);
        RefCode refCode1 = new RefCode();
        refCode1.setId(1L);
        RefCode refCode2 = new RefCode();
        refCode2.setId(refCode1.getId());
        assertThat(refCode1).isEqualTo(refCode2);
        refCode2.setId(2L);
        assertThat(refCode1).isNotEqualTo(refCode2);
        refCode1.setId(null);
        assertThat(refCode1).isNotEqualTo(refCode2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefCodeDTO.class);
        RefCodeDTO refCodeDTO1 = new RefCodeDTO();
        refCodeDTO1.setId(1L);
        RefCodeDTO refCodeDTO2 = new RefCodeDTO();
        assertThat(refCodeDTO1).isNotEqualTo(refCodeDTO2);
        refCodeDTO2.setId(refCodeDTO1.getId());
        assertThat(refCodeDTO1).isEqualTo(refCodeDTO2);
        refCodeDTO2.setId(2L);
        assertThat(refCodeDTO1).isNotEqualTo(refCodeDTO2);
        refCodeDTO1.setId(null);
        assertThat(refCodeDTO1).isNotEqualTo(refCodeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refCodeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refCodeMapper.fromId(null)).isNull();
    }
}

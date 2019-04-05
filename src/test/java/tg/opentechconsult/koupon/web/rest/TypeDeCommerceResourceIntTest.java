package tg.opentechconsult.koupon.web.rest;

import tg.opentechconsult.koupon.KouponApp;

import tg.opentechconsult.koupon.domain.TypeDeCommerce;
import tg.opentechconsult.koupon.repository.TypeDeCommerceRepository;
import tg.opentechconsult.koupon.repository.search.TypeDeCommerceSearchRepository;
import tg.opentechconsult.koupon.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static tg.opentechconsult.koupon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TypeDeCommerceResource REST controller.
 *
 * @see TypeDeCommerceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouponApp.class)
public class TypeDeCommerceResourceIntTest {

    private static final String DEFAULT_NOM_TYPE_DE_COMMERCE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_TYPE_DE_COMMERCE = "BBBBBBBBBB";

    @Autowired
    private TypeDeCommerceRepository typeDeCommerceRepository;

    /**
     * This repository is mocked in the tg.opentechconsult.koupon.repository.search test package.
     *
     * @see tg.opentechconsult.koupon.repository.search.TypeDeCommerceSearchRepositoryMockConfiguration
     */
    @Autowired
    private TypeDeCommerceSearchRepository mockTypeDeCommerceSearchRepository;

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

    private MockMvc restTypeDeCommerceMockMvc;

    private TypeDeCommerce typeDeCommerce;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeDeCommerceResource typeDeCommerceResource = new TypeDeCommerceResource(typeDeCommerceRepository, mockTypeDeCommerceSearchRepository);
        this.restTypeDeCommerceMockMvc = MockMvcBuilders.standaloneSetup(typeDeCommerceResource)
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
    public static TypeDeCommerce createEntity(EntityManager em) {
        TypeDeCommerce typeDeCommerce = new TypeDeCommerce()
            .nomTypeDeCommerce(DEFAULT_NOM_TYPE_DE_COMMERCE);
        return typeDeCommerce;
    }

    @Before
    public void initTest() {
        typeDeCommerce = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeDeCommerce() throws Exception {
        int databaseSizeBeforeCreate = typeDeCommerceRepository.findAll().size();

        // Create the TypeDeCommerce
        restTypeDeCommerceMockMvc.perform(post("/api/type-de-commerces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDeCommerce)))
            .andExpect(status().isCreated());

        // Validate the TypeDeCommerce in the database
        List<TypeDeCommerce> typeDeCommerceList = typeDeCommerceRepository.findAll();
        assertThat(typeDeCommerceList).hasSize(databaseSizeBeforeCreate + 1);
        TypeDeCommerce testTypeDeCommerce = typeDeCommerceList.get(typeDeCommerceList.size() - 1);
        assertThat(testTypeDeCommerce.getNomTypeDeCommerce()).isEqualTo(DEFAULT_NOM_TYPE_DE_COMMERCE);

        // Validate the TypeDeCommerce in Elasticsearch
        verify(mockTypeDeCommerceSearchRepository, times(1)).save(testTypeDeCommerce);
    }

    @Test
    @Transactional
    public void createTypeDeCommerceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeDeCommerceRepository.findAll().size();

        // Create the TypeDeCommerce with an existing ID
        typeDeCommerce.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeDeCommerceMockMvc.perform(post("/api/type-de-commerces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDeCommerce)))
            .andExpect(status().isBadRequest());

        // Validate the TypeDeCommerce in the database
        List<TypeDeCommerce> typeDeCommerceList = typeDeCommerceRepository.findAll();
        assertThat(typeDeCommerceList).hasSize(databaseSizeBeforeCreate);

        // Validate the TypeDeCommerce in Elasticsearch
        verify(mockTypeDeCommerceSearchRepository, times(0)).save(typeDeCommerce);
    }

    @Test
    @Transactional
    public void checkNomTypeDeCommerceIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeDeCommerceRepository.findAll().size();
        // set the field null
        typeDeCommerce.setNomTypeDeCommerce(null);

        // Create the TypeDeCommerce, which fails.

        restTypeDeCommerceMockMvc.perform(post("/api/type-de-commerces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDeCommerce)))
            .andExpect(status().isBadRequest());

        List<TypeDeCommerce> typeDeCommerceList = typeDeCommerceRepository.findAll();
        assertThat(typeDeCommerceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeDeCommerces() throws Exception {
        // Initialize the database
        typeDeCommerceRepository.saveAndFlush(typeDeCommerce);

        // Get all the typeDeCommerceList
        restTypeDeCommerceMockMvc.perform(get("/api/type-de-commerces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeDeCommerce.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomTypeDeCommerce").value(hasItem(DEFAULT_NOM_TYPE_DE_COMMERCE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeDeCommerce() throws Exception {
        // Initialize the database
        typeDeCommerceRepository.saveAndFlush(typeDeCommerce);

        // Get the typeDeCommerce
        restTypeDeCommerceMockMvc.perform(get("/api/type-de-commerces/{id}", typeDeCommerce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeDeCommerce.getId().intValue()))
            .andExpect(jsonPath("$.nomTypeDeCommerce").value(DEFAULT_NOM_TYPE_DE_COMMERCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeDeCommerce() throws Exception {
        // Get the typeDeCommerce
        restTypeDeCommerceMockMvc.perform(get("/api/type-de-commerces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeDeCommerce() throws Exception {
        // Initialize the database
        typeDeCommerceRepository.saveAndFlush(typeDeCommerce);

        int databaseSizeBeforeUpdate = typeDeCommerceRepository.findAll().size();

        // Update the typeDeCommerce
        TypeDeCommerce updatedTypeDeCommerce = typeDeCommerceRepository.findById(typeDeCommerce.getId()).get();
        // Disconnect from session so that the updates on updatedTypeDeCommerce are not directly saved in db
        em.detach(updatedTypeDeCommerce);
        updatedTypeDeCommerce
            .nomTypeDeCommerce(UPDATED_NOM_TYPE_DE_COMMERCE);

        restTypeDeCommerceMockMvc.perform(put("/api/type-de-commerces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeDeCommerce)))
            .andExpect(status().isOk());

        // Validate the TypeDeCommerce in the database
        List<TypeDeCommerce> typeDeCommerceList = typeDeCommerceRepository.findAll();
        assertThat(typeDeCommerceList).hasSize(databaseSizeBeforeUpdate);
        TypeDeCommerce testTypeDeCommerce = typeDeCommerceList.get(typeDeCommerceList.size() - 1);
        assertThat(testTypeDeCommerce.getNomTypeDeCommerce()).isEqualTo(UPDATED_NOM_TYPE_DE_COMMERCE);

        // Validate the TypeDeCommerce in Elasticsearch
        verify(mockTypeDeCommerceSearchRepository, times(1)).save(testTypeDeCommerce);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeDeCommerce() throws Exception {
        int databaseSizeBeforeUpdate = typeDeCommerceRepository.findAll().size();

        // Create the TypeDeCommerce

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeDeCommerceMockMvc.perform(put("/api/type-de-commerces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDeCommerce)))
            .andExpect(status().isBadRequest());

        // Validate the TypeDeCommerce in the database
        List<TypeDeCommerce> typeDeCommerceList = typeDeCommerceRepository.findAll();
        assertThat(typeDeCommerceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TypeDeCommerce in Elasticsearch
        verify(mockTypeDeCommerceSearchRepository, times(0)).save(typeDeCommerce);
    }

    @Test
    @Transactional
    public void deleteTypeDeCommerce() throws Exception {
        // Initialize the database
        typeDeCommerceRepository.saveAndFlush(typeDeCommerce);

        int databaseSizeBeforeDelete = typeDeCommerceRepository.findAll().size();

        // Delete the typeDeCommerce
        restTypeDeCommerceMockMvc.perform(delete("/api/type-de-commerces/{id}", typeDeCommerce.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeDeCommerce> typeDeCommerceList = typeDeCommerceRepository.findAll();
        assertThat(typeDeCommerceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TypeDeCommerce in Elasticsearch
        verify(mockTypeDeCommerceSearchRepository, times(1)).deleteById(typeDeCommerce.getId());
    }

    @Test
    @Transactional
    public void searchTypeDeCommerce() throws Exception {
        // Initialize the database
        typeDeCommerceRepository.saveAndFlush(typeDeCommerce);
        when(mockTypeDeCommerceSearchRepository.search(queryStringQuery("id:" + typeDeCommerce.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(typeDeCommerce), PageRequest.of(0, 1), 1));
        // Search the typeDeCommerce
        restTypeDeCommerceMockMvc.perform(get("/api/_search/type-de-commerces?query=id:" + typeDeCommerce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeDeCommerce.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomTypeDeCommerce").value(hasItem(DEFAULT_NOM_TYPE_DE_COMMERCE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeDeCommerce.class);
        TypeDeCommerce typeDeCommerce1 = new TypeDeCommerce();
        typeDeCommerce1.setId(1L);
        TypeDeCommerce typeDeCommerce2 = new TypeDeCommerce();
        typeDeCommerce2.setId(typeDeCommerce1.getId());
        assertThat(typeDeCommerce1).isEqualTo(typeDeCommerce2);
        typeDeCommerce2.setId(2L);
        assertThat(typeDeCommerce1).isNotEqualTo(typeDeCommerce2);
        typeDeCommerce1.setId(null);
        assertThat(typeDeCommerce1).isNotEqualTo(typeDeCommerce2);
    }
}

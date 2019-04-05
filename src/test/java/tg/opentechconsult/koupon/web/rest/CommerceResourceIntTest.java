package tg.opentechconsult.koupon.web.rest;

import tg.opentechconsult.koupon.KouponApp;

import tg.opentechconsult.koupon.domain.Commerce;
import tg.opentechconsult.koupon.repository.CommerceRepository;
import tg.opentechconsult.koupon.repository.search.CommerceSearchRepository;
import tg.opentechconsult.koupon.service.CommerceService;
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
 * Test class for the CommerceResource REST controller.
 *
 * @see CommerceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouponApp.class)
public class CommerceResourceIntTest {

    private static final String DEFAULT_NOM_COMMERCE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COMMERCE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_RUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_RUE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTALE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTALE = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_WEB = "AAAAAAAAAA";
    private static final String UPDATED_SITE_WEB = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_COMMERCE = "AAAAAAAAAA";
    private static final String UPDATED_DESC_COMMERCE = "BBBBBBBBBB";

    @Autowired
    private CommerceRepository commerceRepository;

    @Autowired
    private CommerceService commerceService;

    /**
     * This repository is mocked in the tg.opentechconsult.koupon.repository.search test package.
     *
     * @see tg.opentechconsult.koupon.repository.search.CommerceSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommerceSearchRepository mockCommerceSearchRepository;

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

    private MockMvc restCommerceMockMvc;

    private Commerce commerce;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommerceResource commerceResource = new CommerceResource(commerceService);
        this.restCommerceMockMvc = MockMvcBuilders.standaloneSetup(commerceResource)
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
    public static Commerce createEntity(EntityManager em) {
        Commerce commerce = new Commerce()
            .nomCommerce(DEFAULT_NOM_COMMERCE)
            .nomRue(DEFAULT_NOM_RUE)
            .codePostale(DEFAULT_CODE_POSTALE)
            .siteWeb(DEFAULT_SITE_WEB)
            .descCommerce(DEFAULT_DESC_COMMERCE);
        return commerce;
    }

    @Before
    public void initTest() {
        commerce = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerce() throws Exception {
        int databaseSizeBeforeCreate = commerceRepository.findAll().size();

        // Create the Commerce
        restCommerceMockMvc.perform(post("/api/commerce")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerce)))
            .andExpect(status().isCreated());

        // Validate the Commerce in the database
        List<Commerce> commerceList = commerceRepository.findAll();
        assertThat(commerceList).hasSize(databaseSizeBeforeCreate + 1);
        Commerce testCommerce = commerceList.get(commerceList.size() - 1);
        assertThat(testCommerce.getNomCommerce()).isEqualTo(DEFAULT_NOM_COMMERCE);
        assertThat(testCommerce.getNomRue()).isEqualTo(DEFAULT_NOM_RUE);
        assertThat(testCommerce.getCodePostale()).isEqualTo(DEFAULT_CODE_POSTALE);
        assertThat(testCommerce.getSiteWeb()).isEqualTo(DEFAULT_SITE_WEB);
        assertThat(testCommerce.getDescCommerce()).isEqualTo(DEFAULT_DESC_COMMERCE);

        // Validate the Commerce in Elasticsearch
        verify(mockCommerceSearchRepository, times(1)).save(testCommerce);
    }

    @Test
    @Transactional
    public void createCommerceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceRepository.findAll().size();

        // Create the Commerce with an existing ID
        commerce.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceMockMvc.perform(post("/api/commerce")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerce)))
            .andExpect(status().isBadRequest());

        // Validate the Commerce in the database
        List<Commerce> commerceList = commerceRepository.findAll();
        assertThat(commerceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Commerce in Elasticsearch
        verify(mockCommerceSearchRepository, times(0)).save(commerce);
    }

    @Test
    @Transactional
    public void checkNomCommerceIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceRepository.findAll().size();
        // set the field null
        commerce.setNomCommerce(null);

        // Create the Commerce, which fails.

        restCommerceMockMvc.perform(post("/api/commerce")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerce)))
            .andExpect(status().isBadRequest());

        List<Commerce> commerceList = commerceRepository.findAll();
        assertThat(commerceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommerce() throws Exception {
        // Initialize the database
        commerceRepository.saveAndFlush(commerce);

        // Get all the commerceList
        restCommerceMockMvc.perform(get("/api/commerce?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerce.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomCommerce").value(hasItem(DEFAULT_NOM_COMMERCE.toString())))
            .andExpect(jsonPath("$.[*].nomRue").value(hasItem(DEFAULT_NOM_RUE.toString())))
            .andExpect(jsonPath("$.[*].codePostale").value(hasItem(DEFAULT_CODE_POSTALE.toString())))
            .andExpect(jsonPath("$.[*].siteWeb").value(hasItem(DEFAULT_SITE_WEB.toString())))
            .andExpect(jsonPath("$.[*].descCommerce").value(hasItem(DEFAULT_DESC_COMMERCE.toString())));
    }
    
    @Test
    @Transactional
    public void getCommerce() throws Exception {
        // Initialize the database
        commerceRepository.saveAndFlush(commerce);

        // Get the commerce
        restCommerceMockMvc.perform(get("/api/commerce/{id}", commerce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerce.getId().intValue()))
            .andExpect(jsonPath("$.nomCommerce").value(DEFAULT_NOM_COMMERCE.toString()))
            .andExpect(jsonPath("$.nomRue").value(DEFAULT_NOM_RUE.toString()))
            .andExpect(jsonPath("$.codePostale").value(DEFAULT_CODE_POSTALE.toString()))
            .andExpect(jsonPath("$.siteWeb").value(DEFAULT_SITE_WEB.toString()))
            .andExpect(jsonPath("$.descCommerce").value(DEFAULT_DESC_COMMERCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommerce() throws Exception {
        // Get the commerce
        restCommerceMockMvc.perform(get("/api/commerce/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerce() throws Exception {
        // Initialize the database
        commerceService.save(commerce);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCommerceSearchRepository);

        int databaseSizeBeforeUpdate = commerceRepository.findAll().size();

        // Update the commerce
        Commerce updatedCommerce = commerceRepository.findById(commerce.getId()).get();
        // Disconnect from session so that the updates on updatedCommerce are not directly saved in db
        em.detach(updatedCommerce);
        updatedCommerce
            .nomCommerce(UPDATED_NOM_COMMERCE)
            .nomRue(UPDATED_NOM_RUE)
            .codePostale(UPDATED_CODE_POSTALE)
            .siteWeb(UPDATED_SITE_WEB)
            .descCommerce(UPDATED_DESC_COMMERCE);

        restCommerceMockMvc.perform(put("/api/commerce")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommerce)))
            .andExpect(status().isOk());

        // Validate the Commerce in the database
        List<Commerce> commerceList = commerceRepository.findAll();
        assertThat(commerceList).hasSize(databaseSizeBeforeUpdate);
        Commerce testCommerce = commerceList.get(commerceList.size() - 1);
        assertThat(testCommerce.getNomCommerce()).isEqualTo(UPDATED_NOM_COMMERCE);
        assertThat(testCommerce.getNomRue()).isEqualTo(UPDATED_NOM_RUE);
        assertThat(testCommerce.getCodePostale()).isEqualTo(UPDATED_CODE_POSTALE);
        assertThat(testCommerce.getSiteWeb()).isEqualTo(UPDATED_SITE_WEB);
        assertThat(testCommerce.getDescCommerce()).isEqualTo(UPDATED_DESC_COMMERCE);

        // Validate the Commerce in Elasticsearch
        verify(mockCommerceSearchRepository, times(1)).save(testCommerce);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerce() throws Exception {
        int databaseSizeBeforeUpdate = commerceRepository.findAll().size();

        // Create the Commerce

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommerceMockMvc.perform(put("/api/commerce")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerce)))
            .andExpect(status().isBadRequest());

        // Validate the Commerce in the database
        List<Commerce> commerceList = commerceRepository.findAll();
        assertThat(commerceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Commerce in Elasticsearch
        verify(mockCommerceSearchRepository, times(0)).save(commerce);
    }

    @Test
    @Transactional
    public void deleteCommerce() throws Exception {
        // Initialize the database
        commerceService.save(commerce);

        int databaseSizeBeforeDelete = commerceRepository.findAll().size();

        // Delete the commerce
        restCommerceMockMvc.perform(delete("/api/commerce/{id}", commerce.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Commerce> commerceList = commerceRepository.findAll();
        assertThat(commerceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Commerce in Elasticsearch
        verify(mockCommerceSearchRepository, times(1)).deleteById(commerce.getId());
    }

    @Test
    @Transactional
    public void searchCommerce() throws Exception {
        // Initialize the database
        commerceService.save(commerce);
        when(mockCommerceSearchRepository.search(queryStringQuery("id:" + commerce.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commerce), PageRequest.of(0, 1), 1));
        // Search the commerce
        restCommerceMockMvc.perform(get("/api/_search/commerce?query=id:" + commerce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerce.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomCommerce").value(hasItem(DEFAULT_NOM_COMMERCE)))
            .andExpect(jsonPath("$.[*].nomRue").value(hasItem(DEFAULT_NOM_RUE)))
            .andExpect(jsonPath("$.[*].codePostale").value(hasItem(DEFAULT_CODE_POSTALE)))
            .andExpect(jsonPath("$.[*].siteWeb").value(hasItem(DEFAULT_SITE_WEB)))
            .andExpect(jsonPath("$.[*].descCommerce").value(hasItem(DEFAULT_DESC_COMMERCE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commerce.class);
        Commerce commerce1 = new Commerce();
        commerce1.setId(1L);
        Commerce commerce2 = new Commerce();
        commerce2.setId(commerce1.getId());
        assertThat(commerce1).isEqualTo(commerce2);
        commerce2.setId(2L);
        assertThat(commerce1).isNotEqualTo(commerce2);
        commerce1.setId(null);
        assertThat(commerce1).isNotEqualTo(commerce2);
    }
}

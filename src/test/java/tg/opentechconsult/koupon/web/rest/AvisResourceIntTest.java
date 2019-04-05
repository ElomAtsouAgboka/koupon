package tg.opentechconsult.koupon.web.rest;

import tg.opentechconsult.koupon.KouponApp;

import tg.opentechconsult.koupon.domain.Avis;
import tg.opentechconsult.koupon.repository.AvisRepository;
import tg.opentechconsult.koupon.repository.search.AvisSearchRepository;
import tg.opentechconsult.koupon.service.AvisService;
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
 * Test class for the AvisResource REST controller.
 *
 * @see AvisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouponApp.class)
public class AvisResourceIntTest {

    private static final String DEFAULT_TEXT_AVIS = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_AVIS = "BBBBBBBBBB";

    private static final Integer DEFAULT_DERNIERE_UTILISATION_COUPON = 1;
    private static final Integer UPDATED_DERNIERE_UTILISATION_COUPON = 2;

    @Autowired
    private AvisRepository avisRepository;

    @Autowired
    private AvisService avisService;

    /**
     * This repository is mocked in the tg.opentechconsult.koupon.repository.search test package.
     *
     * @see tg.opentechconsult.koupon.repository.search.AvisSearchRepositoryMockConfiguration
     */
    @Autowired
    private AvisSearchRepository mockAvisSearchRepository;

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

    private MockMvc restAvisMockMvc;

    private Avis avis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AvisResource avisResource = new AvisResource(avisService);
        this.restAvisMockMvc = MockMvcBuilders.standaloneSetup(avisResource)
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
    public static Avis createEntity(EntityManager em) {
        Avis avis = new Avis()
            .textAvis(DEFAULT_TEXT_AVIS)
            .derniereUtilisationCoupon(DEFAULT_DERNIERE_UTILISATION_COUPON);
        return avis;
    }

    @Before
    public void initTest() {
        avis = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvis() throws Exception {
        int databaseSizeBeforeCreate = avisRepository.findAll().size();

        // Create the Avis
        restAvisMockMvc.perform(post("/api/avis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avis)))
            .andExpect(status().isCreated());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeCreate + 1);
        Avis testAvis = avisList.get(avisList.size() - 1);
        assertThat(testAvis.getTextAvis()).isEqualTo(DEFAULT_TEXT_AVIS);
        assertThat(testAvis.getDerniereUtilisationCoupon()).isEqualTo(DEFAULT_DERNIERE_UTILISATION_COUPON);

        // Validate the Avis in Elasticsearch
        verify(mockAvisSearchRepository, times(1)).save(testAvis);
    }

    @Test
    @Transactional
    public void createAvisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avisRepository.findAll().size();

        // Create the Avis with an existing ID
        avis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisMockMvc.perform(post("/api/avis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avis)))
            .andExpect(status().isBadRequest());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeCreate);

        // Validate the Avis in Elasticsearch
        verify(mockAvisSearchRepository, times(0)).save(avis);
    }

    @Test
    @Transactional
    public void checkTextAvisIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisRepository.findAll().size();
        // set the field null
        avis.setTextAvis(null);

        // Create the Avis, which fails.

        restAvisMockMvc.perform(post("/api/avis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avis)))
            .andExpect(status().isBadRequest());

        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvis() throws Exception {
        // Initialize the database
        avisRepository.saveAndFlush(avis);

        // Get all the avisList
        restAvisMockMvc.perform(get("/api/avis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avis.getId().intValue())))
            .andExpect(jsonPath("$.[*].textAvis").value(hasItem(DEFAULT_TEXT_AVIS.toString())))
            .andExpect(jsonPath("$.[*].derniereUtilisationCoupon").value(hasItem(DEFAULT_DERNIERE_UTILISATION_COUPON)));
    }
    
    @Test
    @Transactional
    public void getAvis() throws Exception {
        // Initialize the database
        avisRepository.saveAndFlush(avis);

        // Get the avis
        restAvisMockMvc.perform(get("/api/avis/{id}", avis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avis.getId().intValue()))
            .andExpect(jsonPath("$.textAvis").value(DEFAULT_TEXT_AVIS.toString()))
            .andExpect(jsonPath("$.derniereUtilisationCoupon").value(DEFAULT_DERNIERE_UTILISATION_COUPON));
    }

    @Test
    @Transactional
    public void getNonExistingAvis() throws Exception {
        // Get the avis
        restAvisMockMvc.perform(get("/api/avis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvis() throws Exception {
        // Initialize the database
        avisService.save(avis);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockAvisSearchRepository);

        int databaseSizeBeforeUpdate = avisRepository.findAll().size();

        // Update the avis
        Avis updatedAvis = avisRepository.findById(avis.getId()).get();
        // Disconnect from session so that the updates on updatedAvis are not directly saved in db
        em.detach(updatedAvis);
        updatedAvis
            .textAvis(UPDATED_TEXT_AVIS)
            .derniereUtilisationCoupon(UPDATED_DERNIERE_UTILISATION_COUPON);

        restAvisMockMvc.perform(put("/api/avis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvis)))
            .andExpect(status().isOk());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeUpdate);
        Avis testAvis = avisList.get(avisList.size() - 1);
        assertThat(testAvis.getTextAvis()).isEqualTo(UPDATED_TEXT_AVIS);
        assertThat(testAvis.getDerniereUtilisationCoupon()).isEqualTo(UPDATED_DERNIERE_UTILISATION_COUPON);

        // Validate the Avis in Elasticsearch
        verify(mockAvisSearchRepository, times(1)).save(testAvis);
    }

    @Test
    @Transactional
    public void updateNonExistingAvis() throws Exception {
        int databaseSizeBeforeUpdate = avisRepository.findAll().size();

        // Create the Avis

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisMockMvc.perform(put("/api/avis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avis)))
            .andExpect(status().isBadRequest());

        // Validate the Avis in the database
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Avis in Elasticsearch
        verify(mockAvisSearchRepository, times(0)).save(avis);
    }

    @Test
    @Transactional
    public void deleteAvis() throws Exception {
        // Initialize the database
        avisService.save(avis);

        int databaseSizeBeforeDelete = avisRepository.findAll().size();

        // Delete the avis
        restAvisMockMvc.perform(delete("/api/avis/{id}", avis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Avis> avisList = avisRepository.findAll();
        assertThat(avisList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Avis in Elasticsearch
        verify(mockAvisSearchRepository, times(1)).deleteById(avis.getId());
    }

    @Test
    @Transactional
    public void searchAvis() throws Exception {
        // Initialize the database
        avisService.save(avis);
        when(mockAvisSearchRepository.search(queryStringQuery("id:" + avis.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(avis), PageRequest.of(0, 1), 1));
        // Search the avis
        restAvisMockMvc.perform(get("/api/_search/avis?query=id:" + avis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avis.getId().intValue())))
            .andExpect(jsonPath("$.[*].textAvis").value(hasItem(DEFAULT_TEXT_AVIS)))
            .andExpect(jsonPath("$.[*].derniereUtilisationCoupon").value(hasItem(DEFAULT_DERNIERE_UTILISATION_COUPON)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avis.class);
        Avis avis1 = new Avis();
        avis1.setId(1L);
        Avis avis2 = new Avis();
        avis2.setId(avis1.getId());
        assertThat(avis1).isEqualTo(avis2);
        avis2.setId(2L);
        assertThat(avis1).isNotEqualTo(avis2);
        avis1.setId(null);
        assertThat(avis1).isNotEqualTo(avis2);
    }
}

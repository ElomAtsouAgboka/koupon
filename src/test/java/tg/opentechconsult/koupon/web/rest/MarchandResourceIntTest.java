package tg.opentechconsult.koupon.web.rest;

import tg.opentechconsult.koupon.KouponApp;

import tg.opentechconsult.koupon.domain.Marchand;
import tg.opentechconsult.koupon.repository.MarchandRepository;
import tg.opentechconsult.koupon.repository.search.MarchandSearchRepository;
import tg.opentechconsult.koupon.service.MarchandService;
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
 * Test class for the MarchandResource REST controller.
 *
 * @see MarchandResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouponApp.class)
public class MarchandResourceIntTest {

    private static final String DEFAULT_NOM_MARCHAND = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MARCHAND = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_MARCHAND = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_MARCHAND = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_PRINCIPALE = "AAAAAAAAAA";
    private static final String UPDATED_TEL_PRINCIPALE = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_SECONDAIRE = "AAAAAAAAAA";
    private static final String UPDATED_TEL_SECONDAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_PRINCIPALE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_PRINCIPALE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_SECONDAIRE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SECONDAIRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NEWSLETTER = false;
    private static final Boolean UPDATED_NEWSLETTER = true;

    @Autowired
    private MarchandRepository marchandRepository;

    @Autowired
    private MarchandService marchandService;

    /**
     * This repository is mocked in the tg.opentechconsult.koupon.repository.search test package.
     *
     * @see tg.opentechconsult.koupon.repository.search.MarchandSearchRepositoryMockConfiguration
     */
    @Autowired
    private MarchandSearchRepository mockMarchandSearchRepository;

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

    private MockMvc restMarchandMockMvc;

    private Marchand marchand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarchandResource marchandResource = new MarchandResource(marchandService);
        this.restMarchandMockMvc = MockMvcBuilders.standaloneSetup(marchandResource)
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
    public static Marchand createEntity(EntityManager em) {
        Marchand marchand = new Marchand()
            .nomMarchand(DEFAULT_NOM_MARCHAND)
            .prenomMarchand(DEFAULT_PRENOM_MARCHAND)
            .telPrincipale(DEFAULT_TEL_PRINCIPALE)
            .telSecondaire(DEFAULT_TEL_SECONDAIRE)
            .emailPrincipale(DEFAULT_EMAIL_PRINCIPALE)
            .emailSecondaire(DEFAULT_EMAIL_SECONDAIRE)
            .newsletter(DEFAULT_NEWSLETTER);
        return marchand;
    }

    @Before
    public void initTest() {
        marchand = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarchand() throws Exception {
        int databaseSizeBeforeCreate = marchandRepository.findAll().size();

        // Create the Marchand
        restMarchandMockMvc.perform(post("/api/marchands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marchand)))
            .andExpect(status().isCreated());

        // Validate the Marchand in the database
        List<Marchand> marchandList = marchandRepository.findAll();
        assertThat(marchandList).hasSize(databaseSizeBeforeCreate + 1);
        Marchand testMarchand = marchandList.get(marchandList.size() - 1);
        assertThat(testMarchand.getNomMarchand()).isEqualTo(DEFAULT_NOM_MARCHAND);
        assertThat(testMarchand.getPrenomMarchand()).isEqualTo(DEFAULT_PRENOM_MARCHAND);
        assertThat(testMarchand.getTelPrincipale()).isEqualTo(DEFAULT_TEL_PRINCIPALE);
        assertThat(testMarchand.getTelSecondaire()).isEqualTo(DEFAULT_TEL_SECONDAIRE);
        assertThat(testMarchand.getEmailPrincipale()).isEqualTo(DEFAULT_EMAIL_PRINCIPALE);
        assertThat(testMarchand.getEmailSecondaire()).isEqualTo(DEFAULT_EMAIL_SECONDAIRE);
        assertThat(testMarchand.isNewsletter()).isEqualTo(DEFAULT_NEWSLETTER);

        // Validate the Marchand in Elasticsearch
        verify(mockMarchandSearchRepository, times(1)).save(testMarchand);
    }

    @Test
    @Transactional
    public void createMarchandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marchandRepository.findAll().size();

        // Create the Marchand with an existing ID
        marchand.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarchandMockMvc.perform(post("/api/marchands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marchand)))
            .andExpect(status().isBadRequest());

        // Validate the Marchand in the database
        List<Marchand> marchandList = marchandRepository.findAll();
        assertThat(marchandList).hasSize(databaseSizeBeforeCreate);

        // Validate the Marchand in Elasticsearch
        verify(mockMarchandSearchRepository, times(0)).save(marchand);
    }

    @Test
    @Transactional
    public void checkNomMarchandIsRequired() throws Exception {
        int databaseSizeBeforeTest = marchandRepository.findAll().size();
        // set the field null
        marchand.setNomMarchand(null);

        // Create the Marchand, which fails.

        restMarchandMockMvc.perform(post("/api/marchands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marchand)))
            .andExpect(status().isBadRequest());

        List<Marchand> marchandList = marchandRepository.findAll();
        assertThat(marchandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomMarchandIsRequired() throws Exception {
        int databaseSizeBeforeTest = marchandRepository.findAll().size();
        // set the field null
        marchand.setPrenomMarchand(null);

        // Create the Marchand, which fails.

        restMarchandMockMvc.perform(post("/api/marchands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marchand)))
            .andExpect(status().isBadRequest());

        List<Marchand> marchandList = marchandRepository.findAll();
        assertThat(marchandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelPrincipaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = marchandRepository.findAll().size();
        // set the field null
        marchand.setTelPrincipale(null);

        // Create the Marchand, which fails.

        restMarchandMockMvc.perform(post("/api/marchands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marchand)))
            .andExpect(status().isBadRequest());

        List<Marchand> marchandList = marchandRepository.findAll();
        assertThat(marchandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailPrincipaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = marchandRepository.findAll().size();
        // set the field null
        marchand.setEmailPrincipale(null);

        // Create the Marchand, which fails.

        restMarchandMockMvc.perform(post("/api/marchands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marchand)))
            .andExpect(status().isBadRequest());

        List<Marchand> marchandList = marchandRepository.findAll();
        assertThat(marchandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarchands() throws Exception {
        // Initialize the database
        marchandRepository.saveAndFlush(marchand);

        // Get all the marchandList
        restMarchandMockMvc.perform(get("/api/marchands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marchand.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomMarchand").value(hasItem(DEFAULT_NOM_MARCHAND.toString())))
            .andExpect(jsonPath("$.[*].prenomMarchand").value(hasItem(DEFAULT_PRENOM_MARCHAND.toString())))
            .andExpect(jsonPath("$.[*].telPrincipale").value(hasItem(DEFAULT_TEL_PRINCIPALE.toString())))
            .andExpect(jsonPath("$.[*].telSecondaire").value(hasItem(DEFAULT_TEL_SECONDAIRE.toString())))
            .andExpect(jsonPath("$.[*].emailPrincipale").value(hasItem(DEFAULT_EMAIL_PRINCIPALE.toString())))
            .andExpect(jsonPath("$.[*].emailSecondaire").value(hasItem(DEFAULT_EMAIL_SECONDAIRE.toString())))
            .andExpect(jsonPath("$.[*].newsletter").value(hasItem(DEFAULT_NEWSLETTER.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMarchand() throws Exception {
        // Initialize the database
        marchandRepository.saveAndFlush(marchand);

        // Get the marchand
        restMarchandMockMvc.perform(get("/api/marchands/{id}", marchand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marchand.getId().intValue()))
            .andExpect(jsonPath("$.nomMarchand").value(DEFAULT_NOM_MARCHAND.toString()))
            .andExpect(jsonPath("$.prenomMarchand").value(DEFAULT_PRENOM_MARCHAND.toString()))
            .andExpect(jsonPath("$.telPrincipale").value(DEFAULT_TEL_PRINCIPALE.toString()))
            .andExpect(jsonPath("$.telSecondaire").value(DEFAULT_TEL_SECONDAIRE.toString()))
            .andExpect(jsonPath("$.emailPrincipale").value(DEFAULT_EMAIL_PRINCIPALE.toString()))
            .andExpect(jsonPath("$.emailSecondaire").value(DEFAULT_EMAIL_SECONDAIRE.toString()))
            .andExpect(jsonPath("$.newsletter").value(DEFAULT_NEWSLETTER.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMarchand() throws Exception {
        // Get the marchand
        restMarchandMockMvc.perform(get("/api/marchands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarchand() throws Exception {
        // Initialize the database
        marchandService.save(marchand);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMarchandSearchRepository);

        int databaseSizeBeforeUpdate = marchandRepository.findAll().size();

        // Update the marchand
        Marchand updatedMarchand = marchandRepository.findById(marchand.getId()).get();
        // Disconnect from session so that the updates on updatedMarchand are not directly saved in db
        em.detach(updatedMarchand);
        updatedMarchand
            .nomMarchand(UPDATED_NOM_MARCHAND)
            .prenomMarchand(UPDATED_PRENOM_MARCHAND)
            .telPrincipale(UPDATED_TEL_PRINCIPALE)
            .telSecondaire(UPDATED_TEL_SECONDAIRE)
            .emailPrincipale(UPDATED_EMAIL_PRINCIPALE)
            .emailSecondaire(UPDATED_EMAIL_SECONDAIRE)
            .newsletter(UPDATED_NEWSLETTER);

        restMarchandMockMvc.perform(put("/api/marchands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarchand)))
            .andExpect(status().isOk());

        // Validate the Marchand in the database
        List<Marchand> marchandList = marchandRepository.findAll();
        assertThat(marchandList).hasSize(databaseSizeBeforeUpdate);
        Marchand testMarchand = marchandList.get(marchandList.size() - 1);
        assertThat(testMarchand.getNomMarchand()).isEqualTo(UPDATED_NOM_MARCHAND);
        assertThat(testMarchand.getPrenomMarchand()).isEqualTo(UPDATED_PRENOM_MARCHAND);
        assertThat(testMarchand.getTelPrincipale()).isEqualTo(UPDATED_TEL_PRINCIPALE);
        assertThat(testMarchand.getTelSecondaire()).isEqualTo(UPDATED_TEL_SECONDAIRE);
        assertThat(testMarchand.getEmailPrincipale()).isEqualTo(UPDATED_EMAIL_PRINCIPALE);
        assertThat(testMarchand.getEmailSecondaire()).isEqualTo(UPDATED_EMAIL_SECONDAIRE);
        assertThat(testMarchand.isNewsletter()).isEqualTo(UPDATED_NEWSLETTER);

        // Validate the Marchand in Elasticsearch
        verify(mockMarchandSearchRepository, times(1)).save(testMarchand);
    }

    @Test
    @Transactional
    public void updateNonExistingMarchand() throws Exception {
        int databaseSizeBeforeUpdate = marchandRepository.findAll().size();

        // Create the Marchand

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarchandMockMvc.perform(put("/api/marchands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marchand)))
            .andExpect(status().isBadRequest());

        // Validate the Marchand in the database
        List<Marchand> marchandList = marchandRepository.findAll();
        assertThat(marchandList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Marchand in Elasticsearch
        verify(mockMarchandSearchRepository, times(0)).save(marchand);
    }

    @Test
    @Transactional
    public void deleteMarchand() throws Exception {
        // Initialize the database
        marchandService.save(marchand);

        int databaseSizeBeforeDelete = marchandRepository.findAll().size();

        // Delete the marchand
        restMarchandMockMvc.perform(delete("/api/marchands/{id}", marchand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Marchand> marchandList = marchandRepository.findAll();
        assertThat(marchandList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Marchand in Elasticsearch
        verify(mockMarchandSearchRepository, times(1)).deleteById(marchand.getId());
    }

    @Test
    @Transactional
    public void searchMarchand() throws Exception {
        // Initialize the database
        marchandService.save(marchand);
        when(mockMarchandSearchRepository.search(queryStringQuery("id:" + marchand.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(marchand), PageRequest.of(0, 1), 1));
        // Search the marchand
        restMarchandMockMvc.perform(get("/api/_search/marchands?query=id:" + marchand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marchand.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomMarchand").value(hasItem(DEFAULT_NOM_MARCHAND)))
            .andExpect(jsonPath("$.[*].prenomMarchand").value(hasItem(DEFAULT_PRENOM_MARCHAND)))
            .andExpect(jsonPath("$.[*].telPrincipale").value(hasItem(DEFAULT_TEL_PRINCIPALE)))
            .andExpect(jsonPath("$.[*].telSecondaire").value(hasItem(DEFAULT_TEL_SECONDAIRE)))
            .andExpect(jsonPath("$.[*].emailPrincipale").value(hasItem(DEFAULT_EMAIL_PRINCIPALE)))
            .andExpect(jsonPath("$.[*].emailSecondaire").value(hasItem(DEFAULT_EMAIL_SECONDAIRE)))
            .andExpect(jsonPath("$.[*].newsletter").value(hasItem(DEFAULT_NEWSLETTER.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Marchand.class);
        Marchand marchand1 = new Marchand();
        marchand1.setId(1L);
        Marchand marchand2 = new Marchand();
        marchand2.setId(marchand1.getId());
        assertThat(marchand1).isEqualTo(marchand2);
        marchand2.setId(2L);
        assertThat(marchand1).isNotEqualTo(marchand2);
        marchand1.setId(null);
        assertThat(marchand1).isNotEqualTo(marchand2);
    }
}

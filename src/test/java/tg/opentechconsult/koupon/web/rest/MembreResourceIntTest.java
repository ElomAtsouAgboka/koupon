package tg.opentechconsult.koupon.web.rest;

import tg.opentechconsult.koupon.KouponApp;

import tg.opentechconsult.koupon.domain.Membre;
import tg.opentechconsult.koupon.repository.MembreRepository;
import tg.opentechconsult.koupon.repository.search.MembreSearchRepository;
import tg.opentechconsult.koupon.service.MembreService;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the MembreResource REST controller.
 *
 * @see MembreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouponApp.class)
public class MembreResourceIntTest {

    private static final String DEFAULT_LOGIN_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_MEMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_MEMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MEMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_MEMBRE = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_MEMBRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL_MEMBRE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_MEMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SOUSCRIRE_MAIL_PERSO = false;
    private static final Boolean UPDATED_SOUSCRIRE_MAIL_PERSO = true;

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private MembreService membreService;

    /**
     * This repository is mocked in the tg.opentechconsult.koupon.repository.search test package.
     *
     * @see tg.opentechconsult.koupon.repository.search.MembreSearchRepositoryMockConfiguration
     */
    @Autowired
    private MembreSearchRepository mockMembreSearchRepository;

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

    private MockMvc restMembreMockMvc;

    private Membre membre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MembreResource membreResource = new MembreResource(membreService);
        this.restMembreMockMvc = MockMvcBuilders.standaloneSetup(membreResource)
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
    public static Membre createEntity(EntityManager em) {
        Membre membre = new Membre()
            .loginMember(DEFAULT_LOGIN_MEMBER)
            .nomMembre(DEFAULT_NOM_MEMBRE)
            .prenomMembre(DEFAULT_PRENOM_MEMBRE)
            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE)
            .emailMembre(DEFAULT_EMAIL_MEMBRE)
            .souscrireMailPerso(DEFAULT_SOUSCRIRE_MAIL_PERSO);
        return membre;
    }

    @Before
    public void initTest() {
        membre = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembre() throws Exception {
        int databaseSizeBeforeCreate = membreRepository.findAll().size();

        // Create the Membre
        restMembreMockMvc.perform(post("/api/membres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membre)))
            .andExpect(status().isCreated());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeCreate + 1);
        Membre testMembre = membreList.get(membreList.size() - 1);
        assertThat(testMembre.getLoginMember()).isEqualTo(DEFAULT_LOGIN_MEMBER);
        assertThat(testMembre.getNomMembre()).isEqualTo(DEFAULT_NOM_MEMBRE);
        assertThat(testMembre.getPrenomMembre()).isEqualTo(DEFAULT_PRENOM_MEMBRE);
        assertThat(testMembre.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testMembre.getEmailMembre()).isEqualTo(DEFAULT_EMAIL_MEMBRE);
        assertThat(testMembre.isSouscrireMailPerso()).isEqualTo(DEFAULT_SOUSCRIRE_MAIL_PERSO);

        // Validate the Membre in Elasticsearch
        verify(mockMembreSearchRepository, times(1)).save(testMembre);
    }

    @Test
    @Transactional
    public void createMembreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membreRepository.findAll().size();

        // Create the Membre with an existing ID
        membre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembreMockMvc.perform(post("/api/membres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membre)))
            .andExpect(status().isBadRequest());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeCreate);

        // Validate the Membre in Elasticsearch
        verify(mockMembreSearchRepository, times(0)).save(membre);
    }

    @Test
    @Transactional
    public void checkLoginMemberIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreRepository.findAll().size();
        // set the field null
        membre.setLoginMember(null);

        // Create the Membre, which fails.

        restMembreMockMvc.perform(post("/api/membres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membre)))
            .andExpect(status().isBadRequest());

        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomMembreIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreRepository.findAll().size();
        // set the field null
        membre.setNomMembre(null);

        // Create the Membre, which fails.

        restMembreMockMvc.perform(post("/api/membres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membre)))
            .andExpect(status().isBadRequest());

        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMembres() throws Exception {
        // Initialize the database
        membreRepository.saveAndFlush(membre);

        // Get all the membreList
        restMembreMockMvc.perform(get("/api/membres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membre.getId().intValue())))
            .andExpect(jsonPath("$.[*].loginMember").value(hasItem(DEFAULT_LOGIN_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].nomMembre").value(hasItem(DEFAULT_NOM_MEMBRE.toString())))
            .andExpect(jsonPath("$.[*].prenomMembre").value(hasItem(DEFAULT_PRENOM_MEMBRE.toString())))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].emailMembre").value(hasItem(DEFAULT_EMAIL_MEMBRE.toString())))
            .andExpect(jsonPath("$.[*].souscrireMailPerso").value(hasItem(DEFAULT_SOUSCRIRE_MAIL_PERSO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMembre() throws Exception {
        // Initialize the database
        membreRepository.saveAndFlush(membre);

        // Get the membre
        restMembreMockMvc.perform(get("/api/membres/{id}", membre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(membre.getId().intValue()))
            .andExpect(jsonPath("$.loginMember").value(DEFAULT_LOGIN_MEMBER.toString()))
            .andExpect(jsonPath("$.nomMembre").value(DEFAULT_NOM_MEMBRE.toString()))
            .andExpect(jsonPath("$.prenomMembre").value(DEFAULT_PRENOM_MEMBRE.toString()))
            .andExpect(jsonPath("$.dateDeNaissance").value(DEFAULT_DATE_DE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.emailMembre").value(DEFAULT_EMAIL_MEMBRE.toString()))
            .andExpect(jsonPath("$.souscrireMailPerso").value(DEFAULT_SOUSCRIRE_MAIL_PERSO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMembre() throws Exception {
        // Get the membre
        restMembreMockMvc.perform(get("/api/membres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembre() throws Exception {
        // Initialize the database
        membreService.save(membre);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMembreSearchRepository);

        int databaseSizeBeforeUpdate = membreRepository.findAll().size();

        // Update the membre
        Membre updatedMembre = membreRepository.findById(membre.getId()).get();
        // Disconnect from session so that the updates on updatedMembre are not directly saved in db
        em.detach(updatedMembre);
        updatedMembre
            .loginMember(UPDATED_LOGIN_MEMBER)
            .nomMembre(UPDATED_NOM_MEMBRE)
            .prenomMembre(UPDATED_PRENOM_MEMBRE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .emailMembre(UPDATED_EMAIL_MEMBRE)
            .souscrireMailPerso(UPDATED_SOUSCRIRE_MAIL_PERSO);

        restMembreMockMvc.perform(put("/api/membres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMembre)))
            .andExpect(status().isOk());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeUpdate);
        Membre testMembre = membreList.get(membreList.size() - 1);
        assertThat(testMembre.getLoginMember()).isEqualTo(UPDATED_LOGIN_MEMBER);
        assertThat(testMembre.getNomMembre()).isEqualTo(UPDATED_NOM_MEMBRE);
        assertThat(testMembre.getPrenomMembre()).isEqualTo(UPDATED_PRENOM_MEMBRE);
        assertThat(testMembre.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testMembre.getEmailMembre()).isEqualTo(UPDATED_EMAIL_MEMBRE);
        assertThat(testMembre.isSouscrireMailPerso()).isEqualTo(UPDATED_SOUSCRIRE_MAIL_PERSO);

        // Validate the Membre in Elasticsearch
        verify(mockMembreSearchRepository, times(1)).save(testMembre);
    }

    @Test
    @Transactional
    public void updateNonExistingMembre() throws Exception {
        int databaseSizeBeforeUpdate = membreRepository.findAll().size();

        // Create the Membre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembreMockMvc.perform(put("/api/membres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membre)))
            .andExpect(status().isBadRequest());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Membre in Elasticsearch
        verify(mockMembreSearchRepository, times(0)).save(membre);
    }

    @Test
    @Transactional
    public void deleteMembre() throws Exception {
        // Initialize the database
        membreService.save(membre);

        int databaseSizeBeforeDelete = membreRepository.findAll().size();

        // Delete the membre
        restMembreMockMvc.perform(delete("/api/membres/{id}", membre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Membre in Elasticsearch
        verify(mockMembreSearchRepository, times(1)).deleteById(membre.getId());
    }

    @Test
    @Transactional
    public void searchMembre() throws Exception {
        // Initialize the database
        membreService.save(membre);
        when(mockMembreSearchRepository.search(queryStringQuery("id:" + membre.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(membre), PageRequest.of(0, 1), 1));
        // Search the membre
        restMembreMockMvc.perform(get("/api/_search/membres?query=id:" + membre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membre.getId().intValue())))
            .andExpect(jsonPath("$.[*].loginMember").value(hasItem(DEFAULT_LOGIN_MEMBER)))
            .andExpect(jsonPath("$.[*].nomMembre").value(hasItem(DEFAULT_NOM_MEMBRE)))
            .andExpect(jsonPath("$.[*].prenomMembre").value(hasItem(DEFAULT_PRENOM_MEMBRE)))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].emailMembre").value(hasItem(DEFAULT_EMAIL_MEMBRE)))
            .andExpect(jsonPath("$.[*].souscrireMailPerso").value(hasItem(DEFAULT_SOUSCRIRE_MAIL_PERSO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Membre.class);
        Membre membre1 = new Membre();
        membre1.setId(1L);
        Membre membre2 = new Membre();
        membre2.setId(membre1.getId());
        assertThat(membre1).isEqualTo(membre2);
        membre2.setId(2L);
        assertThat(membre1).isNotEqualTo(membre2);
        membre1.setId(null);
        assertThat(membre1).isNotEqualTo(membre2);
    }
}

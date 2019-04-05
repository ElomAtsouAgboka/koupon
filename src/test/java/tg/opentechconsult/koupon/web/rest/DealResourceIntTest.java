package tg.opentechconsult.koupon.web.rest;

import tg.opentechconsult.koupon.KouponApp;

import tg.opentechconsult.koupon.domain.Deal;
import tg.opentechconsult.koupon.repository.DealRepository;
import tg.opentechconsult.koupon.repository.search.DealSearchRepository;
import tg.opentechconsult.koupon.service.DealService;
import tg.opentechconsult.koupon.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.util.ArrayList;
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
 * Test class for the DealResource REST controller.
 *
 * @see DealResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouponApp.class)
public class DealResourceIntTest {

    private static final String DEFAULT_REF_DEAL = "AAAAAAAAAA";
    private static final String UPDATED_REF_DEAL = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE_DEAL = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_DEAL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_DEAL = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_DEAL = "BBBBBBBBBB";

    private static final String DEFAULT_PRIX_REDUIT_DEAL = "AAAAAAAAAA";
    private static final String UPDATED_PRIX_REDUIT_DEAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIX_NORMAL_DEAL = 1;
    private static final Integer UPDATED_PRIX_NORMAL_DEAL = 2;

    private static final Integer DEFAULT_PC_REDUCTION_DEAL = 1;
    private static final Integer UPDATED_PC_REDUCTION_DEAL = 2;

    private static final String DEFAULT_PHOTO_DEAL_UN = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_DEAL_UN = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_DEAL_DEUX = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_DEAL_DEUX = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_DEAL_TROIS = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_DEAL_TROIS = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_DEAL_QUATRE = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_DEAL_QUATRE = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_DEAL_CINQ = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_DEAL_CINQ = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_DEAL_SIX = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_DEAL_SIX = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_DEAL_SPET = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_DEAL_SPET = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_DEAL_HUIT = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_DEAL_HUIT = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_DEAL_NEUF = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_DEAL_NEUF = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_DEAL_DIX = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_DEAL_DIX = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_MIN_DEAL_UN = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_MIN_DEAL_UN = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_MIN_DEAL_DEUX = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_MIN_DEAL_DEUX = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_MIN_DEAL_TROIS = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_MIN_DEAL_TROIS = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_MIN_DEAL_QUATRE = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_MIN_DEAL_QUATRE = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_MIN_DEAL_CINQ = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_MIN_DEAL_CINQ = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_MIN_DEAL_SIX = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_MIN_DEAL_SIX = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_MIN_DEAL_SPET = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_MIN_DEAL_SPET = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_MIN_DEAL_HUIT = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_MIN_DEAL_HUIT = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_MIN_DEAL_NEUF = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_MIN_DEAL_NEUF = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_MIN_DEAL_DIX = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_MIN_DEAL_DIX = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_POINT_FORT_DEAL = "AAAAAAAAAA";
    private static final String UPDATED_DESC_POINT_FORT_DEAL = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS_OFFRE_DEAL = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS_OFFRE_DEAL = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITIONS_DEAL = "AAAAAAAAAA";
    private static final String UPDATED_CONDITIONS_DEAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EST_LIMITE = false;
    private static final Boolean UPDATED_EST_LIMITE = true;

    private static final Boolean DEFAULT_EST_EPUISE = false;
    private static final Boolean UPDATED_EST_EPUISE = true;

    private static final LocalDate DEFAULT_DATE_CREATION_DEAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION_DEAL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CLOTURE_DEAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CLOTURE_DEAL = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DealRepository dealRepository;

    @Mock
    private DealRepository dealRepositoryMock;

    @Mock
    private DealService dealServiceMock;

    @Autowired
    private DealService dealService;

    /**
     * This repository is mocked in the tg.opentechconsult.koupon.repository.search test package.
     *
     * @see tg.opentechconsult.koupon.repository.search.DealSearchRepositoryMockConfiguration
     */
    @Autowired
    private DealSearchRepository mockDealSearchRepository;

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

    private MockMvc restDealMockMvc;

    private Deal deal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DealResource dealResource = new DealResource(dealService);
        this.restDealMockMvc = MockMvcBuilders.standaloneSetup(dealResource)
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
    public static Deal createEntity(EntityManager em) {
        Deal deal = new Deal()
            .refDeal(DEFAULT_REF_DEAL)
            .titreDeal(DEFAULT_TITRE_DEAL)
            .descriptionDeal(DEFAULT_DESCRIPTION_DEAL)
            .prixReduitDeal(DEFAULT_PRIX_REDUIT_DEAL)
            .prixNormalDeal(DEFAULT_PRIX_NORMAL_DEAL)
            .pcReductionDeal(DEFAULT_PC_REDUCTION_DEAL)
            .photoDealUn(DEFAULT_PHOTO_DEAL_UN)
            .photoDealDeux(DEFAULT_PHOTO_DEAL_DEUX)
            .photoDealTrois(DEFAULT_PHOTO_DEAL_TROIS)
            .photoDealQuatre(DEFAULT_PHOTO_DEAL_QUATRE)
            .photoDealCinq(DEFAULT_PHOTO_DEAL_CINQ)
            .photoDealSix(DEFAULT_PHOTO_DEAL_SIX)
            .photoDealSpet(DEFAULT_PHOTO_DEAL_SPET)
            .photoDealHuit(DEFAULT_PHOTO_DEAL_HUIT)
            .photoDealNeuf(DEFAULT_PHOTO_DEAL_NEUF)
            .photoDealDix(DEFAULT_PHOTO_DEAL_DIX)
            .photoMinDealUn(DEFAULT_PHOTO_MIN_DEAL_UN)
            .photoMinDealDeux(DEFAULT_PHOTO_MIN_DEAL_DEUX)
            .photoMinDealTrois(DEFAULT_PHOTO_MIN_DEAL_TROIS)
            .photoMinDealQuatre(DEFAULT_PHOTO_MIN_DEAL_QUATRE)
            .photoMinDealCinq(DEFAULT_PHOTO_MIN_DEAL_CINQ)
            .photoMinDealSix(DEFAULT_PHOTO_MIN_DEAL_SIX)
            .photoMinDealSpet(DEFAULT_PHOTO_MIN_DEAL_SPET)
            .photoMinDealHuit(DEFAULT_PHOTO_MIN_DEAL_HUIT)
            .photoMinDealNeuf(DEFAULT_PHOTO_MIN_DEAL_NEUF)
            .photoMinDealDix(DEFAULT_PHOTO_MIN_DEAL_DIX)
            .descPointFortDeal(DEFAULT_DESC_POINT_FORT_DEAL)
            .detailsOffreDeal(DEFAULT_DETAILS_OFFRE_DEAL)
            .conditionsDeal(DEFAULT_CONDITIONS_DEAL)
            .estLimite(DEFAULT_EST_LIMITE)
            .estEpuise(DEFAULT_EST_EPUISE)
            .dateCreationDeal(DEFAULT_DATE_CREATION_DEAL)
            .dateClotureDeal(DEFAULT_DATE_CLOTURE_DEAL);
        return deal;
    }

    @Before
    public void initTest() {
        deal = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeal() throws Exception {
        int databaseSizeBeforeCreate = dealRepository.findAll().size();

        // Create the Deal
        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isCreated());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeCreate + 1);
        Deal testDeal = dealList.get(dealList.size() - 1);
        assertThat(testDeal.getRefDeal()).isEqualTo(DEFAULT_REF_DEAL);
        assertThat(testDeal.getTitreDeal()).isEqualTo(DEFAULT_TITRE_DEAL);
        assertThat(testDeal.getDescriptionDeal()).isEqualTo(DEFAULT_DESCRIPTION_DEAL);
        assertThat(testDeal.getPrixReduitDeal()).isEqualTo(DEFAULT_PRIX_REDUIT_DEAL);
        assertThat(testDeal.getPrixNormalDeal()).isEqualTo(DEFAULT_PRIX_NORMAL_DEAL);
        assertThat(testDeal.getPcReductionDeal()).isEqualTo(DEFAULT_PC_REDUCTION_DEAL);
        assertThat(testDeal.getPhotoDealUn()).isEqualTo(DEFAULT_PHOTO_DEAL_UN);
        assertThat(testDeal.getPhotoDealDeux()).isEqualTo(DEFAULT_PHOTO_DEAL_DEUX);
        assertThat(testDeal.getPhotoDealTrois()).isEqualTo(DEFAULT_PHOTO_DEAL_TROIS);
        assertThat(testDeal.getPhotoDealQuatre()).isEqualTo(DEFAULT_PHOTO_DEAL_QUATRE);
        assertThat(testDeal.getPhotoDealCinq()).isEqualTo(DEFAULT_PHOTO_DEAL_CINQ);
        assertThat(testDeal.getPhotoDealSix()).isEqualTo(DEFAULT_PHOTO_DEAL_SIX);
        assertThat(testDeal.getPhotoDealSpet()).isEqualTo(DEFAULT_PHOTO_DEAL_SPET);
        assertThat(testDeal.getPhotoDealHuit()).isEqualTo(DEFAULT_PHOTO_DEAL_HUIT);
        assertThat(testDeal.getPhotoDealNeuf()).isEqualTo(DEFAULT_PHOTO_DEAL_NEUF);
        assertThat(testDeal.getPhotoDealDix()).isEqualTo(DEFAULT_PHOTO_DEAL_DIX);
        assertThat(testDeal.getPhotoMinDealUn()).isEqualTo(DEFAULT_PHOTO_MIN_DEAL_UN);
        assertThat(testDeal.getPhotoMinDealDeux()).isEqualTo(DEFAULT_PHOTO_MIN_DEAL_DEUX);
        assertThat(testDeal.getPhotoMinDealTrois()).isEqualTo(DEFAULT_PHOTO_MIN_DEAL_TROIS);
        assertThat(testDeal.getPhotoMinDealQuatre()).isEqualTo(DEFAULT_PHOTO_MIN_DEAL_QUATRE);
        assertThat(testDeal.getPhotoMinDealCinq()).isEqualTo(DEFAULT_PHOTO_MIN_DEAL_CINQ);
        assertThat(testDeal.getPhotoMinDealSix()).isEqualTo(DEFAULT_PHOTO_MIN_DEAL_SIX);
        assertThat(testDeal.getPhotoMinDealSpet()).isEqualTo(DEFAULT_PHOTO_MIN_DEAL_SPET);
        assertThat(testDeal.getPhotoMinDealHuit()).isEqualTo(DEFAULT_PHOTO_MIN_DEAL_HUIT);
        assertThat(testDeal.getPhotoMinDealNeuf()).isEqualTo(DEFAULT_PHOTO_MIN_DEAL_NEUF);
        assertThat(testDeal.getPhotoMinDealDix()).isEqualTo(DEFAULT_PHOTO_MIN_DEAL_DIX);
        assertThat(testDeal.getDescPointFortDeal()).isEqualTo(DEFAULT_DESC_POINT_FORT_DEAL);
        assertThat(testDeal.getDetailsOffreDeal()).isEqualTo(DEFAULT_DETAILS_OFFRE_DEAL);
        assertThat(testDeal.getConditionsDeal()).isEqualTo(DEFAULT_CONDITIONS_DEAL);
        assertThat(testDeal.isEstLimite()).isEqualTo(DEFAULT_EST_LIMITE);
        assertThat(testDeal.isEstEpuise()).isEqualTo(DEFAULT_EST_EPUISE);
        assertThat(testDeal.getDateCreationDeal()).isEqualTo(DEFAULT_DATE_CREATION_DEAL);
        assertThat(testDeal.getDateClotureDeal()).isEqualTo(DEFAULT_DATE_CLOTURE_DEAL);

        // Validate the Deal in Elasticsearch
        verify(mockDealSearchRepository, times(1)).save(testDeal);
    }

    @Test
    @Transactional
    public void createDealWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dealRepository.findAll().size();

        // Create the Deal with an existing ID
        deal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeCreate);

        // Validate the Deal in Elasticsearch
        verify(mockDealSearchRepository, times(0)).save(deal);
    }

    @Test
    @Transactional
    public void checkRefDealIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRepository.findAll().size();
        // set the field null
        deal.setRefDeal(null);

        // Create the Deal, which fails.

        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitreDealIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRepository.findAll().size();
        // set the field null
        deal.setTitreDeal(null);

        // Create the Deal, which fails.

        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionDealIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRepository.findAll().size();
        // set the field null
        deal.setDescriptionDeal(null);

        // Create the Deal, which fails.

        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixReduitDealIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRepository.findAll().size();
        // set the field null
        deal.setPrixReduitDeal(null);

        // Create the Deal, which fails.

        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixNormalDealIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRepository.findAll().size();
        // set the field null
        deal.setPrixNormalDeal(null);

        // Create the Deal, which fails.

        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPcReductionDealIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRepository.findAll().size();
        // set the field null
        deal.setPcReductionDeal(null);

        // Create the Deal, which fails.

        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhotoDealUnIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRepository.findAll().size();
        // set the field null
        deal.setPhotoDealUn(null);

        // Create the Deal, which fails.

        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhotoDealDeuxIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRepository.findAll().size();
        // set the field null
        deal.setPhotoDealDeux(null);

        // Create the Deal, which fails.

        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhotoDealTroisIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRepository.findAll().size();
        // set the field null
        deal.setPhotoDealTrois(null);

        // Create the Deal, which fails.

        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhotoMinDealUnIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRepository.findAll().size();
        // set the field null
        deal.setPhotoMinDealUn(null);

        // Create the Deal, which fails.

        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhotoMinDealDeuxIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRepository.findAll().size();
        // set the field null
        deal.setPhotoMinDealDeux(null);

        // Create the Deal, which fails.

        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhotoMinDealTroisIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRepository.findAll().size();
        // set the field null
        deal.setPhotoMinDealTrois(null);

        // Create the Deal, which fails.

        restDealMockMvc.perform(post("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeals() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList
        restDealMockMvc.perform(get("/api/deals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deal.getId().intValue())))
            .andExpect(jsonPath("$.[*].refDeal").value(hasItem(DEFAULT_REF_DEAL.toString())))
            .andExpect(jsonPath("$.[*].titreDeal").value(hasItem(DEFAULT_TITRE_DEAL.toString())))
            .andExpect(jsonPath("$.[*].descriptionDeal").value(hasItem(DEFAULT_DESCRIPTION_DEAL.toString())))
            .andExpect(jsonPath("$.[*].prixReduitDeal").value(hasItem(DEFAULT_PRIX_REDUIT_DEAL.toString())))
            .andExpect(jsonPath("$.[*].prixNormalDeal").value(hasItem(DEFAULT_PRIX_NORMAL_DEAL)))
            .andExpect(jsonPath("$.[*].pcReductionDeal").value(hasItem(DEFAULT_PC_REDUCTION_DEAL)))
            .andExpect(jsonPath("$.[*].photoDealUn").value(hasItem(DEFAULT_PHOTO_DEAL_UN.toString())))
            .andExpect(jsonPath("$.[*].photoDealDeux").value(hasItem(DEFAULT_PHOTO_DEAL_DEUX.toString())))
            .andExpect(jsonPath("$.[*].photoDealTrois").value(hasItem(DEFAULT_PHOTO_DEAL_TROIS.toString())))
            .andExpect(jsonPath("$.[*].photoDealQuatre").value(hasItem(DEFAULT_PHOTO_DEAL_QUATRE.toString())))
            .andExpect(jsonPath("$.[*].photoDealCinq").value(hasItem(DEFAULT_PHOTO_DEAL_CINQ.toString())))
            .andExpect(jsonPath("$.[*].photoDealSix").value(hasItem(DEFAULT_PHOTO_DEAL_SIX.toString())))
            .andExpect(jsonPath("$.[*].photoDealSpet").value(hasItem(DEFAULT_PHOTO_DEAL_SPET.toString())))
            .andExpect(jsonPath("$.[*].photoDealHuit").value(hasItem(DEFAULT_PHOTO_DEAL_HUIT.toString())))
            .andExpect(jsonPath("$.[*].photoDealNeuf").value(hasItem(DEFAULT_PHOTO_DEAL_NEUF.toString())))
            .andExpect(jsonPath("$.[*].photoDealDix").value(hasItem(DEFAULT_PHOTO_DEAL_DIX.toString())))
            .andExpect(jsonPath("$.[*].photoMinDealUn").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_UN.toString())))
            .andExpect(jsonPath("$.[*].photoMinDealDeux").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_DEUX.toString())))
            .andExpect(jsonPath("$.[*].photoMinDealTrois").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_TROIS.toString())))
            .andExpect(jsonPath("$.[*].photoMinDealQuatre").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_QUATRE.toString())))
            .andExpect(jsonPath("$.[*].photoMinDealCinq").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_CINQ.toString())))
            .andExpect(jsonPath("$.[*].photoMinDealSix").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_SIX.toString())))
            .andExpect(jsonPath("$.[*].photoMinDealSpet").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_SPET.toString())))
            .andExpect(jsonPath("$.[*].photoMinDealHuit").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_HUIT.toString())))
            .andExpect(jsonPath("$.[*].photoMinDealNeuf").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_NEUF.toString())))
            .andExpect(jsonPath("$.[*].photoMinDealDix").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_DIX.toString())))
            .andExpect(jsonPath("$.[*].descPointFortDeal").value(hasItem(DEFAULT_DESC_POINT_FORT_DEAL.toString())))
            .andExpect(jsonPath("$.[*].detailsOffreDeal").value(hasItem(DEFAULT_DETAILS_OFFRE_DEAL.toString())))
            .andExpect(jsonPath("$.[*].conditionsDeal").value(hasItem(DEFAULT_CONDITIONS_DEAL.toString())))
            .andExpect(jsonPath("$.[*].estLimite").value(hasItem(DEFAULT_EST_LIMITE.booleanValue())))
            .andExpect(jsonPath("$.[*].estEpuise").value(hasItem(DEFAULT_EST_EPUISE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreationDeal").value(hasItem(DEFAULT_DATE_CREATION_DEAL.toString())))
            .andExpect(jsonPath("$.[*].dateClotureDeal").value(hasItem(DEFAULT_DATE_CLOTURE_DEAL.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDealsWithEagerRelationshipsIsEnabled() throws Exception {
        DealResource dealResource = new DealResource(dealServiceMock);
        when(dealServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDealMockMvc = MockMvcBuilders.standaloneSetup(dealResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDealMockMvc.perform(get("/api/deals?eagerload=true"))
        .andExpect(status().isOk());

        verify(dealServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDealsWithEagerRelationshipsIsNotEnabled() throws Exception {
        DealResource dealResource = new DealResource(dealServiceMock);
            when(dealServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDealMockMvc = MockMvcBuilders.standaloneSetup(dealResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDealMockMvc.perform(get("/api/deals?eagerload=true"))
        .andExpect(status().isOk());

            verify(dealServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDeal() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get the deal
        restDealMockMvc.perform(get("/api/deals/{id}", deal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deal.getId().intValue()))
            .andExpect(jsonPath("$.refDeal").value(DEFAULT_REF_DEAL.toString()))
            .andExpect(jsonPath("$.titreDeal").value(DEFAULT_TITRE_DEAL.toString()))
            .andExpect(jsonPath("$.descriptionDeal").value(DEFAULT_DESCRIPTION_DEAL.toString()))
            .andExpect(jsonPath("$.prixReduitDeal").value(DEFAULT_PRIX_REDUIT_DEAL.toString()))
            .andExpect(jsonPath("$.prixNormalDeal").value(DEFAULT_PRIX_NORMAL_DEAL))
            .andExpect(jsonPath("$.pcReductionDeal").value(DEFAULT_PC_REDUCTION_DEAL))
            .andExpect(jsonPath("$.photoDealUn").value(DEFAULT_PHOTO_DEAL_UN.toString()))
            .andExpect(jsonPath("$.photoDealDeux").value(DEFAULT_PHOTO_DEAL_DEUX.toString()))
            .andExpect(jsonPath("$.photoDealTrois").value(DEFAULT_PHOTO_DEAL_TROIS.toString()))
            .andExpect(jsonPath("$.photoDealQuatre").value(DEFAULT_PHOTO_DEAL_QUATRE.toString()))
            .andExpect(jsonPath("$.photoDealCinq").value(DEFAULT_PHOTO_DEAL_CINQ.toString()))
            .andExpect(jsonPath("$.photoDealSix").value(DEFAULT_PHOTO_DEAL_SIX.toString()))
            .andExpect(jsonPath("$.photoDealSpet").value(DEFAULT_PHOTO_DEAL_SPET.toString()))
            .andExpect(jsonPath("$.photoDealHuit").value(DEFAULT_PHOTO_DEAL_HUIT.toString()))
            .andExpect(jsonPath("$.photoDealNeuf").value(DEFAULT_PHOTO_DEAL_NEUF.toString()))
            .andExpect(jsonPath("$.photoDealDix").value(DEFAULT_PHOTO_DEAL_DIX.toString()))
            .andExpect(jsonPath("$.photoMinDealUn").value(DEFAULT_PHOTO_MIN_DEAL_UN.toString()))
            .andExpect(jsonPath("$.photoMinDealDeux").value(DEFAULT_PHOTO_MIN_DEAL_DEUX.toString()))
            .andExpect(jsonPath("$.photoMinDealTrois").value(DEFAULT_PHOTO_MIN_DEAL_TROIS.toString()))
            .andExpect(jsonPath("$.photoMinDealQuatre").value(DEFAULT_PHOTO_MIN_DEAL_QUATRE.toString()))
            .andExpect(jsonPath("$.photoMinDealCinq").value(DEFAULT_PHOTO_MIN_DEAL_CINQ.toString()))
            .andExpect(jsonPath("$.photoMinDealSix").value(DEFAULT_PHOTO_MIN_DEAL_SIX.toString()))
            .andExpect(jsonPath("$.photoMinDealSpet").value(DEFAULT_PHOTO_MIN_DEAL_SPET.toString()))
            .andExpect(jsonPath("$.photoMinDealHuit").value(DEFAULT_PHOTO_MIN_DEAL_HUIT.toString()))
            .andExpect(jsonPath("$.photoMinDealNeuf").value(DEFAULT_PHOTO_MIN_DEAL_NEUF.toString()))
            .andExpect(jsonPath("$.photoMinDealDix").value(DEFAULT_PHOTO_MIN_DEAL_DIX.toString()))
            .andExpect(jsonPath("$.descPointFortDeal").value(DEFAULT_DESC_POINT_FORT_DEAL.toString()))
            .andExpect(jsonPath("$.detailsOffreDeal").value(DEFAULT_DETAILS_OFFRE_DEAL.toString()))
            .andExpect(jsonPath("$.conditionsDeal").value(DEFAULT_CONDITIONS_DEAL.toString()))
            .andExpect(jsonPath("$.estLimite").value(DEFAULT_EST_LIMITE.booleanValue()))
            .andExpect(jsonPath("$.estEpuise").value(DEFAULT_EST_EPUISE.booleanValue()))
            .andExpect(jsonPath("$.dateCreationDeal").value(DEFAULT_DATE_CREATION_DEAL.toString()))
            .andExpect(jsonPath("$.dateClotureDeal").value(DEFAULT_DATE_CLOTURE_DEAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeal() throws Exception {
        // Get the deal
        restDealMockMvc.perform(get("/api/deals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeal() throws Exception {
        // Initialize the database
        dealService.save(deal);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockDealSearchRepository);

        int databaseSizeBeforeUpdate = dealRepository.findAll().size();

        // Update the deal
        Deal updatedDeal = dealRepository.findById(deal.getId()).get();
        // Disconnect from session so that the updates on updatedDeal are not directly saved in db
        em.detach(updatedDeal);
        updatedDeal
            .refDeal(UPDATED_REF_DEAL)
            .titreDeal(UPDATED_TITRE_DEAL)
            .descriptionDeal(UPDATED_DESCRIPTION_DEAL)
            .prixReduitDeal(UPDATED_PRIX_REDUIT_DEAL)
            .prixNormalDeal(UPDATED_PRIX_NORMAL_DEAL)
            .pcReductionDeal(UPDATED_PC_REDUCTION_DEAL)
            .photoDealUn(UPDATED_PHOTO_DEAL_UN)
            .photoDealDeux(UPDATED_PHOTO_DEAL_DEUX)
            .photoDealTrois(UPDATED_PHOTO_DEAL_TROIS)
            .photoDealQuatre(UPDATED_PHOTO_DEAL_QUATRE)
            .photoDealCinq(UPDATED_PHOTO_DEAL_CINQ)
            .photoDealSix(UPDATED_PHOTO_DEAL_SIX)
            .photoDealSpet(UPDATED_PHOTO_DEAL_SPET)
            .photoDealHuit(UPDATED_PHOTO_DEAL_HUIT)
            .photoDealNeuf(UPDATED_PHOTO_DEAL_NEUF)
            .photoDealDix(UPDATED_PHOTO_DEAL_DIX)
            .photoMinDealUn(UPDATED_PHOTO_MIN_DEAL_UN)
            .photoMinDealDeux(UPDATED_PHOTO_MIN_DEAL_DEUX)
            .photoMinDealTrois(UPDATED_PHOTO_MIN_DEAL_TROIS)
            .photoMinDealQuatre(UPDATED_PHOTO_MIN_DEAL_QUATRE)
            .photoMinDealCinq(UPDATED_PHOTO_MIN_DEAL_CINQ)
            .photoMinDealSix(UPDATED_PHOTO_MIN_DEAL_SIX)
            .photoMinDealSpet(UPDATED_PHOTO_MIN_DEAL_SPET)
            .photoMinDealHuit(UPDATED_PHOTO_MIN_DEAL_HUIT)
            .photoMinDealNeuf(UPDATED_PHOTO_MIN_DEAL_NEUF)
            .photoMinDealDix(UPDATED_PHOTO_MIN_DEAL_DIX)
            .descPointFortDeal(UPDATED_DESC_POINT_FORT_DEAL)
            .detailsOffreDeal(UPDATED_DETAILS_OFFRE_DEAL)
            .conditionsDeal(UPDATED_CONDITIONS_DEAL)
            .estLimite(UPDATED_EST_LIMITE)
            .estEpuise(UPDATED_EST_EPUISE)
            .dateCreationDeal(UPDATED_DATE_CREATION_DEAL)
            .dateClotureDeal(UPDATED_DATE_CLOTURE_DEAL);

        restDealMockMvc.perform(put("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeal)))
            .andExpect(status().isOk());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
        Deal testDeal = dealList.get(dealList.size() - 1);
        assertThat(testDeal.getRefDeal()).isEqualTo(UPDATED_REF_DEAL);
        assertThat(testDeal.getTitreDeal()).isEqualTo(UPDATED_TITRE_DEAL);
        assertThat(testDeal.getDescriptionDeal()).isEqualTo(UPDATED_DESCRIPTION_DEAL);
        assertThat(testDeal.getPrixReduitDeal()).isEqualTo(UPDATED_PRIX_REDUIT_DEAL);
        assertThat(testDeal.getPrixNormalDeal()).isEqualTo(UPDATED_PRIX_NORMAL_DEAL);
        assertThat(testDeal.getPcReductionDeal()).isEqualTo(UPDATED_PC_REDUCTION_DEAL);
        assertThat(testDeal.getPhotoDealUn()).isEqualTo(UPDATED_PHOTO_DEAL_UN);
        assertThat(testDeal.getPhotoDealDeux()).isEqualTo(UPDATED_PHOTO_DEAL_DEUX);
        assertThat(testDeal.getPhotoDealTrois()).isEqualTo(UPDATED_PHOTO_DEAL_TROIS);
        assertThat(testDeal.getPhotoDealQuatre()).isEqualTo(UPDATED_PHOTO_DEAL_QUATRE);
        assertThat(testDeal.getPhotoDealCinq()).isEqualTo(UPDATED_PHOTO_DEAL_CINQ);
        assertThat(testDeal.getPhotoDealSix()).isEqualTo(UPDATED_PHOTO_DEAL_SIX);
        assertThat(testDeal.getPhotoDealSpet()).isEqualTo(UPDATED_PHOTO_DEAL_SPET);
        assertThat(testDeal.getPhotoDealHuit()).isEqualTo(UPDATED_PHOTO_DEAL_HUIT);
        assertThat(testDeal.getPhotoDealNeuf()).isEqualTo(UPDATED_PHOTO_DEAL_NEUF);
        assertThat(testDeal.getPhotoDealDix()).isEqualTo(UPDATED_PHOTO_DEAL_DIX);
        assertThat(testDeal.getPhotoMinDealUn()).isEqualTo(UPDATED_PHOTO_MIN_DEAL_UN);
        assertThat(testDeal.getPhotoMinDealDeux()).isEqualTo(UPDATED_PHOTO_MIN_DEAL_DEUX);
        assertThat(testDeal.getPhotoMinDealTrois()).isEqualTo(UPDATED_PHOTO_MIN_DEAL_TROIS);
        assertThat(testDeal.getPhotoMinDealQuatre()).isEqualTo(UPDATED_PHOTO_MIN_DEAL_QUATRE);
        assertThat(testDeal.getPhotoMinDealCinq()).isEqualTo(UPDATED_PHOTO_MIN_DEAL_CINQ);
        assertThat(testDeal.getPhotoMinDealSix()).isEqualTo(UPDATED_PHOTO_MIN_DEAL_SIX);
        assertThat(testDeal.getPhotoMinDealSpet()).isEqualTo(UPDATED_PHOTO_MIN_DEAL_SPET);
        assertThat(testDeal.getPhotoMinDealHuit()).isEqualTo(UPDATED_PHOTO_MIN_DEAL_HUIT);
        assertThat(testDeal.getPhotoMinDealNeuf()).isEqualTo(UPDATED_PHOTO_MIN_DEAL_NEUF);
        assertThat(testDeal.getPhotoMinDealDix()).isEqualTo(UPDATED_PHOTO_MIN_DEAL_DIX);
        assertThat(testDeal.getDescPointFortDeal()).isEqualTo(UPDATED_DESC_POINT_FORT_DEAL);
        assertThat(testDeal.getDetailsOffreDeal()).isEqualTo(UPDATED_DETAILS_OFFRE_DEAL);
        assertThat(testDeal.getConditionsDeal()).isEqualTo(UPDATED_CONDITIONS_DEAL);
        assertThat(testDeal.isEstLimite()).isEqualTo(UPDATED_EST_LIMITE);
        assertThat(testDeal.isEstEpuise()).isEqualTo(UPDATED_EST_EPUISE);
        assertThat(testDeal.getDateCreationDeal()).isEqualTo(UPDATED_DATE_CREATION_DEAL);
        assertThat(testDeal.getDateClotureDeal()).isEqualTo(UPDATED_DATE_CLOTURE_DEAL);

        // Validate the Deal in Elasticsearch
        verify(mockDealSearchRepository, times(1)).save(testDeal);
    }

    @Test
    @Transactional
    public void updateNonExistingDeal() throws Exception {
        int databaseSizeBeforeUpdate = dealRepository.findAll().size();

        // Create the Deal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealMockMvc.perform(put("/api/deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Deal in Elasticsearch
        verify(mockDealSearchRepository, times(0)).save(deal);
    }

    @Test
    @Transactional
    public void deleteDeal() throws Exception {
        // Initialize the database
        dealService.save(deal);

        int databaseSizeBeforeDelete = dealRepository.findAll().size();

        // Delete the deal
        restDealMockMvc.perform(delete("/api/deals/{id}", deal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Deal in Elasticsearch
        verify(mockDealSearchRepository, times(1)).deleteById(deal.getId());
    }

    @Test
    @Transactional
    public void searchDeal() throws Exception {
        // Initialize the database
        dealService.save(deal);
        when(mockDealSearchRepository.search(queryStringQuery("id:" + deal.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(deal), PageRequest.of(0, 1), 1));
        // Search the deal
        restDealMockMvc.perform(get("/api/_search/deals?query=id:" + deal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deal.getId().intValue())))
            .andExpect(jsonPath("$.[*].refDeal").value(hasItem(DEFAULT_REF_DEAL)))
            .andExpect(jsonPath("$.[*].titreDeal").value(hasItem(DEFAULT_TITRE_DEAL)))
            .andExpect(jsonPath("$.[*].descriptionDeal").value(hasItem(DEFAULT_DESCRIPTION_DEAL)))
            .andExpect(jsonPath("$.[*].prixReduitDeal").value(hasItem(DEFAULT_PRIX_REDUIT_DEAL)))
            .andExpect(jsonPath("$.[*].prixNormalDeal").value(hasItem(DEFAULT_PRIX_NORMAL_DEAL)))
            .andExpect(jsonPath("$.[*].pcReductionDeal").value(hasItem(DEFAULT_PC_REDUCTION_DEAL)))
            .andExpect(jsonPath("$.[*].photoDealUn").value(hasItem(DEFAULT_PHOTO_DEAL_UN)))
            .andExpect(jsonPath("$.[*].photoDealDeux").value(hasItem(DEFAULT_PHOTO_DEAL_DEUX)))
            .andExpect(jsonPath("$.[*].photoDealTrois").value(hasItem(DEFAULT_PHOTO_DEAL_TROIS)))
            .andExpect(jsonPath("$.[*].photoDealQuatre").value(hasItem(DEFAULT_PHOTO_DEAL_QUATRE)))
            .andExpect(jsonPath("$.[*].photoDealCinq").value(hasItem(DEFAULT_PHOTO_DEAL_CINQ)))
            .andExpect(jsonPath("$.[*].photoDealSix").value(hasItem(DEFAULT_PHOTO_DEAL_SIX)))
            .andExpect(jsonPath("$.[*].photoDealSpet").value(hasItem(DEFAULT_PHOTO_DEAL_SPET)))
            .andExpect(jsonPath("$.[*].photoDealHuit").value(hasItem(DEFAULT_PHOTO_DEAL_HUIT)))
            .andExpect(jsonPath("$.[*].photoDealNeuf").value(hasItem(DEFAULT_PHOTO_DEAL_NEUF)))
            .andExpect(jsonPath("$.[*].photoDealDix").value(hasItem(DEFAULT_PHOTO_DEAL_DIX)))
            .andExpect(jsonPath("$.[*].photoMinDealUn").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_UN)))
            .andExpect(jsonPath("$.[*].photoMinDealDeux").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_DEUX)))
            .andExpect(jsonPath("$.[*].photoMinDealTrois").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_TROIS)))
            .andExpect(jsonPath("$.[*].photoMinDealQuatre").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_QUATRE)))
            .andExpect(jsonPath("$.[*].photoMinDealCinq").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_CINQ)))
            .andExpect(jsonPath("$.[*].photoMinDealSix").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_SIX)))
            .andExpect(jsonPath("$.[*].photoMinDealSpet").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_SPET)))
            .andExpect(jsonPath("$.[*].photoMinDealHuit").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_HUIT)))
            .andExpect(jsonPath("$.[*].photoMinDealNeuf").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_NEUF)))
            .andExpect(jsonPath("$.[*].photoMinDealDix").value(hasItem(DEFAULT_PHOTO_MIN_DEAL_DIX)))
            .andExpect(jsonPath("$.[*].descPointFortDeal").value(hasItem(DEFAULT_DESC_POINT_FORT_DEAL)))
            .andExpect(jsonPath("$.[*].detailsOffreDeal").value(hasItem(DEFAULT_DETAILS_OFFRE_DEAL)))
            .andExpect(jsonPath("$.[*].conditionsDeal").value(hasItem(DEFAULT_CONDITIONS_DEAL)))
            .andExpect(jsonPath("$.[*].estLimite").value(hasItem(DEFAULT_EST_LIMITE.booleanValue())))
            .andExpect(jsonPath("$.[*].estEpuise").value(hasItem(DEFAULT_EST_EPUISE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreationDeal").value(hasItem(DEFAULT_DATE_CREATION_DEAL.toString())))
            .andExpect(jsonPath("$.[*].dateClotureDeal").value(hasItem(DEFAULT_DATE_CLOTURE_DEAL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deal.class);
        Deal deal1 = new Deal();
        deal1.setId(1L);
        Deal deal2 = new Deal();
        deal2.setId(deal1.getId());
        assertThat(deal1).isEqualTo(deal2);
        deal2.setId(2L);
        assertThat(deal1).isNotEqualTo(deal2);
        deal1.setId(null);
        assertThat(deal1).isNotEqualTo(deal2);
    }
}

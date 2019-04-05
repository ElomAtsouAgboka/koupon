package tg.opentechconsult.koupon.web.rest;

import tg.opentechconsult.koupon.KouponApp;

import tg.opentechconsult.koupon.domain.OptionDeal;
import tg.opentechconsult.koupon.repository.OptionDealRepository;
import tg.opentechconsult.koupon.repository.search.OptionDealSearchRepository;
import tg.opentechconsult.koupon.service.OptionDealService;
import tg.opentechconsult.koupon.web.rest.errors.ExceptionTranslator;

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
 * Test class for the OptionDealResource REST controller.
 *
 * @see OptionDealResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouponApp.class)
public class OptionDealResourceIntTest {

    private static final String DEFAULT_LIB_OPTION_DEAL = "AAAAAAAAAA";
    private static final String UPDATED_LIB_OPTION_DEAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIX_NORMAL_OPTION_DEAL = 1;
    private static final Integer UPDATED_PRIX_NORMAL_OPTION_DEAL = 2;

    private static final Integer DEFAULT_PRIX_REDUCTION_OPTION_BON_PLAN = 1;
    private static final Integer UPDATED_PRIX_REDUCTION_OPTION_BON_PLAN = 2;

    private static final Integer DEFAULT_PC_REDUCTION_OPTION_BON_PLAN = 1;
    private static final Integer UPDATED_PC_REDUCTION_OPTION_BON_PLAN = 2;

    @Autowired
    private OptionDealRepository optionDealRepository;

    @Autowired
    private OptionDealService optionDealService;

    /**
     * This repository is mocked in the tg.opentechconsult.koupon.repository.search test package.
     *
     * @see tg.opentechconsult.koupon.repository.search.OptionDealSearchRepositoryMockConfiguration
     */
    @Autowired
    private OptionDealSearchRepository mockOptionDealSearchRepository;

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

    private MockMvc restOptionDealMockMvc;

    private OptionDeal optionDeal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OptionDealResource optionDealResource = new OptionDealResource(optionDealService);
        this.restOptionDealMockMvc = MockMvcBuilders.standaloneSetup(optionDealResource)
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
    public static OptionDeal createEntity(EntityManager em) {
        OptionDeal optionDeal = new OptionDeal()
            .libOptionDeal(DEFAULT_LIB_OPTION_DEAL)
            .prixNormalOptionDeal(DEFAULT_PRIX_NORMAL_OPTION_DEAL)
            .prixReductionOptionBonPlan(DEFAULT_PRIX_REDUCTION_OPTION_BON_PLAN)
            .pcReductionOptionBonPlan(DEFAULT_PC_REDUCTION_OPTION_BON_PLAN);
        return optionDeal;
    }

    @Before
    public void initTest() {
        optionDeal = createEntity(em);
    }

    @Test
    @Transactional
    public void createOptionDeal() throws Exception {
        int databaseSizeBeforeCreate = optionDealRepository.findAll().size();

        // Create the OptionDeal
        restOptionDealMockMvc.perform(post("/api/option-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionDeal)))
            .andExpect(status().isCreated());

        // Validate the OptionDeal in the database
        List<OptionDeal> optionDealList = optionDealRepository.findAll();
        assertThat(optionDealList).hasSize(databaseSizeBeforeCreate + 1);
        OptionDeal testOptionDeal = optionDealList.get(optionDealList.size() - 1);
        assertThat(testOptionDeal.getLibOptionDeal()).isEqualTo(DEFAULT_LIB_OPTION_DEAL);
        assertThat(testOptionDeal.getPrixNormalOptionDeal()).isEqualTo(DEFAULT_PRIX_NORMAL_OPTION_DEAL);
        assertThat(testOptionDeal.getPrixReductionOptionBonPlan()).isEqualTo(DEFAULT_PRIX_REDUCTION_OPTION_BON_PLAN);
        assertThat(testOptionDeal.getPcReductionOptionBonPlan()).isEqualTo(DEFAULT_PC_REDUCTION_OPTION_BON_PLAN);

        // Validate the OptionDeal in Elasticsearch
        verify(mockOptionDealSearchRepository, times(1)).save(testOptionDeal);
    }

    @Test
    @Transactional
    public void createOptionDealWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optionDealRepository.findAll().size();

        // Create the OptionDeal with an existing ID
        optionDeal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionDealMockMvc.perform(post("/api/option-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionDeal)))
            .andExpect(status().isBadRequest());

        // Validate the OptionDeal in the database
        List<OptionDeal> optionDealList = optionDealRepository.findAll();
        assertThat(optionDealList).hasSize(databaseSizeBeforeCreate);

        // Validate the OptionDeal in Elasticsearch
        verify(mockOptionDealSearchRepository, times(0)).save(optionDeal);
    }

    @Test
    @Transactional
    public void getAllOptionDeals() throws Exception {
        // Initialize the database
        optionDealRepository.saveAndFlush(optionDeal);

        // Get all the optionDealList
        restOptionDealMockMvc.perform(get("/api/option-deals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionDeal.getId().intValue())))
            .andExpect(jsonPath("$.[*].libOptionDeal").value(hasItem(DEFAULT_LIB_OPTION_DEAL.toString())))
            .andExpect(jsonPath("$.[*].prixNormalOptionDeal").value(hasItem(DEFAULT_PRIX_NORMAL_OPTION_DEAL)))
            .andExpect(jsonPath("$.[*].prixReductionOptionBonPlan").value(hasItem(DEFAULT_PRIX_REDUCTION_OPTION_BON_PLAN)))
            .andExpect(jsonPath("$.[*].pcReductionOptionBonPlan").value(hasItem(DEFAULT_PC_REDUCTION_OPTION_BON_PLAN)));
    }
    
    @Test
    @Transactional
    public void getOptionDeal() throws Exception {
        // Initialize the database
        optionDealRepository.saveAndFlush(optionDeal);

        // Get the optionDeal
        restOptionDealMockMvc.perform(get("/api/option-deals/{id}", optionDeal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(optionDeal.getId().intValue()))
            .andExpect(jsonPath("$.libOptionDeal").value(DEFAULT_LIB_OPTION_DEAL.toString()))
            .andExpect(jsonPath("$.prixNormalOptionDeal").value(DEFAULT_PRIX_NORMAL_OPTION_DEAL))
            .andExpect(jsonPath("$.prixReductionOptionBonPlan").value(DEFAULT_PRIX_REDUCTION_OPTION_BON_PLAN))
            .andExpect(jsonPath("$.pcReductionOptionBonPlan").value(DEFAULT_PC_REDUCTION_OPTION_BON_PLAN));
    }

    @Test
    @Transactional
    public void getNonExistingOptionDeal() throws Exception {
        // Get the optionDeal
        restOptionDealMockMvc.perform(get("/api/option-deals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOptionDeal() throws Exception {
        // Initialize the database
        optionDealService.save(optionDeal);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockOptionDealSearchRepository);

        int databaseSizeBeforeUpdate = optionDealRepository.findAll().size();

        // Update the optionDeal
        OptionDeal updatedOptionDeal = optionDealRepository.findById(optionDeal.getId()).get();
        // Disconnect from session so that the updates on updatedOptionDeal are not directly saved in db
        em.detach(updatedOptionDeal);
        updatedOptionDeal
            .libOptionDeal(UPDATED_LIB_OPTION_DEAL)
            .prixNormalOptionDeal(UPDATED_PRIX_NORMAL_OPTION_DEAL)
            .prixReductionOptionBonPlan(UPDATED_PRIX_REDUCTION_OPTION_BON_PLAN)
            .pcReductionOptionBonPlan(UPDATED_PC_REDUCTION_OPTION_BON_PLAN);

        restOptionDealMockMvc.perform(put("/api/option-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOptionDeal)))
            .andExpect(status().isOk());

        // Validate the OptionDeal in the database
        List<OptionDeal> optionDealList = optionDealRepository.findAll();
        assertThat(optionDealList).hasSize(databaseSizeBeforeUpdate);
        OptionDeal testOptionDeal = optionDealList.get(optionDealList.size() - 1);
        assertThat(testOptionDeal.getLibOptionDeal()).isEqualTo(UPDATED_LIB_OPTION_DEAL);
        assertThat(testOptionDeal.getPrixNormalOptionDeal()).isEqualTo(UPDATED_PRIX_NORMAL_OPTION_DEAL);
        assertThat(testOptionDeal.getPrixReductionOptionBonPlan()).isEqualTo(UPDATED_PRIX_REDUCTION_OPTION_BON_PLAN);
        assertThat(testOptionDeal.getPcReductionOptionBonPlan()).isEqualTo(UPDATED_PC_REDUCTION_OPTION_BON_PLAN);

        // Validate the OptionDeal in Elasticsearch
        verify(mockOptionDealSearchRepository, times(1)).save(testOptionDeal);
    }

    @Test
    @Transactional
    public void updateNonExistingOptionDeal() throws Exception {
        int databaseSizeBeforeUpdate = optionDealRepository.findAll().size();

        // Create the OptionDeal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionDealMockMvc.perform(put("/api/option-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionDeal)))
            .andExpect(status().isBadRequest());

        // Validate the OptionDeal in the database
        List<OptionDeal> optionDealList = optionDealRepository.findAll();
        assertThat(optionDealList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OptionDeal in Elasticsearch
        verify(mockOptionDealSearchRepository, times(0)).save(optionDeal);
    }

    @Test
    @Transactional
    public void deleteOptionDeal() throws Exception {
        // Initialize the database
        optionDealService.save(optionDeal);

        int databaseSizeBeforeDelete = optionDealRepository.findAll().size();

        // Delete the optionDeal
        restOptionDealMockMvc.perform(delete("/api/option-deals/{id}", optionDeal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OptionDeal> optionDealList = optionDealRepository.findAll();
        assertThat(optionDealList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OptionDeal in Elasticsearch
        verify(mockOptionDealSearchRepository, times(1)).deleteById(optionDeal.getId());
    }

    @Test
    @Transactional
    public void searchOptionDeal() throws Exception {
        // Initialize the database
        optionDealService.save(optionDeal);
        when(mockOptionDealSearchRepository.search(queryStringQuery("id:" + optionDeal.getId())))
            .thenReturn(Collections.singletonList(optionDeal));
        // Search the optionDeal
        restOptionDealMockMvc.perform(get("/api/_search/option-deals?query=id:" + optionDeal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionDeal.getId().intValue())))
            .andExpect(jsonPath("$.[*].libOptionDeal").value(hasItem(DEFAULT_LIB_OPTION_DEAL)))
            .andExpect(jsonPath("$.[*].prixNormalOptionDeal").value(hasItem(DEFAULT_PRIX_NORMAL_OPTION_DEAL)))
            .andExpect(jsonPath("$.[*].prixReductionOptionBonPlan").value(hasItem(DEFAULT_PRIX_REDUCTION_OPTION_BON_PLAN)))
            .andExpect(jsonPath("$.[*].pcReductionOptionBonPlan").value(hasItem(DEFAULT_PC_REDUCTION_OPTION_BON_PLAN)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionDeal.class);
        OptionDeal optionDeal1 = new OptionDeal();
        optionDeal1.setId(1L);
        OptionDeal optionDeal2 = new OptionDeal();
        optionDeal2.setId(optionDeal1.getId());
        assertThat(optionDeal1).isEqualTo(optionDeal2);
        optionDeal2.setId(2L);
        assertThat(optionDeal1).isNotEqualTo(optionDeal2);
        optionDeal1.setId(null);
        assertThat(optionDeal1).isNotEqualTo(optionDeal2);
    }
}

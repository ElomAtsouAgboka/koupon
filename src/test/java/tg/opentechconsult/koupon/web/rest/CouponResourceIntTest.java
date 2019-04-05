package tg.opentechconsult.koupon.web.rest;

import tg.opentechconsult.koupon.KouponApp;

import tg.opentechconsult.koupon.domain.Coupon;
import tg.opentechconsult.koupon.repository.CouponRepository;
import tg.opentechconsult.koupon.repository.search.CouponSearchRepository;
import tg.opentechconsult.koupon.service.CouponService;
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
 * Test class for the CouponResource REST controller.
 *
 * @see CouponResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouponApp.class)
public class CouponResourceIntTest {

    private static final String DEFAULT_REF_COUPON = "AAAAAAAAAA";
    private static final String UPDATED_REF_COUPON = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_ACHAT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_ACHAT = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_UTILISATION = "AAAAAAAAAA";
    private static final String UPDATED_DATE_UTILISATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EST_CADEAUX = false;
    private static final Boolean UPDATED_EST_CADEAUX = true;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponService couponService;

    /**
     * This repository is mocked in the tg.opentechconsult.koupon.repository.search test package.
     *
     * @see tg.opentechconsult.koupon.repository.search.CouponSearchRepositoryMockConfiguration
     */
    @Autowired
    private CouponSearchRepository mockCouponSearchRepository;

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

    private MockMvc restCouponMockMvc;

    private Coupon coupon;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CouponResource couponResource = new CouponResource(couponService);
        this.restCouponMockMvc = MockMvcBuilders.standaloneSetup(couponResource)
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
    public static Coupon createEntity(EntityManager em) {
        Coupon coupon = new Coupon()
            .refCoupon(DEFAULT_REF_COUPON)
            .dateAchat(DEFAULT_DATE_ACHAT)
            .dateUtilisation(DEFAULT_DATE_UTILISATION)
            .estCadeaux(DEFAULT_EST_CADEAUX);
        return coupon;
    }

    @Before
    public void initTest() {
        coupon = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoupon() throws Exception {
        int databaseSizeBeforeCreate = couponRepository.findAll().size();

        // Create the Coupon
        restCouponMockMvc.perform(post("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coupon)))
            .andExpect(status().isCreated());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeCreate + 1);
        Coupon testCoupon = couponList.get(couponList.size() - 1);
        assertThat(testCoupon.getRefCoupon()).isEqualTo(DEFAULT_REF_COUPON);
        assertThat(testCoupon.getDateAchat()).isEqualTo(DEFAULT_DATE_ACHAT);
        assertThat(testCoupon.getDateUtilisation()).isEqualTo(DEFAULT_DATE_UTILISATION);
        assertThat(testCoupon.isEstCadeaux()).isEqualTo(DEFAULT_EST_CADEAUX);

        // Validate the Coupon in Elasticsearch
        verify(mockCouponSearchRepository, times(1)).save(testCoupon);
    }

    @Test
    @Transactional
    public void createCouponWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = couponRepository.findAll().size();

        // Create the Coupon with an existing ID
        coupon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCouponMockMvc.perform(post("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coupon)))
            .andExpect(status().isBadRequest());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeCreate);

        // Validate the Coupon in Elasticsearch
        verify(mockCouponSearchRepository, times(0)).save(coupon);
    }

    @Test
    @Transactional
    public void checkRefCouponIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponRepository.findAll().size();
        // set the field null
        coupon.setRefCoupon(null);

        // Create the Coupon, which fails.

        restCouponMockMvc.perform(post("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coupon)))
            .andExpect(status().isBadRequest());

        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoupons() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        // Get all the couponList
        restCouponMockMvc.perform(get("/api/coupons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coupon.getId().intValue())))
            .andExpect(jsonPath("$.[*].refCoupon").value(hasItem(DEFAULT_REF_COUPON.toString())))
            .andExpect(jsonPath("$.[*].dateAchat").value(hasItem(DEFAULT_DATE_ACHAT.toString())))
            .andExpect(jsonPath("$.[*].dateUtilisation").value(hasItem(DEFAULT_DATE_UTILISATION.toString())))
            .andExpect(jsonPath("$.[*].estCadeaux").value(hasItem(DEFAULT_EST_CADEAUX.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCoupon() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        // Get the coupon
        restCouponMockMvc.perform(get("/api/coupons/{id}", coupon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coupon.getId().intValue()))
            .andExpect(jsonPath("$.refCoupon").value(DEFAULT_REF_COUPON.toString()))
            .andExpect(jsonPath("$.dateAchat").value(DEFAULT_DATE_ACHAT.toString()))
            .andExpect(jsonPath("$.dateUtilisation").value(DEFAULT_DATE_UTILISATION.toString()))
            .andExpect(jsonPath("$.estCadeaux").value(DEFAULT_EST_CADEAUX.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoupon() throws Exception {
        // Get the coupon
        restCouponMockMvc.perform(get("/api/coupons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoupon() throws Exception {
        // Initialize the database
        couponService.save(coupon);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCouponSearchRepository);

        int databaseSizeBeforeUpdate = couponRepository.findAll().size();

        // Update the coupon
        Coupon updatedCoupon = couponRepository.findById(coupon.getId()).get();
        // Disconnect from session so that the updates on updatedCoupon are not directly saved in db
        em.detach(updatedCoupon);
        updatedCoupon
            .refCoupon(UPDATED_REF_COUPON)
            .dateAchat(UPDATED_DATE_ACHAT)
            .dateUtilisation(UPDATED_DATE_UTILISATION)
            .estCadeaux(UPDATED_EST_CADEAUX);

        restCouponMockMvc.perform(put("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCoupon)))
            .andExpect(status().isOk());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeUpdate);
        Coupon testCoupon = couponList.get(couponList.size() - 1);
        assertThat(testCoupon.getRefCoupon()).isEqualTo(UPDATED_REF_COUPON);
        assertThat(testCoupon.getDateAchat()).isEqualTo(UPDATED_DATE_ACHAT);
        assertThat(testCoupon.getDateUtilisation()).isEqualTo(UPDATED_DATE_UTILISATION);
        assertThat(testCoupon.isEstCadeaux()).isEqualTo(UPDATED_EST_CADEAUX);

        // Validate the Coupon in Elasticsearch
        verify(mockCouponSearchRepository, times(1)).save(testCoupon);
    }

    @Test
    @Transactional
    public void updateNonExistingCoupon() throws Exception {
        int databaseSizeBeforeUpdate = couponRepository.findAll().size();

        // Create the Coupon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCouponMockMvc.perform(put("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coupon)))
            .andExpect(status().isBadRequest());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Coupon in Elasticsearch
        verify(mockCouponSearchRepository, times(0)).save(coupon);
    }

    @Test
    @Transactional
    public void deleteCoupon() throws Exception {
        // Initialize the database
        couponService.save(coupon);

        int databaseSizeBeforeDelete = couponRepository.findAll().size();

        // Delete the coupon
        restCouponMockMvc.perform(delete("/api/coupons/{id}", coupon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Coupon in Elasticsearch
        verify(mockCouponSearchRepository, times(1)).deleteById(coupon.getId());
    }

    @Test
    @Transactional
    public void searchCoupon() throws Exception {
        // Initialize the database
        couponService.save(coupon);
        when(mockCouponSearchRepository.search(queryStringQuery("id:" + coupon.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(coupon), PageRequest.of(0, 1), 1));
        // Search the coupon
        restCouponMockMvc.perform(get("/api/_search/coupons?query=id:" + coupon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coupon.getId().intValue())))
            .andExpect(jsonPath("$.[*].refCoupon").value(hasItem(DEFAULT_REF_COUPON)))
            .andExpect(jsonPath("$.[*].dateAchat").value(hasItem(DEFAULT_DATE_ACHAT)))
            .andExpect(jsonPath("$.[*].dateUtilisation").value(hasItem(DEFAULT_DATE_UTILISATION)))
            .andExpect(jsonPath("$.[*].estCadeaux").value(hasItem(DEFAULT_EST_CADEAUX.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coupon.class);
        Coupon coupon1 = new Coupon();
        coupon1.setId(1L);
        Coupon coupon2 = new Coupon();
        coupon2.setId(coupon1.getId());
        assertThat(coupon1).isEqualTo(coupon2);
        coupon2.setId(2L);
        assertThat(coupon1).isNotEqualTo(coupon2);
        coupon1.setId(null);
        assertThat(coupon1).isNotEqualTo(coupon2);
    }
}

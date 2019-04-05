package tg.opentechconsult.koupon.service.impl;

import tg.opentechconsult.koupon.service.CouponService;
import tg.opentechconsult.koupon.domain.Coupon;
import tg.opentechconsult.koupon.repository.CouponRepository;
import tg.opentechconsult.koupon.repository.search.CouponSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Coupon.
 */
@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    private final Logger log = LoggerFactory.getLogger(CouponServiceImpl.class);

    private final CouponRepository couponRepository;

    private final CouponSearchRepository couponSearchRepository;

    public CouponServiceImpl(CouponRepository couponRepository, CouponSearchRepository couponSearchRepository) {
        this.couponRepository = couponRepository;
        this.couponSearchRepository = couponSearchRepository;
    }

    /**
     * Save a coupon.
     *
     * @param coupon the entity to save
     * @return the persisted entity
     */
    @Override
    public Coupon save(Coupon coupon) {
        log.debug("Request to save Coupon : {}", coupon);
        Coupon result = couponRepository.save(coupon);
        couponSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the coupons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Coupon> findAll(Pageable pageable) {
        log.debug("Request to get all Coupons");
        return couponRepository.findAll(pageable);
    }


    /**
     * Get one coupon by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Coupon> findOne(Long id) {
        log.debug("Request to get Coupon : {}", id);
        return couponRepository.findById(id);
    }

    /**
     * Delete the coupon by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coupon : {}", id);
        couponRepository.deleteById(id);
        couponSearchRepository.deleteById(id);
    }

    /**
     * Search for the coupon corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Coupon> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Coupons for query {}", query);
        return couponSearchRepository.search(queryStringQuery(query), pageable);    }
}

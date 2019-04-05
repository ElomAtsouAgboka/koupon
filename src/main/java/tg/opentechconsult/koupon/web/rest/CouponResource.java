package tg.opentechconsult.koupon.web.rest;
import tg.opentechconsult.koupon.domain.Coupon;
import tg.opentechconsult.koupon.service.CouponService;
import tg.opentechconsult.koupon.web.rest.errors.BadRequestAlertException;
import tg.opentechconsult.koupon.web.rest.util.HeaderUtil;
import tg.opentechconsult.koupon.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Coupon.
 */
@RestController
@RequestMapping("/api")
public class CouponResource {

    private final Logger log = LoggerFactory.getLogger(CouponResource.class);

    private static final String ENTITY_NAME = "coupon";

    private final CouponService couponService;

    public CouponResource(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     * POST  /coupons : Create a new coupon.
     *
     * @param coupon the coupon to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coupon, or with status 400 (Bad Request) if the coupon has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coupons")
    public ResponseEntity<Coupon> createCoupon(@Valid @RequestBody Coupon coupon) throws URISyntaxException {
        log.debug("REST request to save Coupon : {}", coupon);
        if (coupon.getId() != null) {
            throw new BadRequestAlertException("A new coupon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Coupon result = couponService.save(coupon);
        return ResponseEntity.created(new URI("/api/coupons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coupons : Updates an existing coupon.
     *
     * @param coupon the coupon to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coupon,
     * or with status 400 (Bad Request) if the coupon is not valid,
     * or with status 500 (Internal Server Error) if the coupon couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coupons")
    public ResponseEntity<Coupon> updateCoupon(@Valid @RequestBody Coupon coupon) throws URISyntaxException {
        log.debug("REST request to update Coupon : {}", coupon);
        if (coupon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Coupon result = couponService.save(coupon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coupon.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coupons : get all the coupons.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of coupons in body
     */
    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(Pageable pageable) {
        log.debug("REST request to get a page of Coupons");
        Page<Coupon> page = couponService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/coupons");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /coupons/:id : get the "id" coupon.
     *
     * @param id the id of the coupon to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coupon, or with status 404 (Not Found)
     */
    @GetMapping("/coupons/{id}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable Long id) {
        log.debug("REST request to get Coupon : {}", id);
        Optional<Coupon> coupon = couponService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coupon);
    }

    /**
     * DELETE  /coupons/:id : delete the "id" coupon.
     *
     * @param id the id of the coupon to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coupons/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        log.debug("REST request to delete Coupon : {}", id);
        couponService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/coupons?query=:query : search for the coupon corresponding
     * to the query.
     *
     * @param query the query of the coupon search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/coupons")
    public ResponseEntity<List<Coupon>> searchCoupons(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Coupons for query {}", query);
        Page<Coupon> page = couponService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/coupons");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}

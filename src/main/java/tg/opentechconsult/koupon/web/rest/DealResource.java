package tg.opentechconsult.koupon.web.rest;
import tg.opentechconsult.koupon.domain.Deal;
import tg.opentechconsult.koupon.service.DealService;
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
 * REST controller for managing Deal.
 */
@RestController
@RequestMapping("/api")
public class DealResource {

    private final Logger log = LoggerFactory.getLogger(DealResource.class);

    private static final String ENTITY_NAME = "deal";

    private final DealService dealService;

    public DealResource(DealService dealService) {
        this.dealService = dealService;
    }

    /**
     * POST  /deals : Create a new deal.
     *
     * @param deal the deal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deal, or with status 400 (Bad Request) if the deal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deals")
    public ResponseEntity<Deal> createDeal(@Valid @RequestBody Deal deal) throws URISyntaxException {
        log.debug("REST request to save Deal : {}", deal);
        if (deal.getId() != null) {
            throw new BadRequestAlertException("A new deal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Deal result = dealService.save(deal);
        return ResponseEntity.created(new URI("/api/deals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deals : Updates an existing deal.
     *
     * @param deal the deal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deal,
     * or with status 400 (Bad Request) if the deal is not valid,
     * or with status 500 (Internal Server Error) if the deal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deals")
    public ResponseEntity<Deal> updateDeal(@Valid @RequestBody Deal deal) throws URISyntaxException {
        log.debug("REST request to update Deal : {}", deal);
        if (deal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Deal result = dealService.save(deal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deals : get all the deals.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of deals in body
     */
    @GetMapping("/deals")
    public ResponseEntity<List<Deal>> getAllDeals(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Deals");
        Page<Deal> page;
        if (eagerload) {
            page = dealService.findAllWithEagerRelationships(pageable);
        } else {
            page = dealService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/deals?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /deals/:id : get the "id" deal.
     *
     * @param id the id of the deal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deal, or with status 404 (Not Found)
     */
    @GetMapping("/deals/{id}")
    public ResponseEntity<Deal> getDeal(@PathVariable Long id) {
        log.debug("REST request to get Deal : {}", id);
        Optional<Deal> deal = dealService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deal);
    }

    /**
     * DELETE  /deals/:id : delete the "id" deal.
     *
     * @param id the id of the deal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deals/{id}")
    public ResponseEntity<Void> deleteDeal(@PathVariable Long id) {
        log.debug("REST request to delete Deal : {}", id);
        dealService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/deals?query=:query : search for the deal corresponding
     * to the query.
     *
     * @param query the query of the deal search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/deals")
    public ResponseEntity<List<Deal>> searchDeals(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Deals for query {}", query);
        Page<Deal> page = dealService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/deals");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}

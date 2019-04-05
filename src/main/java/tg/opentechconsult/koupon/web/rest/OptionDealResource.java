package tg.opentechconsult.koupon.web.rest;
import tg.opentechconsult.koupon.domain.OptionDeal;
import tg.opentechconsult.koupon.service.OptionDealService;
import tg.opentechconsult.koupon.web.rest.errors.BadRequestAlertException;
import tg.opentechconsult.koupon.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing OptionDeal.
 */
@RestController
@RequestMapping("/api")
public class OptionDealResource {

    private final Logger log = LoggerFactory.getLogger(OptionDealResource.class);

    private static final String ENTITY_NAME = "optionDeal";

    private final OptionDealService optionDealService;

    public OptionDealResource(OptionDealService optionDealService) {
        this.optionDealService = optionDealService;
    }

    /**
     * POST  /option-deals : Create a new optionDeal.
     *
     * @param optionDeal the optionDeal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new optionDeal, or with status 400 (Bad Request) if the optionDeal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/option-deals")
    public ResponseEntity<OptionDeal> createOptionDeal(@RequestBody OptionDeal optionDeal) throws URISyntaxException {
        log.debug("REST request to save OptionDeal : {}", optionDeal);
        if (optionDeal.getId() != null) {
            throw new BadRequestAlertException("A new optionDeal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptionDeal result = optionDealService.save(optionDeal);
        return ResponseEntity.created(new URI("/api/option-deals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /option-deals : Updates an existing optionDeal.
     *
     * @param optionDeal the optionDeal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated optionDeal,
     * or with status 400 (Bad Request) if the optionDeal is not valid,
     * or with status 500 (Internal Server Error) if the optionDeal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/option-deals")
    public ResponseEntity<OptionDeal> updateOptionDeal(@RequestBody OptionDeal optionDeal) throws URISyntaxException {
        log.debug("REST request to update OptionDeal : {}", optionDeal);
        if (optionDeal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OptionDeal result = optionDealService.save(optionDeal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, optionDeal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /option-deals : get all the optionDeals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of optionDeals in body
     */
    @GetMapping("/option-deals")
    public List<OptionDeal> getAllOptionDeals() {
        log.debug("REST request to get all OptionDeals");
        return optionDealService.findAll();
    }

    /**
     * GET  /option-deals/:id : get the "id" optionDeal.
     *
     * @param id the id of the optionDeal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the optionDeal, or with status 404 (Not Found)
     */
    @GetMapping("/option-deals/{id}")
    public ResponseEntity<OptionDeal> getOptionDeal(@PathVariable Long id) {
        log.debug("REST request to get OptionDeal : {}", id);
        Optional<OptionDeal> optionDeal = optionDealService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionDeal);
    }

    /**
     * DELETE  /option-deals/:id : delete the "id" optionDeal.
     *
     * @param id the id of the optionDeal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/option-deals/{id}")
    public ResponseEntity<Void> deleteOptionDeal(@PathVariable Long id) {
        log.debug("REST request to delete OptionDeal : {}", id);
        optionDealService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/option-deals?query=:query : search for the optionDeal corresponding
     * to the query.
     *
     * @param query the query of the optionDeal search
     * @return the result of the search
     */
    @GetMapping("/_search/option-deals")
    public List<OptionDeal> searchOptionDeals(@RequestParam String query) {
        log.debug("REST request to search OptionDeals for query {}", query);
        return optionDealService.search(query);
    }

}

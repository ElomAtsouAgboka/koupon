package tg.opentechconsult.koupon.web.rest;
import tg.opentechconsult.koupon.domain.Avis;
import tg.opentechconsult.koupon.service.AvisService;
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
 * REST controller for managing Avis.
 */
@RestController
@RequestMapping("/api")
public class AvisResource {

    private final Logger log = LoggerFactory.getLogger(AvisResource.class);

    private static final String ENTITY_NAME = "avis";

    private final AvisService avisService;

    public AvisResource(AvisService avisService) {
        this.avisService = avisService;
    }

    /**
     * POST  /avis : Create a new avis.
     *
     * @param avis the avis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new avis, or with status 400 (Bad Request) if the avis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/avis")
    public ResponseEntity<Avis> createAvis(@Valid @RequestBody Avis avis) throws URISyntaxException {
        log.debug("REST request to save Avis : {}", avis);
        if (avis.getId() != null) {
            throw new BadRequestAlertException("A new avis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Avis result = avisService.save(avis);
        return ResponseEntity.created(new URI("/api/avis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /avis : Updates an existing avis.
     *
     * @param avis the avis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated avis,
     * or with status 400 (Bad Request) if the avis is not valid,
     * or with status 500 (Internal Server Error) if the avis couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/avis")
    public ResponseEntity<Avis> updateAvis(@Valid @RequestBody Avis avis) throws URISyntaxException {
        log.debug("REST request to update Avis : {}", avis);
        if (avis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Avis result = avisService.save(avis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, avis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /avis : get all the avis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of avis in body
     */
    @GetMapping("/avis")
    public ResponseEntity<List<Avis>> getAllAvis(Pageable pageable) {
        log.debug("REST request to get a page of Avis");
        Page<Avis> page = avisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/avis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /avis/:id : get the "id" avis.
     *
     * @param id the id of the avis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the avis, or with status 404 (Not Found)
     */
    @GetMapping("/avis/{id}")
    public ResponseEntity<Avis> getAvis(@PathVariable Long id) {
        log.debug("REST request to get Avis : {}", id);
        Optional<Avis> avis = avisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avis);
    }

    /**
     * DELETE  /avis/:id : delete the "id" avis.
     *
     * @param id the id of the avis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/avis/{id}")
    public ResponseEntity<Void> deleteAvis(@PathVariable Long id) {
        log.debug("REST request to delete Avis : {}", id);
        avisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/avis?query=:query : search for the avis corresponding
     * to the query.
     *
     * @param query the query of the avis search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/avis")
    public ResponseEntity<List<Avis>> searchAvis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Avis for query {}", query);
        Page<Avis> page = avisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/avis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}

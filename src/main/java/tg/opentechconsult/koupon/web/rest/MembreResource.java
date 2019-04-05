package tg.opentechconsult.koupon.web.rest;
import tg.opentechconsult.koupon.domain.Membre;
import tg.opentechconsult.koupon.service.MembreService;
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
 * REST controller for managing Membre.
 */
@RestController
@RequestMapping("/api")
public class MembreResource {

    private final Logger log = LoggerFactory.getLogger(MembreResource.class);

    private static final String ENTITY_NAME = "membre";

    private final MembreService membreService;

    public MembreResource(MembreService membreService) {
        this.membreService = membreService;
    }

    /**
     * POST  /membres : Create a new membre.
     *
     * @param membre the membre to create
     * @return the ResponseEntity with status 201 (Created) and with body the new membre, or with status 400 (Bad Request) if the membre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/membres")
    public ResponseEntity<Membre> createMembre(@Valid @RequestBody Membre membre) throws URISyntaxException {
        log.debug("REST request to save Membre : {}", membre);
        if (membre.getId() != null) {
            throw new BadRequestAlertException("A new membre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Membre result = membreService.save(membre);
        return ResponseEntity.created(new URI("/api/membres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /membres : Updates an existing membre.
     *
     * @param membre the membre to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated membre,
     * or with status 400 (Bad Request) if the membre is not valid,
     * or with status 500 (Internal Server Error) if the membre couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/membres")
    public ResponseEntity<Membre> updateMembre(@Valid @RequestBody Membre membre) throws URISyntaxException {
        log.debug("REST request to update Membre : {}", membre);
        if (membre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Membre result = membreService.save(membre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, membre.getId().toString()))
            .body(result);
    }

    /**
     * GET  /membres : get all the membres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of membres in body
     */
    @GetMapping("/membres")
    public ResponseEntity<List<Membre>> getAllMembres(Pageable pageable) {
        log.debug("REST request to get a page of Membres");
        Page<Membre> page = membreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/membres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /membres/:id : get the "id" membre.
     *
     * @param id the id of the membre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the membre, or with status 404 (Not Found)
     */
    @GetMapping("/membres/{id}")
    public ResponseEntity<Membre> getMembre(@PathVariable Long id) {
        log.debug("REST request to get Membre : {}", id);
        Optional<Membre> membre = membreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membre);
    }

    /**
     * DELETE  /membres/:id : delete the "id" membre.
     *
     * @param id the id of the membre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/membres/{id}")
    public ResponseEntity<Void> deleteMembre(@PathVariable Long id) {
        log.debug("REST request to delete Membre : {}", id);
        membreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/membres?query=:query : search for the membre corresponding
     * to the query.
     *
     * @param query the query of the membre search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/membres")
    public ResponseEntity<List<Membre>> searchMembres(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Membres for query {}", query);
        Page<Membre> page = membreService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/membres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}

package tg.opentechconsult.koupon.web.rest;
import tg.opentechconsult.koupon.domain.Marchand;
import tg.opentechconsult.koupon.service.MarchandService;
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
 * REST controller for managing Marchand.
 */
@RestController
@RequestMapping("/api")
public class MarchandResource {

    private final Logger log = LoggerFactory.getLogger(MarchandResource.class);

    private static final String ENTITY_NAME = "marchand";

    private final MarchandService marchandService;

    public MarchandResource(MarchandService marchandService) {
        this.marchandService = marchandService;
    }

    /**
     * POST  /marchands : Create a new marchand.
     *
     * @param marchand the marchand to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marchand, or with status 400 (Bad Request) if the marchand has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/marchands")
    public ResponseEntity<Marchand> createMarchand(@Valid @RequestBody Marchand marchand) throws URISyntaxException {
        log.debug("REST request to save Marchand : {}", marchand);
        if (marchand.getId() != null) {
            throw new BadRequestAlertException("A new marchand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Marchand result = marchandService.save(marchand);
        return ResponseEntity.created(new URI("/api/marchands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /marchands : Updates an existing marchand.
     *
     * @param marchand the marchand to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marchand,
     * or with status 400 (Bad Request) if the marchand is not valid,
     * or with status 500 (Internal Server Error) if the marchand couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/marchands")
    public ResponseEntity<Marchand> updateMarchand(@Valid @RequestBody Marchand marchand) throws URISyntaxException {
        log.debug("REST request to update Marchand : {}", marchand);
        if (marchand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Marchand result = marchandService.save(marchand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marchand.getId().toString()))
            .body(result);
    }

    /**
     * GET  /marchands : get all the marchands.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of marchands in body
     */
    @GetMapping("/marchands")
    public ResponseEntity<List<Marchand>> getAllMarchands(Pageable pageable) {
        log.debug("REST request to get a page of Marchands");
        Page<Marchand> page = marchandService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/marchands");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /marchands/:id : get the "id" marchand.
     *
     * @param id the id of the marchand to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marchand, or with status 404 (Not Found)
     */
    @GetMapping("/marchands/{id}")
    public ResponseEntity<Marchand> getMarchand(@PathVariable Long id) {
        log.debug("REST request to get Marchand : {}", id);
        Optional<Marchand> marchand = marchandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marchand);
    }

    /**
     * DELETE  /marchands/:id : delete the "id" marchand.
     *
     * @param id the id of the marchand to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/marchands/{id}")
    public ResponseEntity<Void> deleteMarchand(@PathVariable Long id) {
        log.debug("REST request to delete Marchand : {}", id);
        marchandService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/marchands?query=:query : search for the marchand corresponding
     * to the query.
     *
     * @param query the query of the marchand search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/marchands")
    public ResponseEntity<List<Marchand>> searchMarchands(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Marchands for query {}", query);
        Page<Marchand> page = marchandService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/marchands");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}

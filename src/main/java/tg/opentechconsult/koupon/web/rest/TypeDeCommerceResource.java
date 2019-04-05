package tg.opentechconsult.koupon.web.rest;
import tg.opentechconsult.koupon.domain.TypeDeCommerce;
import tg.opentechconsult.koupon.repository.TypeDeCommerceRepository;
import tg.opentechconsult.koupon.repository.search.TypeDeCommerceSearchRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TypeDeCommerce.
 */
@RestController
@RequestMapping("/api")
public class TypeDeCommerceResource {

    private final Logger log = LoggerFactory.getLogger(TypeDeCommerceResource.class);

    private static final String ENTITY_NAME = "typeDeCommerce";

    private final TypeDeCommerceRepository typeDeCommerceRepository;

    private final TypeDeCommerceSearchRepository typeDeCommerceSearchRepository;

    public TypeDeCommerceResource(TypeDeCommerceRepository typeDeCommerceRepository, TypeDeCommerceSearchRepository typeDeCommerceSearchRepository) {
        this.typeDeCommerceRepository = typeDeCommerceRepository;
        this.typeDeCommerceSearchRepository = typeDeCommerceSearchRepository;
    }

    /**
     * POST  /type-de-commerces : Create a new typeDeCommerce.
     *
     * @param typeDeCommerce the typeDeCommerce to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeDeCommerce, or with status 400 (Bad Request) if the typeDeCommerce has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-de-commerces")
    public ResponseEntity<TypeDeCommerce> createTypeDeCommerce(@Valid @RequestBody TypeDeCommerce typeDeCommerce) throws URISyntaxException {
        log.debug("REST request to save TypeDeCommerce : {}", typeDeCommerce);
        if (typeDeCommerce.getId() != null) {
            throw new BadRequestAlertException("A new typeDeCommerce cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeDeCommerce result = typeDeCommerceRepository.save(typeDeCommerce);
        typeDeCommerceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/type-de-commerces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-de-commerces : Updates an existing typeDeCommerce.
     *
     * @param typeDeCommerce the typeDeCommerce to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeDeCommerce,
     * or with status 400 (Bad Request) if the typeDeCommerce is not valid,
     * or with status 500 (Internal Server Error) if the typeDeCommerce couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-de-commerces")
    public ResponseEntity<TypeDeCommerce> updateTypeDeCommerce(@Valid @RequestBody TypeDeCommerce typeDeCommerce) throws URISyntaxException {
        log.debug("REST request to update TypeDeCommerce : {}", typeDeCommerce);
        if (typeDeCommerce.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeDeCommerce result = typeDeCommerceRepository.save(typeDeCommerce);
        typeDeCommerceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeDeCommerce.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-de-commerces : get all the typeDeCommerces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeDeCommerces in body
     */
    @GetMapping("/type-de-commerces")
    public ResponseEntity<List<TypeDeCommerce>> getAllTypeDeCommerces(Pageable pageable) {
        log.debug("REST request to get a page of TypeDeCommerces");
        Page<TypeDeCommerce> page = typeDeCommerceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-de-commerces");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /type-de-commerces/:id : get the "id" typeDeCommerce.
     *
     * @param id the id of the typeDeCommerce to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeDeCommerce, or with status 404 (Not Found)
     */
    @GetMapping("/type-de-commerces/{id}")
    public ResponseEntity<TypeDeCommerce> getTypeDeCommerce(@PathVariable Long id) {
        log.debug("REST request to get TypeDeCommerce : {}", id);
        Optional<TypeDeCommerce> typeDeCommerce = typeDeCommerceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeDeCommerce);
    }

    /**
     * DELETE  /type-de-commerces/:id : delete the "id" typeDeCommerce.
     *
     * @param id the id of the typeDeCommerce to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-de-commerces/{id}")
    public ResponseEntity<Void> deleteTypeDeCommerce(@PathVariable Long id) {
        log.debug("REST request to delete TypeDeCommerce : {}", id);
        typeDeCommerceRepository.deleteById(id);
        typeDeCommerceSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-de-commerces?query=:query : search for the typeDeCommerce corresponding
     * to the query.
     *
     * @param query the query of the typeDeCommerce search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/type-de-commerces")
    public ResponseEntity<List<TypeDeCommerce>> searchTypeDeCommerces(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TypeDeCommerces for query {}", query);
        Page<TypeDeCommerce> page = typeDeCommerceSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/type-de-commerces");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}

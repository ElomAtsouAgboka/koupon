package tg.opentechconsult.koupon.service.impl;

import tg.opentechconsult.koupon.service.CommerceService;
import tg.opentechconsult.koupon.domain.Commerce;
import tg.opentechconsult.koupon.repository.CommerceRepository;
import tg.opentechconsult.koupon.repository.search.CommerceSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Commerce.
 */
@Service
@Transactional
public class CommerceServiceImpl implements CommerceService {

    private final Logger log = LoggerFactory.getLogger(CommerceServiceImpl.class);

    private final CommerceRepository commerceRepository;

    private final CommerceSearchRepository commerceSearchRepository;

    public CommerceServiceImpl(CommerceRepository commerceRepository, CommerceSearchRepository commerceSearchRepository) {
        this.commerceRepository = commerceRepository;
        this.commerceSearchRepository = commerceSearchRepository;
    }

    /**
     * Save a commerce.
     *
     * @param commerce the entity to save
     * @return the persisted entity
     */
    @Override
    public Commerce save(Commerce commerce) {
        log.debug("Request to save Commerce : {}", commerce);
        Commerce result = commerceRepository.save(commerce);
        commerceSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the commerce.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Commerce> findAll(Pageable pageable) {
        log.debug("Request to get all Commerce");
        return commerceRepository.findAll(pageable);
    }


    /**
     * Get one commerce by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Commerce> findOne(Long id) {
        log.debug("Request to get Commerce : {}", id);
        return commerceRepository.findById(id);
    }

    /**
     * Delete the commerce by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commerce : {}", id);
        commerceRepository.deleteById(id);
        commerceSearchRepository.deleteById(id);
    }

    /**
     * Search for the commerce corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Commerce> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Commerce for query {}", query);
        return commerceSearchRepository.search(queryStringQuery(query), pageable);    }
}

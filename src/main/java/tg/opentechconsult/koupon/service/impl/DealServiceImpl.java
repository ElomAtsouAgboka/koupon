package tg.opentechconsult.koupon.service.impl;

import tg.opentechconsult.koupon.service.DealService;
import tg.opentechconsult.koupon.domain.Deal;
import tg.opentechconsult.koupon.repository.DealRepository;
import tg.opentechconsult.koupon.repository.search.DealSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Deal.
 */
@Service
@Transactional
public class DealServiceImpl implements DealService {

    private final Logger log = LoggerFactory.getLogger(DealServiceImpl.class);

    private final DealRepository dealRepository;

    private final DealSearchRepository dealSearchRepository;

    public DealServiceImpl(DealRepository dealRepository, DealSearchRepository dealSearchRepository) {
        this.dealRepository = dealRepository;
        this.dealSearchRepository = dealSearchRepository;
    }

    /**
     * Save a deal.
     *
     * @param deal the entity to save
     * @return the persisted entity
     */
    @Override
    public Deal save(Deal deal) {
        log.debug("Request to save Deal : {}", deal);
        Deal result = dealRepository.save(deal);
        dealSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the deals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Deal> findAll(Pageable pageable) {
        log.debug("Request to get all Deals");
        return dealRepository.findAll(pageable);
    }

    /**
     * Get all the Deal with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Deal> findAllWithEagerRelationships(Pageable pageable) {
        return dealRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one deal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Deal> findOne(Long id) {
        log.debug("Request to get Deal : {}", id);
        return dealRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the deal by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Deal : {}", id);
        dealRepository.deleteById(id);
        dealSearchRepository.deleteById(id);
    }

    /**
     * Search for the deal corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Deal> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Deals for query {}", query);
        return dealSearchRepository.search(queryStringQuery(query), pageable);    }
}

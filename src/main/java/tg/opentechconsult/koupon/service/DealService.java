package tg.opentechconsult.koupon.service;

import tg.opentechconsult.koupon.domain.Deal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Deal.
 */
public interface DealService {

    /**
     * Save a deal.
     *
     * @param deal the entity to save
     * @return the persisted entity
     */
    Deal save(Deal deal);

    /**
     * Get all the deals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Deal> findAll(Pageable pageable);

    /**
     * Get all the Deal with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Deal> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" deal.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Deal> findOne(Long id);

    /**
     * Delete the "id" deal.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the deal corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Deal> search(String query, Pageable pageable);
}

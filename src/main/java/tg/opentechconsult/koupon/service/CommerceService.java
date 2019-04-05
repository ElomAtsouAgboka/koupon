package tg.opentechconsult.koupon.service;

import tg.opentechconsult.koupon.domain.Commerce;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Commerce.
 */
public interface CommerceService {

    /**
     * Save a commerce.
     *
     * @param commerce the entity to save
     * @return the persisted entity
     */
    Commerce save(Commerce commerce);

    /**
     * Get all the commerce.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Commerce> findAll(Pageable pageable);


    /**
     * Get the "id" commerce.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Commerce> findOne(Long id);

    /**
     * Delete the "id" commerce.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the commerce corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Commerce> search(String query, Pageable pageable);
}

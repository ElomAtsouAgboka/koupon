package tg.opentechconsult.koupon.service;

import tg.opentechconsult.koupon.domain.Avis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Avis.
 */
public interface AvisService {

    /**
     * Save a avis.
     *
     * @param avis the entity to save
     * @return the persisted entity
     */
    Avis save(Avis avis);

    /**
     * Get all the avis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Avis> findAll(Pageable pageable);


    /**
     * Get the "id" avis.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Avis> findOne(Long id);

    /**
     * Delete the "id" avis.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the avis corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Avis> search(String query, Pageable pageable);
}

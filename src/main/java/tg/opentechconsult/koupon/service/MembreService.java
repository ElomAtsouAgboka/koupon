package tg.opentechconsult.koupon.service;

import tg.opentechconsult.koupon.domain.Membre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Membre.
 */
public interface MembreService {

    /**
     * Save a membre.
     *
     * @param membre the entity to save
     * @return the persisted entity
     */
    Membre save(Membre membre);

    /**
     * Get all the membres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Membre> findAll(Pageable pageable);


    /**
     * Get the "id" membre.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Membre> findOne(Long id);

    /**
     * Delete the "id" membre.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the membre corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Membre> search(String query, Pageable pageable);
}

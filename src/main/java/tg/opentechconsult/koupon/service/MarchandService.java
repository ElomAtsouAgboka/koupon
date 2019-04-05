package tg.opentechconsult.koupon.service;

import tg.opentechconsult.koupon.domain.Marchand;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Marchand.
 */
public interface MarchandService {

    /**
     * Save a marchand.
     *
     * @param marchand the entity to save
     * @return the persisted entity
     */
    Marchand save(Marchand marchand);

    /**
     * Get all the marchands.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Marchand> findAll(Pageable pageable);


    /**
     * Get the "id" marchand.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Marchand> findOne(Long id);

    /**
     * Delete the "id" marchand.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the marchand corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Marchand> search(String query, Pageable pageable);
}

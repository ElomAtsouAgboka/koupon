package tg.opentechconsult.koupon.service;

import tg.opentechconsult.koupon.domain.OptionDeal;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing OptionDeal.
 */
public interface OptionDealService {

    /**
     * Save a optionDeal.
     *
     * @param optionDeal the entity to save
     * @return the persisted entity
     */
    OptionDeal save(OptionDeal optionDeal);

    /**
     * Get all the optionDeals.
     *
     * @return the list of entities
     */
    List<OptionDeal> findAll();


    /**
     * Get the "id" optionDeal.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OptionDeal> findOne(Long id);

    /**
     * Delete the "id" optionDeal.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the optionDeal corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<OptionDeal> search(String query);
}

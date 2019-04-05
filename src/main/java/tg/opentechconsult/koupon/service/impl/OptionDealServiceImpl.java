package tg.opentechconsult.koupon.service.impl;

import tg.opentechconsult.koupon.service.OptionDealService;
import tg.opentechconsult.koupon.domain.OptionDeal;
import tg.opentechconsult.koupon.repository.OptionDealRepository;
import tg.opentechconsult.koupon.repository.search.OptionDealSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OptionDeal.
 */
@Service
@Transactional
public class OptionDealServiceImpl implements OptionDealService {

    private final Logger log = LoggerFactory.getLogger(OptionDealServiceImpl.class);

    private final OptionDealRepository optionDealRepository;

    private final OptionDealSearchRepository optionDealSearchRepository;

    public OptionDealServiceImpl(OptionDealRepository optionDealRepository, OptionDealSearchRepository optionDealSearchRepository) {
        this.optionDealRepository = optionDealRepository;
        this.optionDealSearchRepository = optionDealSearchRepository;
    }

    /**
     * Save a optionDeal.
     *
     * @param optionDeal the entity to save
     * @return the persisted entity
     */
    @Override
    public OptionDeal save(OptionDeal optionDeal) {
        log.debug("Request to save OptionDeal : {}", optionDeal);
        OptionDeal result = optionDealRepository.save(optionDeal);
        optionDealSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the optionDeals.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OptionDeal> findAll() {
        log.debug("Request to get all OptionDeals");
        return optionDealRepository.findAll();
    }


    /**
     * Get one optionDeal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OptionDeal> findOne(Long id) {
        log.debug("Request to get OptionDeal : {}", id);
        return optionDealRepository.findById(id);
    }

    /**
     * Delete the optionDeal by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OptionDeal : {}", id);
        optionDealRepository.deleteById(id);
        optionDealSearchRepository.deleteById(id);
    }

    /**
     * Search for the optionDeal corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OptionDeal> search(String query) {
        log.debug("Request to search OptionDeals for query {}", query);
        return StreamSupport
            .stream(optionDealSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

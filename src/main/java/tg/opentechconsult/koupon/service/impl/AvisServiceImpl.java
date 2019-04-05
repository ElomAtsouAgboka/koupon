package tg.opentechconsult.koupon.service.impl;

import tg.opentechconsult.koupon.service.AvisService;
import tg.opentechconsult.koupon.domain.Avis;
import tg.opentechconsult.koupon.repository.AvisRepository;
import tg.opentechconsult.koupon.repository.search.AvisSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Avis.
 */
@Service
@Transactional
public class AvisServiceImpl implements AvisService {

    private final Logger log = LoggerFactory.getLogger(AvisServiceImpl.class);

    private final AvisRepository avisRepository;

    private final AvisSearchRepository avisSearchRepository;

    public AvisServiceImpl(AvisRepository avisRepository, AvisSearchRepository avisSearchRepository) {
        this.avisRepository = avisRepository;
        this.avisSearchRepository = avisSearchRepository;
    }

    /**
     * Save a avis.
     *
     * @param avis the entity to save
     * @return the persisted entity
     */
    @Override
    public Avis save(Avis avis) {
        log.debug("Request to save Avis : {}", avis);
        Avis result = avisRepository.save(avis);
        avisSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the avis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Avis> findAll(Pageable pageable) {
        log.debug("Request to get all Avis");
        return avisRepository.findAll(pageable);
    }


    /**
     * Get one avis by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Avis> findOne(Long id) {
        log.debug("Request to get Avis : {}", id);
        return avisRepository.findById(id);
    }

    /**
     * Delete the avis by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Avis : {}", id);
        avisRepository.deleteById(id);
        avisSearchRepository.deleteById(id);
    }

    /**
     * Search for the avis corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Avis> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Avis for query {}", query);
        return avisSearchRepository.search(queryStringQuery(query), pageable);    }
}

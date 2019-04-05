package tg.opentechconsult.koupon.service.impl;

import tg.opentechconsult.koupon.service.MarchandService;
import tg.opentechconsult.koupon.domain.Marchand;
import tg.opentechconsult.koupon.repository.MarchandRepository;
import tg.opentechconsult.koupon.repository.search.MarchandSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Marchand.
 */
@Service
@Transactional
public class MarchandServiceImpl implements MarchandService {

    private final Logger log = LoggerFactory.getLogger(MarchandServiceImpl.class);

    private final MarchandRepository marchandRepository;

    private final MarchandSearchRepository marchandSearchRepository;

    public MarchandServiceImpl(MarchandRepository marchandRepository, MarchandSearchRepository marchandSearchRepository) {
        this.marchandRepository = marchandRepository;
        this.marchandSearchRepository = marchandSearchRepository;
    }

    /**
     * Save a marchand.
     *
     * @param marchand the entity to save
     * @return the persisted entity
     */
    @Override
    public Marchand save(Marchand marchand) {
        log.debug("Request to save Marchand : {}", marchand);
        Marchand result = marchandRepository.save(marchand);
        marchandSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the marchands.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Marchand> findAll(Pageable pageable) {
        log.debug("Request to get all Marchands");
        return marchandRepository.findAll(pageable);
    }


    /**
     * Get one marchand by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Marchand> findOne(Long id) {
        log.debug("Request to get Marchand : {}", id);
        return marchandRepository.findById(id);
    }

    /**
     * Delete the marchand by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Marchand : {}", id);
        marchandRepository.deleteById(id);
        marchandSearchRepository.deleteById(id);
    }

    /**
     * Search for the marchand corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Marchand> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Marchands for query {}", query);
        return marchandSearchRepository.search(queryStringQuery(query), pageable);    }
}

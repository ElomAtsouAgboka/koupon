package tg.opentechconsult.koupon.service.impl;

import tg.opentechconsult.koupon.service.MembreService;
import tg.opentechconsult.koupon.domain.Membre;
import tg.opentechconsult.koupon.repository.MembreRepository;
import tg.opentechconsult.koupon.repository.search.MembreSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Membre.
 */
@Service
@Transactional
public class MembreServiceImpl implements MembreService {

    private final Logger log = LoggerFactory.getLogger(MembreServiceImpl.class);

    private final MembreRepository membreRepository;

    private final MembreSearchRepository membreSearchRepository;

    public MembreServiceImpl(MembreRepository membreRepository, MembreSearchRepository membreSearchRepository) {
        this.membreRepository = membreRepository;
        this.membreSearchRepository = membreSearchRepository;
    }

    /**
     * Save a membre.
     *
     * @param membre the entity to save
     * @return the persisted entity
     */
    @Override
    public Membre save(Membre membre) {
        log.debug("Request to save Membre : {}", membre);
        Membre result = membreRepository.save(membre);
        membreSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the membres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Membre> findAll(Pageable pageable) {
        log.debug("Request to get all Membres");
        return membreRepository.findAll(pageable);
    }


    /**
     * Get one membre by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Membre> findOne(Long id) {
        log.debug("Request to get Membre : {}", id);
        return membreRepository.findById(id);
    }

    /**
     * Delete the membre by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Membre : {}", id);
        membreRepository.deleteById(id);
        membreSearchRepository.deleteById(id);
    }

    /**
     * Search for the membre corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Membre> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Membres for query {}", query);
        return membreSearchRepository.search(queryStringQuery(query), pageable);    }
}

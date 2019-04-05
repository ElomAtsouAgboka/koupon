package tg.opentechconsult.koupon.repository.search;

import tg.opentechconsult.koupon.domain.Membre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Membre entity.
 */
public interface MembreSearchRepository extends ElasticsearchRepository<Membre, Long> {
}

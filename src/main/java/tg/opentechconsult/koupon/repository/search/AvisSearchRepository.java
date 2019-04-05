package tg.opentechconsult.koupon.repository.search;

import tg.opentechconsult.koupon.domain.Avis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Avis entity.
 */
public interface AvisSearchRepository extends ElasticsearchRepository<Avis, Long> {
}

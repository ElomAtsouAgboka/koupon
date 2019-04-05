package tg.opentechconsult.koupon.repository.search;

import tg.opentechconsult.koupon.domain.Quartier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Quartier entity.
 */
public interface QuartierSearchRepository extends ElasticsearchRepository<Quartier, Long> {
}

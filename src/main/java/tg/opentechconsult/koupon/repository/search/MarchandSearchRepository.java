package tg.opentechconsult.koupon.repository.search;

import tg.opentechconsult.koupon.domain.Marchand;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Marchand entity.
 */
public interface MarchandSearchRepository extends ElasticsearchRepository<Marchand, Long> {
}

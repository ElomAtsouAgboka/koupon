package tg.opentechconsult.koupon.repository.search;

import tg.opentechconsult.koupon.domain.Deal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Deal entity.
 */
public interface DealSearchRepository extends ElasticsearchRepository<Deal, Long> {
}

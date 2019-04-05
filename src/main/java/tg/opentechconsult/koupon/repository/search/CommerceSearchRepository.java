package tg.opentechconsult.koupon.repository.search;

import tg.opentechconsult.koupon.domain.Commerce;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Commerce entity.
 */
public interface CommerceSearchRepository extends ElasticsearchRepository<Commerce, Long> {
}

package tg.opentechconsult.koupon.repository.search;

import tg.opentechconsult.koupon.domain.OptionDeal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OptionDeal entity.
 */
public interface OptionDealSearchRepository extends ElasticsearchRepository<OptionDeal, Long> {
}

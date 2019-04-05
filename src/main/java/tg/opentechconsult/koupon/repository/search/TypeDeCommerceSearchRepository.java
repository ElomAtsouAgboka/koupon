package tg.opentechconsult.koupon.repository.search;

import tg.opentechconsult.koupon.domain.TypeDeCommerce;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TypeDeCommerce entity.
 */
public interface TypeDeCommerceSearchRepository extends ElasticsearchRepository<TypeDeCommerce, Long> {
}

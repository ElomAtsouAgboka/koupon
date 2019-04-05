package tg.opentechconsult.koupon.repository.search;

import tg.opentechconsult.koupon.domain.Pays;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pays entity.
 */
public interface PaysSearchRepository extends ElasticsearchRepository<Pays, Long> {
}

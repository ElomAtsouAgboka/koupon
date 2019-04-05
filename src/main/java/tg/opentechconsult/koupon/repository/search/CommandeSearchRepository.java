package tg.opentechconsult.koupon.repository.search;

import tg.opentechconsult.koupon.domain.Commande;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Commande entity.
 */
public interface CommandeSearchRepository extends ElasticsearchRepository<Commande, Long> {
}

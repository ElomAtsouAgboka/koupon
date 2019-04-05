package tg.opentechconsult.koupon.repository.search;

import tg.opentechconsult.koupon.domain.Menu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Menu entity.
 */
public interface MenuSearchRepository extends ElasticsearchRepository<Menu, Long> {
}

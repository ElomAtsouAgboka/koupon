package tg.opentechconsult.koupon.repository.search;

import tg.opentechconsult.koupon.domain.Coupon;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Coupon entity.
 */
public interface CouponSearchRepository extends ElasticsearchRepository<Coupon, Long> {
}

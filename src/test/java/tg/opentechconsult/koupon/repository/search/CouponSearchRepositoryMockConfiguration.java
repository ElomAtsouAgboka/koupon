package tg.opentechconsult.koupon.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CouponSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CouponSearchRepositoryMockConfiguration {

    @MockBean
    private CouponSearchRepository mockCouponSearchRepository;

}

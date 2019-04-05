package tg.opentechconsult.koupon.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of OptionDealSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class OptionDealSearchRepositoryMockConfiguration {

    @MockBean
    private OptionDealSearchRepository mockOptionDealSearchRepository;

}

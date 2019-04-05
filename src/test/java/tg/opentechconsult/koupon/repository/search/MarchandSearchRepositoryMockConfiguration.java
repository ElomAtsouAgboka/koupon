package tg.opentechconsult.koupon.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of MarchandSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MarchandSearchRepositoryMockConfiguration {

    @MockBean
    private MarchandSearchRepository mockMarchandSearchRepository;

}

package tg.opentechconsult.koupon.repository;

import tg.opentechconsult.koupon.domain.OptionDeal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OptionDeal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionDealRepository extends JpaRepository<OptionDeal, Long> {

}

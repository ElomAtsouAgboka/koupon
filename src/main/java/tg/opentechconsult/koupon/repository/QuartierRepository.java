package tg.opentechconsult.koupon.repository;

import tg.opentechconsult.koupon.domain.Quartier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Quartier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuartierRepository extends JpaRepository<Quartier, Long> {

}

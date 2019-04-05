package tg.opentechconsult.koupon.repository;

import tg.opentechconsult.koupon.domain.Avis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Avis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisRepository extends JpaRepository<Avis, Long> {

}

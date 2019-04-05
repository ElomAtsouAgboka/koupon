package tg.opentechconsult.koupon.repository;

import tg.opentechconsult.koupon.domain.Marchand;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Marchand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarchandRepository extends JpaRepository<Marchand, Long> {

}

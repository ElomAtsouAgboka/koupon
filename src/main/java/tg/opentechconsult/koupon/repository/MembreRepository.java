package tg.opentechconsult.koupon.repository;

import tg.opentechconsult.koupon.domain.Membre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Membre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {

}

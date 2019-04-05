package tg.opentechconsult.koupon.repository;

import tg.opentechconsult.koupon.domain.TypeDeCommerce;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeDeCommerce entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeDeCommerceRepository extends JpaRepository<TypeDeCommerce, Long> {

}

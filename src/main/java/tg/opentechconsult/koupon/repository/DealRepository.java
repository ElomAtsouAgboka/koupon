package tg.opentechconsult.koupon.repository;

import tg.opentechconsult.koupon.domain.Deal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Deal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

    @Query(value = "select distinct deal from Deal deal left join fetch deal.menus left join fetch deal.categories",
        countQuery = "select count(distinct deal) from Deal deal")
    Page<Deal> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct deal from Deal deal left join fetch deal.menus left join fetch deal.categories")
    List<Deal> findAllWithEagerRelationships();

    @Query("select deal from Deal deal left join fetch deal.menus left join fetch deal.categories where deal.id =:id")
    Optional<Deal> findOneWithEagerRelationships(@Param("id") Long id);

}

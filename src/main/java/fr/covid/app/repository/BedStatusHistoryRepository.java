package fr.covid.app.repository;

import fr.covid.app.domain.BedStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the BedStatusHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BedStatusHistoryRepository extends JpaRepository<BedStatusHistory, String> {

}

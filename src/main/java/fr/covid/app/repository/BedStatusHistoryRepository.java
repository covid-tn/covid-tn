package fr.covid.app.repository;

import fr.covid.app.domain.BedStatusHistory;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the BedStatusHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BedStatusHistoryRepository extends MongoRepository<BedStatusHistory, String> {

}

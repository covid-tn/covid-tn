package fr.covid.app.repository;

import fr.covid.app.domain.ServiceRoom;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the ServiceRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRoomRepository extends MongoRepository<ServiceRoom, String> {

}

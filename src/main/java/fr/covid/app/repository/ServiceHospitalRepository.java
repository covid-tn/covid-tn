package fr.covid.app.repository;

import fr.covid.app.domain.ServiceHospital;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the ServiceHospital entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceHospitalRepository extends MongoRepository<ServiceHospital, String> {

}

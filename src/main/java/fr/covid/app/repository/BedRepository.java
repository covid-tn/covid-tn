package fr.covid.app.repository;

import fr.covid.app.domain.Bed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Bed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BedRepository extends MongoRepository<Bed, String> {

    @Query("{ 'hospital': ?0}")
    Page<Bed> findByHospital(Pageable pageable, String hospitalId);
}

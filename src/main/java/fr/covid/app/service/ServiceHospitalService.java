package fr.covid.app.service;

import fr.covid.app.domain.ServiceHospital;

import fr.covid.app.domain.ServiceRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ServiceHospital}.
 */
public interface ServiceHospitalService {

    /**
     * Save a serviceHospital.
     *
     * @param serviceHospital the entity to save.
     * @return the persisted entity.
     */
    ServiceHospital save(ServiceHospital serviceHospital);

    /**
     * Get all the serviceHospitals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceHospital> findAll(Pageable pageable);


    /**
     * Get the "id" serviceHospital.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceHospital> findOne(String id);

    void addRoomToService(String serviceId, ServiceRoom room);

    /**
     * Delete the "id" serviceHospital.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    void removeRoomFromService(ServiceRoom serviceRoom);
}

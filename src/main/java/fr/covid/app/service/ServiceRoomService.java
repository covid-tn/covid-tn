package fr.covid.app.service;

import fr.covid.app.domain.ServiceRoom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ServiceRoom}.
 */
public interface ServiceRoomService {

    /**
     * Save a serviceRoom.
     *
     * @param serviceRoom the entity to save.
     * @return the persisted entity.
     */
    ServiceRoom save(ServiceRoom serviceRoom);

    /**
     * Get all the serviceRooms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceRoom> findAll(Pageable pageable);


    /**
     * Get the "id" serviceRoom.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceRoom> findOne(String id);

    /**
     * Delete the "id" serviceRoom.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

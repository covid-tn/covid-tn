package fr.covid.app.service;

import fr.covid.app.domain.Bed;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Bed}.
 */
public interface BedService {

    /**
     * Save a bed.
     *
     * @param bed the entity to save.
     * @return the persisted entity.
     */
    Bed save(Bed bed);

    /**
     * Get all the beds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Bed> findAll(Pageable pageable);


    /**
     * Get the "id" bed.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bed> findOne(String id);

    /**
     * Delete the "id" bed.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

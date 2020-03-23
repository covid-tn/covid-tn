package fr.covid.app.service;

import fr.covid.app.domain.BedStatusHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link BedStatusHistory}.
 */
public interface BedStatusHistoryService {

    /**
     * Save a bedStatusHistory.
     *
     * @param bedStatusHistory the entity to save.
     * @return the persisted entity.
     */
    BedStatusHistory save(BedStatusHistory bedStatusHistory);

    /**
     * Get all the bedStatusHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BedStatusHistory> findAll(Pageable pageable);


    /**
     * Get the "id" bedStatusHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BedStatusHistory> findOne(String id);

    /**
     * Delete the "id" bedStatusHistory.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

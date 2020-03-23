package fr.covid.app.service.impl;

import fr.covid.app.service.BedStatusHistoryService;
import fr.covid.app.domain.BedStatusHistory;
import fr.covid.app.repository.BedStatusHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BedStatusHistory}.
 */
@Service
public class BedStatusHistoryServiceImpl implements BedStatusHistoryService {

    private final Logger log = LoggerFactory.getLogger(BedStatusHistoryServiceImpl.class);

    private final BedStatusHistoryRepository bedStatusHistoryRepository;

    public BedStatusHistoryServiceImpl(BedStatusHistoryRepository bedStatusHistoryRepository) {
        this.bedStatusHistoryRepository = bedStatusHistoryRepository;
    }

    /**
     * Save a bedStatusHistory.
     *
     * @param bedStatusHistory the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BedStatusHistory save(BedStatusHistory bedStatusHistory) {
        log.debug("Request to save BedStatusHistory : {}", bedStatusHistory);
        return bedStatusHistoryRepository.save(bedStatusHistory);
    }

    /**
     * Get all the bedStatusHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<BedStatusHistory> findAll(Pageable pageable) {
        log.debug("Request to get all BedStatusHistories");
        return bedStatusHistoryRepository.findAll(pageable);
    }


    /**
     * Get one bedStatusHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<BedStatusHistory> findOne(String id) {
        log.debug("Request to get BedStatusHistory : {}", id);
        return bedStatusHistoryRepository.findById(id);
    }

    /**
     * Delete the bedStatusHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete BedStatusHistory : {}", id);
        bedStatusHistoryRepository.deleteById(id);
    }
}

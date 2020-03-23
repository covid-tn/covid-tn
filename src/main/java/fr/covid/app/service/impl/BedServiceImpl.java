package fr.covid.app.service.impl;

import fr.covid.app.service.BedService;
import fr.covid.app.domain.Bed;
import fr.covid.app.repository.BedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Bed}.
 */
@Service
public class BedServiceImpl implements BedService {

    private final Logger log = LoggerFactory.getLogger(BedServiceImpl.class);

    private final BedRepository bedRepository;

    public BedServiceImpl(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    /**
     * Save a bed.
     *
     * @param bed the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Bed save(Bed bed) {
        log.debug("Request to save Bed : {}", bed);
        return bedRepository.save(bed);
    }

    /**
     * Get all the beds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Bed> findAll(Pageable pageable) {
        log.debug("Request to get all Beds");
        return bedRepository.findAll(pageable);
    }


    /**
     * Get one bed by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Bed> findOne(String id) {
        log.debug("Request to get Bed : {}", id);
        return bedRepository.findById(id);
    }

    /**
     * Delete the bed by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Bed : {}", id);
        bedRepository.deleteById(id);
    }
}

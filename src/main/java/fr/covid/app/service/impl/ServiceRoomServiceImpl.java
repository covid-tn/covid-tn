package fr.covid.app.service.impl;

import fr.covid.app.service.ServiceHospitalService;
import fr.covid.app.service.ServiceRoomService;
import fr.covid.app.domain.ServiceRoom;
import fr.covid.app.repository.ServiceRoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceRoom}.
 */
@Service
public class ServiceRoomServiceImpl implements ServiceRoomService {

    private final Logger log = LoggerFactory.getLogger(ServiceRoomServiceImpl.class);

    private final ServiceRoomRepository serviceRoomRepository;

    private final ServiceHospitalService serviceHospitalService;

    public ServiceRoomServiceImpl(ServiceRoomRepository serviceRoomRepository, ServiceHospitalService serviceHospitalService) {
        this.serviceRoomRepository = serviceRoomRepository;
        this.serviceHospitalService = serviceHospitalService;
    }

    /**
     * Save a serviceRoom.
     *
     * @param serviceRoom the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceRoom save(ServiceRoom serviceRoom) {
        log.debug("Request to save ServiceRoom : {}", serviceRoom);
        ServiceRoom result = serviceRoomRepository.save(serviceRoom);
        if (result.getServiceHospital() != null) {
            serviceHospitalService.addRoomToService(result.getServiceHospital().getId(), serviceRoom);
        }
        return result;
    }

    /**
     * Get all the serviceRooms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<ServiceRoom> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceRooms");
        return serviceRoomRepository.findAll(pageable);
    }


    /**
     * Get one serviceRoom by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<ServiceRoom> findOne(String id) {
        log.debug("Request to get ServiceRoom : {}", id);
        return serviceRoomRepository.findById(id);
    }

    /**
     * Delete the serviceRoom by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete ServiceRoom : {}", id);
        serviceRoomRepository.findById(id).ifPresent(serviceHospitalService::removeRoomFromService);
        serviceRoomRepository.deleteById(id);
    }
}

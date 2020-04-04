package fr.covid.app.service.impl;

import fr.covid.app.domain.ServiceRoom;
import fr.covid.app.service.ServiceHospitalService;
import fr.covid.app.domain.ServiceHospital;
import fr.covid.app.repository.ServiceHospitalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceHospital}.
 */
@Service
public class ServiceHospitalServiceImpl implements ServiceHospitalService {

    private final Logger log = LoggerFactory.getLogger(ServiceHospitalServiceImpl.class);

    private final ServiceHospitalRepository serviceHospitalRepository;

    public ServiceHospitalServiceImpl(ServiceHospitalRepository serviceHospitalRepository) {
        this.serviceHospitalRepository = serviceHospitalRepository;
    }

    /**
     * Save a serviceHospital.
     *
     * @param serviceHospital the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceHospital save(ServiceHospital serviceHospital) {
        log.debug("Request to save ServiceHospital : {}", serviceHospital);
        return serviceHospitalRepository.save(serviceHospital);
    }

    /**
     * Get all the serviceHospitals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<ServiceHospital> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceHospitals");
        return serviceHospitalRepository.findAll(pageable);
    }


    /**
     * Get one serviceHospital by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<ServiceHospital> findOne(String id) {
        log.debug("Request to get ServiceHospital : {}", id);
        return serviceHospitalRepository.findById(id);
    }

    @Override
    public void addRoomToService(String serviceId, ServiceRoom room) {
        log.debug("Add Room {} to HospitalService : {}", room, serviceId);
        findOne(serviceId).ifPresent(
            serviceHospital -> {
                serviceHospital.addRoom(room);
                save(serviceHospital);
            }
        );
    }

    /**
     * Delete the serviceHospital by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete ServiceHospital : {}", id);
        serviceHospitalRepository.deleteById(id);
    }

    @Override
    public  void removeRoomFromService(ServiceRoom serviceRoom) {
        log.debug("Remove Room {} from Service {}",serviceRoom, serviceRoom.getServiceHospital());
        findOne(serviceRoom.getServiceHospital().getId()).ifPresent(serviceHospital -> {
            serviceHospital.removeRoom(serviceRoom);
            save(serviceHospital);
        });
    }
}

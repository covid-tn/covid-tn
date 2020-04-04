package fr.covid.app.web.rest;

import fr.covid.app.domain.ServiceRoom;
import fr.covid.app.service.ServiceRoomService;
import fr.covid.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.covid.app.domain.ServiceRoom}.
 */
@RestController
@RequestMapping("/api")
public class ServiceRoomResource {

    private final Logger log = LoggerFactory.getLogger(ServiceRoomResource.class);

    private static final String ENTITY_NAME = "serviceRoom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceRoomService serviceRoomService;

    public ServiceRoomResource(ServiceRoomService serviceRoomService) {
        this.serviceRoomService = serviceRoomService;
    }

    /**
     * {@code POST  /service-rooms} : Create a new serviceRoom.
     *
     * @param serviceRoom the serviceRoom to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceRoom, or with status {@code 400 (Bad Request)} if the serviceRoom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-rooms")
    public ResponseEntity<ServiceRoom> createServiceRoom(@Valid @RequestBody ServiceRoom serviceRoom) throws URISyntaxException {
        log.debug("REST request to save ServiceRoom : {}", serviceRoom);
        if (serviceRoom.getId() != null) {
            throw new BadRequestAlertException("A new serviceRoom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceRoom result = serviceRoomService.save(serviceRoom);
        return ResponseEntity.created(new URI("/api/service-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-rooms} : Updates an existing serviceRoom.
     *
     * @param serviceRoom the serviceRoom to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceRoom,
     * or with status {@code 400 (Bad Request)} if the serviceRoom is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceRoom couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-rooms")
    public ResponseEntity<ServiceRoom> updateServiceRoom(@Valid @RequestBody ServiceRoom serviceRoom) throws URISyntaxException {
        log.debug("REST request to update ServiceRoom : {}", serviceRoom);
        if (serviceRoom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceRoom result = serviceRoomService.save(serviceRoom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceRoom.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-rooms} : get all the serviceRooms.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceRooms in body.
     */
    @GetMapping("/service-rooms")
    public ResponseEntity<List<ServiceRoom>> getAllServiceRooms(Pageable pageable) {
        log.debug("REST request to get a page of ServiceRooms");
        Page<ServiceRoom> page = serviceRoomService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-rooms/:id} : get the "id" serviceRoom.
     *
     * @param id the id of the serviceRoom to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceRoom, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-rooms/{id}")
    public ResponseEntity<ServiceRoom> getServiceRoom(@PathVariable String id) {
        log.debug("REST request to get ServiceRoom : {}", id);
        Optional<ServiceRoom> serviceRoom = serviceRoomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceRoom);
    }

    /**
     * {@code DELETE  /service-rooms/:id} : delete the "id" serviceRoom.
     *
     * @param id the id of the serviceRoom to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-rooms/{id}")
    public ResponseEntity<Void> deleteServiceRoom(@PathVariable String id) {
        log.debug("REST request to delete ServiceRoom : {}", id);
        serviceRoomService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}

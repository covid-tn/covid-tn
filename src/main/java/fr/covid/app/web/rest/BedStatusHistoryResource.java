package fr.covid.app.web.rest;

import fr.covid.app.domain.BedStatusHistory;
import fr.covid.app.service.BedStatusHistoryService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.covid.app.domain.BedStatusHistory}.
 */
@RestController
@RequestMapping("/api")
public class BedStatusHistoryResource {

    private final Logger log = LoggerFactory.getLogger(BedStatusHistoryResource.class);

    private static final String ENTITY_NAME = "bedStatusHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BedStatusHistoryService bedStatusHistoryService;

    public BedStatusHistoryResource(BedStatusHistoryService bedStatusHistoryService) {
        this.bedStatusHistoryService = bedStatusHistoryService;
    }

    /**
     * {@code POST  /bed-status-histories} : Create a new bedStatusHistory.
     *
     * @param bedStatusHistory the bedStatusHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bedStatusHistory, or with status {@code 400 (Bad Request)} if the bedStatusHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bed-status-histories")
    public ResponseEntity<BedStatusHistory> createBedStatusHistory(@RequestBody BedStatusHistory bedStatusHistory) throws URISyntaxException {
        log.debug("REST request to save BedStatusHistory : {}", bedStatusHistory);
        if (bedStatusHistory.getId() != null) {
            throw new BadRequestAlertException("A new bedStatusHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BedStatusHistory result = bedStatusHistoryService.save(bedStatusHistory);
        return ResponseEntity.created(new URI("/api/bed-status-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bed-status-histories} : Updates an existing bedStatusHistory.
     *
     * @param bedStatusHistory the bedStatusHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedStatusHistory,
     * or with status {@code 400 (Bad Request)} if the bedStatusHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bedStatusHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bed-status-histories")
    public ResponseEntity<BedStatusHistory> updateBedStatusHistory(@RequestBody BedStatusHistory bedStatusHistory) throws URISyntaxException {
        log.debug("REST request to update BedStatusHistory : {}", bedStatusHistory);
        if (bedStatusHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BedStatusHistory result = bedStatusHistoryService.save(bedStatusHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bedStatusHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bed-status-histories} : get all the bedStatusHistories.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bedStatusHistories in body.
     */
    @GetMapping("/bed-status-histories")
    public ResponseEntity<List<BedStatusHistory>> getAllBedStatusHistories(Pageable pageable) {
        log.debug("REST request to get a page of BedStatusHistories");
        Page<BedStatusHistory> page = bedStatusHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bed-status-histories/:id} : get the "id" bedStatusHistory.
     *
     * @param id the id of the bedStatusHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bedStatusHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bed-status-histories/{id}")
    public ResponseEntity<BedStatusHistory> getBedStatusHistory(@PathVariable String id) {
        log.debug("REST request to get BedStatusHistory : {}", id);
        Optional<BedStatusHistory> bedStatusHistory = bedStatusHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bedStatusHistory);
    }

    /**
     * {@code DELETE  /bed-status-histories/:id} : delete the "id" bedStatusHistory.
     *
     * @param id the id of the bedStatusHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bed-status-histories/{id}")
    public ResponseEntity<Void> deleteBedStatusHistory(@PathVariable String id) {
        log.debug("REST request to delete BedStatusHistory : {}", id);
        bedStatusHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}

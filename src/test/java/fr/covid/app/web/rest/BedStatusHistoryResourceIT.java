package fr.covid.app.web.rest;

import fr.covid.app.CovidApp;
import fr.covid.app.domain.BedStatusHistory;
import fr.covid.app.repository.BedStatusHistoryRepository;
import fr.covid.app.service.BedStatusHistoryService;
import fr.covid.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static fr.covid.app.web.rest.TestUtil.sameInstant;
import static fr.covid.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BedStatusHistoryResource} REST controller.
 */
@SpringBootTest(classes = CovidApp.class)
public class BedStatusHistoryResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BedStatusHistoryRepository bedStatusHistoryRepository;

    @Autowired
    private BedStatusHistoryService bedStatusHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restBedStatusHistoryMockMvc;

    private BedStatusHistory bedStatusHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BedStatusHistoryResource bedStatusHistoryResource = new BedStatusHistoryResource(bedStatusHistoryService);
        this.restBedStatusHistoryMockMvc = MockMvcBuilders.standaloneSetup(bedStatusHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BedStatusHistory createEntity() {
        BedStatusHistory bedStatusHistory = new BedStatusHistory()
            .createdDate(DEFAULT_CREATED_DATE);
        return bedStatusHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BedStatusHistory createUpdatedEntity() {
        BedStatusHistory bedStatusHistory = new BedStatusHistory()
            .createdDate(UPDATED_CREATED_DATE);
        return bedStatusHistory;
    }

    @BeforeEach
    public void initTest() {
        bedStatusHistoryRepository.deleteAll();
        bedStatusHistory = createEntity();
    }

    @Test
    public void createBedStatusHistory() throws Exception {
        int databaseSizeBeforeCreate = bedStatusHistoryRepository.findAll().size();

        // Create the BedStatusHistory
        restBedStatusHistoryMockMvc.perform(post("/api/bed-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bedStatusHistory)))
            .andExpect(status().isCreated());

        // Validate the BedStatusHistory in the database
        List<BedStatusHistory> bedStatusHistoryList = bedStatusHistoryRepository.findAll();
        assertThat(bedStatusHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        BedStatusHistory testBedStatusHistory = bedStatusHistoryList.get(bedStatusHistoryList.size() - 1);
        assertThat(testBedStatusHistory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    public void createBedStatusHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bedStatusHistoryRepository.findAll().size();

        // Create the BedStatusHistory with an existing ID
        bedStatusHistory.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restBedStatusHistoryMockMvc.perform(post("/api/bed-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bedStatusHistory)))
            .andExpect(status().isBadRequest());

        // Validate the BedStatusHistory in the database
        List<BedStatusHistory> bedStatusHistoryList = bedStatusHistoryRepository.findAll();
        assertThat(bedStatusHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllBedStatusHistories() throws Exception {
        // Initialize the database
        bedStatusHistoryRepository.save(bedStatusHistory);

        // Get all the bedStatusHistoryList
        restBedStatusHistoryMockMvc.perform(get("/api/bed-status-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bedStatusHistory.getId())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))));
    }
    
    @Test
    public void getBedStatusHistory() throws Exception {
        // Initialize the database
        bedStatusHistoryRepository.save(bedStatusHistory);

        // Get the bedStatusHistory
        restBedStatusHistoryMockMvc.perform(get("/api/bed-status-histories/{id}", bedStatusHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bedStatusHistory.getId()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)));
    }

    @Test
    public void getNonExistingBedStatusHistory() throws Exception {
        // Get the bedStatusHistory
        restBedStatusHistoryMockMvc.perform(get("/api/bed-status-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBedStatusHistory() throws Exception {
        // Initialize the database
        bedStatusHistoryService.save(bedStatusHistory);

        int databaseSizeBeforeUpdate = bedStatusHistoryRepository.findAll().size();

        // Update the bedStatusHistory
        BedStatusHistory updatedBedStatusHistory = bedStatusHistoryRepository.findById(bedStatusHistory.getId()).get();
        updatedBedStatusHistory
            .createdDate(UPDATED_CREATED_DATE);

        restBedStatusHistoryMockMvc.perform(put("/api/bed-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBedStatusHistory)))
            .andExpect(status().isOk());

        // Validate the BedStatusHistory in the database
        List<BedStatusHistory> bedStatusHistoryList = bedStatusHistoryRepository.findAll();
        assertThat(bedStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
        BedStatusHistory testBedStatusHistory = bedStatusHistoryList.get(bedStatusHistoryList.size() - 1);
        assertThat(testBedStatusHistory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    public void updateNonExistingBedStatusHistory() throws Exception {
        int databaseSizeBeforeUpdate = bedStatusHistoryRepository.findAll().size();

        // Create the BedStatusHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBedStatusHistoryMockMvc.perform(put("/api/bed-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bedStatusHistory)))
            .andExpect(status().isBadRequest());

        // Validate the BedStatusHistory in the database
        List<BedStatusHistory> bedStatusHistoryList = bedStatusHistoryRepository.findAll();
        assertThat(bedStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBedStatusHistory() throws Exception {
        // Initialize the database
        bedStatusHistoryService.save(bedStatusHistory);

        int databaseSizeBeforeDelete = bedStatusHistoryRepository.findAll().size();

        // Delete the bedStatusHistory
        restBedStatusHistoryMockMvc.perform(delete("/api/bed-status-histories/{id}", bedStatusHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BedStatusHistory> bedStatusHistoryList = bedStatusHistoryRepository.findAll();
        assertThat(bedStatusHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

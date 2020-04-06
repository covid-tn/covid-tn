package fr.covid.app.web.rest;

import fr.covid.app.CovidApp;
import fr.covid.app.domain.Bed;
import fr.covid.app.repository.BedRepository;
import fr.covid.app.service.BedService;
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


import java.util.List;

import static fr.covid.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.covid.app.domain.enumeration.BedStatus;
/**
 * Integration tests for the {@link BedResource} REST controller.
 */
@SpringBootTest(classes = CovidApp.class)
public class BedResourceIT {

    private static final BedStatus DEFAULT_STATUS = BedStatus.AVAILABLE;
    private static final BedStatus UPDATED_STATUS = BedStatus.RESERVED;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private BedService bedService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restBedMockMvc;

    private Bed bed;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BedResource bedResource = new BedResource(bedService);
        this.restBedMockMvc = MockMvcBuilders.standaloneSetup(bedResource)
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
    public static Bed createEntity() {
        Bed bed = new Bed()
            .status(DEFAULT_STATUS)
            .name(DEFAULT_NAME);
        return bed;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bed createUpdatedEntity() {
        Bed bed = new Bed()
            .status(UPDATED_STATUS)
            .name(UPDATED_NAME);
        return bed;
    }

    @BeforeEach
    public void initTest() {
        bedRepository.deleteAll();
        bed = createEntity();
    }

    @Test
    public void createBed() throws Exception {
        int databaseSizeBeforeCreate = bedRepository.findAll().size();

        // Create the Bed
        restBedMockMvc.perform(post("/api/beds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bed)))
            .andExpect(status().isCreated());

        // Validate the Bed in the database
        List<Bed> bedList = bedRepository.findAll();
        assertThat(bedList).hasSize(databaseSizeBeforeCreate + 1);
        Bed testBed = bedList.get(bedList.size() - 1);
        assertThat(testBed.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBed.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createBedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bedRepository.findAll().size();

        // Create the Bed with an existing ID
        bed.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restBedMockMvc.perform(post("/api/beds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bed)))
            .andExpect(status().isBadRequest());

        // Validate the Bed in the database
        List<Bed> bedList = bedRepository.findAll();
        assertThat(bedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllBeds() throws Exception {
        // Initialize the database
        bedRepository.save(bed);

        // Get all the bedList
        restBedMockMvc.perform(get("/api/beds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bed.getId())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    public void getBed() throws Exception {
        // Initialize the database
        bedRepository.save(bed);

        // Get the bed
        restBedMockMvc.perform(get("/api/beds/{id}", bed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bed.getId()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    public void getNonExistingBed() throws Exception {
        // Get the bed
        restBedMockMvc.perform(get("/api/beds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBed() throws Exception {
        // Initialize the database
        bedService.save(bed);

        int databaseSizeBeforeUpdate = bedRepository.findAll().size();

        // Update the bed
        Bed updatedBed = bedRepository.findById(bed.getId()).get();
        updatedBed
            .status(UPDATED_STATUS)
            .name(UPDATED_NAME);

        restBedMockMvc.perform(put("/api/beds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBed)))
            .andExpect(status().isOk());

        // Validate the Bed in the database
        List<Bed> bedList = bedRepository.findAll();
        assertThat(bedList).hasSize(databaseSizeBeforeUpdate);
        Bed testBed = bedList.get(bedList.size() - 1);
        assertThat(testBed.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBed.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingBed() throws Exception {
        int databaseSizeBeforeUpdate = bedRepository.findAll().size();

        // Create the Bed

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBedMockMvc.perform(put("/api/beds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bed)))
            .andExpect(status().isBadRequest());

        // Validate the Bed in the database
        List<Bed> bedList = bedRepository.findAll();
        assertThat(bedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBed() throws Exception {
        // Initialize the database
        bedService.save(bed);

        int databaseSizeBeforeDelete = bedRepository.findAll().size();

        // Delete the bed
        restBedMockMvc.perform(delete("/api/beds/{id}", bed.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bed> bedList = bedRepository.findAll();
        assertThat(bedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

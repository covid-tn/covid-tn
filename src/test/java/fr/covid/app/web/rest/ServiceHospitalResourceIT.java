package fr.covid.app.web.rest;

import fr.covid.app.CovidApp;
import fr.covid.app.domain.ServiceHospital;
import fr.covid.app.repository.ServiceHospitalRepository;
import fr.covid.app.service.ServiceHospitalService;
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

/**
 * Integration tests for the {@link ServiceHospitalResource} REST controller.
 */
@SpringBootTest(classes = CovidApp.class)
public class ServiceHospitalResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ServiceHospitalRepository serviceHospitalRepository;

    @Autowired
    private ServiceHospitalService serviceHospitalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restServiceHospitalMockMvc;

    private ServiceHospital serviceHospital;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceHospitalResource serviceHospitalResource = new ServiceHospitalResource(serviceHospitalService);
        this.restServiceHospitalMockMvc = MockMvcBuilders.standaloneSetup(serviceHospitalResource)
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
    public static ServiceHospital createEntity() {
        ServiceHospital serviceHospital = new ServiceHospital()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return serviceHospital;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceHospital createUpdatedEntity() {
        ServiceHospital serviceHospital = new ServiceHospital()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return serviceHospital;
    }

    @BeforeEach
    public void initTest() {
        serviceHospitalRepository.deleteAll();
        serviceHospital = createEntity();
    }

    @Test
    public void createServiceHospital() throws Exception {
        int databaseSizeBeforeCreate = serviceHospitalRepository.findAll().size();

        // Create the ServiceHospital
        restServiceHospitalMockMvc.perform(post("/api/service-hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceHospital)))
            .andExpect(status().isCreated());

        // Validate the ServiceHospital in the database
        List<ServiceHospital> serviceHospitalList = serviceHospitalRepository.findAll();
        assertThat(serviceHospitalList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceHospital testServiceHospital = serviceHospitalList.get(serviceHospitalList.size() - 1);
        assertThat(testServiceHospital.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceHospital.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createServiceHospitalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceHospitalRepository.findAll().size();

        // Create the ServiceHospital with an existing ID
        serviceHospital.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceHospitalMockMvc.perform(post("/api/service-hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceHospital)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceHospital in the database
        List<ServiceHospital> serviceHospitalList = serviceHospitalRepository.findAll();
        assertThat(serviceHospitalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceHospitalRepository.findAll().size();
        // set the field null
        serviceHospital.setName(null);

        // Create the ServiceHospital, which fails.

        restServiceHospitalMockMvc.perform(post("/api/service-hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceHospital)))
            .andExpect(status().isBadRequest());

        List<ServiceHospital> serviceHospitalList = serviceHospitalRepository.findAll();
        assertThat(serviceHospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllServiceHospitals() throws Exception {
        // Initialize the database
        serviceHospitalRepository.save(serviceHospital);

        // Get all the serviceHospitalList
        restServiceHospitalMockMvc.perform(get("/api/service-hospitals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceHospital.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    public void getServiceHospital() throws Exception {
        // Initialize the database
        serviceHospitalRepository.save(serviceHospital);

        // Get the serviceHospital
        restServiceHospitalMockMvc.perform(get("/api/service-hospitals/{id}", serviceHospital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceHospital.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    public void getNonExistingServiceHospital() throws Exception {
        // Get the serviceHospital
        restServiceHospitalMockMvc.perform(get("/api/service-hospitals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateServiceHospital() throws Exception {
        // Initialize the database
        serviceHospitalService.save(serviceHospital);

        int databaseSizeBeforeUpdate = serviceHospitalRepository.findAll().size();

        // Update the serviceHospital
        ServiceHospital updatedServiceHospital = serviceHospitalRepository.findById(serviceHospital.getId()).get();
        updatedServiceHospital
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restServiceHospitalMockMvc.perform(put("/api/service-hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceHospital)))
            .andExpect(status().isOk());

        // Validate the ServiceHospital in the database
        List<ServiceHospital> serviceHospitalList = serviceHospitalRepository.findAll();
        assertThat(serviceHospitalList).hasSize(databaseSizeBeforeUpdate);
        ServiceHospital testServiceHospital = serviceHospitalList.get(serviceHospitalList.size() - 1);
        assertThat(testServiceHospital.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceHospital.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingServiceHospital() throws Exception {
        int databaseSizeBeforeUpdate = serviceHospitalRepository.findAll().size();

        // Create the ServiceHospital

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceHospitalMockMvc.perform(put("/api/service-hospitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceHospital)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceHospital in the database
        List<ServiceHospital> serviceHospitalList = serviceHospitalRepository.findAll();
        assertThat(serviceHospitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteServiceHospital() throws Exception {
        // Initialize the database
        serviceHospitalService.save(serviceHospital);

        int databaseSizeBeforeDelete = serviceHospitalRepository.findAll().size();

        // Delete the serviceHospital
        restServiceHospitalMockMvc.perform(delete("/api/service-hospitals/{id}", serviceHospital.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceHospital> serviceHospitalList = serviceHospitalRepository.findAll();
        assertThat(serviceHospitalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package fr.covid.app.web.rest;

import fr.covid.app.CovidApp;
import fr.covid.app.domain.ServiceRoom;
import fr.covid.app.repository.ServiceRoomRepository;
import fr.covid.app.service.ServiceRoomService;
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
 * Integration tests for the {@link ServiceRoomResource} REST controller.
 */
@SpringBootTest(classes = CovidApp.class)
public class ServiceRoomResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ServiceRoomRepository serviceRoomRepository;

    @Autowired
    private ServiceRoomService serviceRoomService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restServiceRoomMockMvc;

    private ServiceRoom serviceRoom;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceRoomResource serviceRoomResource = new ServiceRoomResource(serviceRoomService);
        this.restServiceRoomMockMvc = MockMvcBuilders.standaloneSetup(serviceRoomResource)
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
    public static ServiceRoom createEntity() {
        ServiceRoom serviceRoom = new ServiceRoom()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return serviceRoom;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceRoom createUpdatedEntity() {
        ServiceRoom serviceRoom = new ServiceRoom()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return serviceRoom;
    }

    @BeforeEach
    public void initTest() {
        serviceRoomRepository.deleteAll();
        serviceRoom = createEntity();
    }

    @Test
    public void createServiceRoom() throws Exception {
        int databaseSizeBeforeCreate = serviceRoomRepository.findAll().size();

        // Create the ServiceRoom
        restServiceRoomMockMvc.perform(post("/api/service-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRoom)))
            .andExpect(status().isCreated());

        // Validate the ServiceRoom in the database
        List<ServiceRoom> serviceRoomList = serviceRoomRepository.findAll();
        assertThat(serviceRoomList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceRoom testServiceRoom = serviceRoomList.get(serviceRoomList.size() - 1);
        assertThat(testServiceRoom.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceRoom.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createServiceRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceRoomRepository.findAll().size();

        // Create the ServiceRoom with an existing ID
        serviceRoom.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceRoomMockMvc.perform(post("/api/service-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRoom)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRoom in the database
        List<ServiceRoom> serviceRoomList = serviceRoomRepository.findAll();
        assertThat(serviceRoomList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRoomRepository.findAll().size();
        // set the field null
        serviceRoom.setName(null);

        // Create the ServiceRoom, which fails.

        restServiceRoomMockMvc.perform(post("/api/service-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRoom)))
            .andExpect(status().isBadRequest());

        List<ServiceRoom> serviceRoomList = serviceRoomRepository.findAll();
        assertThat(serviceRoomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllServiceRooms() throws Exception {
        // Initialize the database
        serviceRoomRepository.save(serviceRoom);

        // Get all the serviceRoomList
        restServiceRoomMockMvc.perform(get("/api/service-rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRoom.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    public void getServiceRoom() throws Exception {
        // Initialize the database
        serviceRoomRepository.save(serviceRoom);

        // Get the serviceRoom
        restServiceRoomMockMvc.perform(get("/api/service-rooms/{id}", serviceRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceRoom.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    public void getNonExistingServiceRoom() throws Exception {
        // Get the serviceRoom
        restServiceRoomMockMvc.perform(get("/api/service-rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateServiceRoom() throws Exception {
        // Initialize the database
        serviceRoomService.save(serviceRoom);

        int databaseSizeBeforeUpdate = serviceRoomRepository.findAll().size();

        // Update the serviceRoom
        ServiceRoom updatedServiceRoom = serviceRoomRepository.findById(serviceRoom.getId()).get();
        updatedServiceRoom
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restServiceRoomMockMvc.perform(put("/api/service-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceRoom)))
            .andExpect(status().isOk());

        // Validate the ServiceRoom in the database
        List<ServiceRoom> serviceRoomList = serviceRoomRepository.findAll();
        assertThat(serviceRoomList).hasSize(databaseSizeBeforeUpdate);
        ServiceRoom testServiceRoom = serviceRoomList.get(serviceRoomList.size() - 1);
        assertThat(testServiceRoom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceRoom.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingServiceRoom() throws Exception {
        int databaseSizeBeforeUpdate = serviceRoomRepository.findAll().size();

        // Create the ServiceRoom

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceRoomMockMvc.perform(put("/api/service-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRoom)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRoom in the database
        List<ServiceRoom> serviceRoomList = serviceRoomRepository.findAll();
        assertThat(serviceRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteServiceRoom() throws Exception {
        // Initialize the database
        serviceRoomService.save(serviceRoom);

        int databaseSizeBeforeDelete = serviceRoomRepository.findAll().size();

        // Delete the serviceRoom
        restServiceRoomMockMvc.perform(delete("/api/service-rooms/{id}", serviceRoom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceRoom> serviceRoomList = serviceRoomRepository.findAll();
        assertThat(serviceRoomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

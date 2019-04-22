package com.zedelivery.pontoDeVenda.resource;

import com.zedelivery.pontoDeVenda.PontoDeVendaApplication;
import com.zedelivery.pontoDeVenda.domain.Address;
import com.zedelivery.pontoDeVenda.domain.CoverageArea;
import com.zedelivery.pontoDeVenda.domain.Pdv;
import com.zedelivery.pontoDeVenda.repository.PdvRepository;
import com.zedelivery.pontoDeVenda.service.PdvService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PdvResource REST controller.
 *
 * @see PdvResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PontoDeVendaApplication.class)
public class PdvResourceIntTest {

    private static final String DEFAULT_TRADING_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRADING_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT = "BBBBBBBBBB";

    private static final Double DEFAULT_LAT = -43.36556;
    private static final Double UPDATED_LAT = -43.36539;

    private static final Double DEFAULT_LON = -22.99669;
    private static final Double UPDATED_LON = -23.01928;

    private static final Double[] DEFAULT_LAT_LON = {DEFAULT_LAT, DEFAULT_LON};
    private static final Double[] UPDATED_LAT_LON = {UPDATED_LAT, UPDATED_LON};

    private static final Double[][][][] DEFAULT_LIST_LAT_LON = {{{{DEFAULT_LAT, DEFAULT_LON}}}};
    private static final Double[][][][] UPDATED_LIST_LAT_LON = {{{{UPDATED_LAT, UPDATED_LON}}}};

    private static final CoverageArea DEFAULT_COVERAGE_AREA = new CoverageArea().type("AAAAAAAAAA").coordinates(DEFAULT_LIST_LAT_LON);
    private static final CoverageArea UPDATED_COVERAGE_AREA = new CoverageArea().type("BBBBBBBBBB").coordinates(UPDATED_LIST_LAT_LON);

    private static final Address DEFAULT_ADDRESS = new Address().type("AAAAAAAAAA").coordinates(DEFAULT_LAT_LON);
    private static final Address UPDATED_ADDRESS = new Address().type("BBBBBBBBBB").coordinates(UPDATED_LAT_LON);

    @Autowired
    private PdvRepository pdvRepository;

    @Autowired
    private PdvService pdvService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private Validator validator;

    private MockMvc restPdvMockMvc;

    private Pdv pdv;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PdvResource pdvResource = new PdvResource(pdvService);
        this.restPdvMockMvc = MockMvcBuilders.standaloneSetup(pdvResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pdv createEntity() {
        Pdv pdv = new Pdv()
            .tradingName(DEFAULT_TRADING_NAME)
            .ownerName(DEFAULT_OWNER_NAME)
            .document(DEFAULT_DOCUMENT)
            .coverageArea(DEFAULT_COVERAGE_AREA)
            .address(DEFAULT_ADDRESS);
        return pdv;
    }

    @Before
    public void initTest() {
        pdvRepository.deleteAll();
        pdv = createEntity();
    }

    @Test
    public void createPdv() throws Exception {
        int databaseSizeBeforeCreate = pdvRepository.findAll().size();

        // Create the Pdv
        restPdvMockMvc.perform(post("/api/pdvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pdv)))
            .andExpect(status().isCreated());

        // Validate the Pdv in the database
        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeCreate + 1);
        Pdv testPdv = pdvList.get(pdvList.size() - 1);
        assertThat(testPdv.getTradingName()).isEqualTo(DEFAULT_TRADING_NAME);
        assertThat(testPdv.getOwnerName()).isEqualTo(DEFAULT_OWNER_NAME);
        assertThat(testPdv.getDocument()).isEqualTo(DEFAULT_DOCUMENT);
        assertThat(testPdv.getCoverageArea().getType()).isEqualTo(DEFAULT_COVERAGE_AREA.getType());
        assertThat(testPdv.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    public void createPdvWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pdvRepository.findAll().size();

        // Create the Pdv with an existing ID
        pdv.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPdvMockMvc.perform(post("/api/pdvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pdv)))
            .andExpect(status().isBadRequest());

        // Validate the Pdv in the database
        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkTradingNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pdvRepository.findAll().size();
        // set the field null
        pdv.setTradingName(null);

        // Create the Pdv, which fails.

        restPdvMockMvc.perform(post("/api/pdvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pdv)))
            .andExpect(status().isBadRequest());

        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkOwnerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pdvRepository.findAll().size();
        // set the field null
        pdv.setOwnerName(null);

        // Create the Pdv, which fails.

        restPdvMockMvc.perform(post("/api/pdvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pdv)))
            .andExpect(status().isBadRequest());

        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDocumentIsRequired() throws Exception {
        int databaseSizeBeforeTest = pdvRepository.findAll().size();
        // set the field null
        pdv.setDocument(null);

        // Create the Pdv, which fails.

        restPdvMockMvc.perform(post("/api/pdvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pdv)))
            .andExpect(status().isBadRequest());

        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCoverageAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pdvRepository.findAll().size();
        // set the field null
        pdv.setCoverageArea(null);

        // Create the Pdv, which fails.

        restPdvMockMvc.perform(post("/api/pdvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pdv)))
            .andExpect(status().isBadRequest());

        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = pdvRepository.findAll().size();
        // set the field null
        pdv.setAddress(null);

        // Create the Pdv, which fails.

        restPdvMockMvc.perform(post("/api/pdvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pdv)))
            .andExpect(status().isBadRequest());

        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPdvs() throws Exception {
        // Initialize the database
        pdvRepository.save(pdv);

        // Get all the pdvList
        restPdvMockMvc.perform(get("/api/pdvs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pdv.getId())))
            .andExpect(jsonPath("$.[*].tradingName").value(hasItem(DEFAULT_TRADING_NAME.toString())))
            .andExpect(jsonPath("$.[*].ownerName").value(hasItem(DEFAULT_OWNER_NAME.toString())))
            .andExpect(jsonPath("$.[*].document").value(hasItem(DEFAULT_DOCUMENT.toString())));
    }
    
    @Test
    public void getPdv() throws Exception {
        // Initialize the database
        pdvRepository.save(pdv);

        // Get the pdv
        restPdvMockMvc.perform(get("/api/pdvs/{id}", pdv.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pdv.getId()))
            .andExpect(jsonPath("$.tradingName").value(DEFAULT_TRADING_NAME.toString()))
            .andExpect(jsonPath("$.ownerName").value(DEFAULT_OWNER_NAME.toString()))
            .andExpect(jsonPath("$.document").value(DEFAULT_DOCUMENT.toString()));
    }

    @Test
    public void getNonExistingPdv() throws Exception {
        // Get the pdv
        restPdvMockMvc.perform(get("/api/pdvs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePdv() throws Exception {
        // Initialize the database
        pdvService.save(pdv);

        int databaseSizeBeforeUpdate = pdvRepository.findAll().size();

        // Update the pdv
        Pdv updatedPdv = pdvRepository.findById(pdv.getId()).get();
        updatedPdv
            .tradingName(UPDATED_TRADING_NAME)
            .ownerName(UPDATED_OWNER_NAME)
            .document(UPDATED_DOCUMENT)
            .coverageArea(UPDATED_COVERAGE_AREA)
            .address(UPDATED_ADDRESS);

        restPdvMockMvc.perform(put("/api/pdvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPdv)))
            .andExpect(status().isOk());

        // Validate the Pdv in the database
        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeUpdate);
        Pdv testPdv = pdvList.get(pdvList.size() - 1);
        assertThat(testPdv.getTradingName()).isEqualTo(UPDATED_TRADING_NAME);
        assertThat(testPdv.getOwnerName()).isEqualTo(UPDATED_OWNER_NAME);
        assertThat(testPdv.getDocument()).isEqualTo(UPDATED_DOCUMENT);
        assertThat(testPdv.getCoverageArea().getType()).isEqualTo(UPDATED_COVERAGE_AREA.getType());
        assertThat(testPdv.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    public void updateNonExistingPdv() throws Exception {
        int databaseSizeBeforeUpdate = pdvRepository.findAll().size();

        // Create the Pdv

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPdvMockMvc.perform(put("/api/pdvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pdv)))
            .andExpect(status().isNotFound());

        // Validate the Pdv in the database
        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePdv() throws Exception {
        // Initialize the database
        pdvService.save(pdv);

        int databaseSizeBeforeDelete = pdvRepository.findAll().size();

        // Delete the pdv
        restPdvMockMvc.perform(delete("/api/pdvs/{id}", pdv.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pdv.class);
        Pdv pdv1 = new Pdv();
        pdv1.setId("id1");
        Pdv pdv2 = new Pdv();
        pdv2.setId(pdv1.getId());
        assertThat(pdv1).isEqualTo(pdv2);
        pdv2.setId("id2");
        assertThat(pdv1).isNotEqualTo(pdv2);
        pdv1.setId(null);
        assertThat(pdv1).isNotEqualTo(pdv2);
    }
}

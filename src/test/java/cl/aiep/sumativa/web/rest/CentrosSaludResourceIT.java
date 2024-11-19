package cl.aiep.sumativa.web.rest;

import static cl.aiep.sumativa.domain.CentrosSaludAsserts.*;
import static cl.aiep.sumativa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.aiep.sumativa.IntegrationTest;
import cl.aiep.sumativa.domain.CentrosSalud;
import cl.aiep.sumativa.repository.CentrosSaludRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CentrosSaludResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CentrosSaludResourceIT {

    private static final Integer DEFAULT_CENTRO_SALUD_ID = 1;
    private static final Integer UPDATED_CENTRO_SALUD_ID = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_VIGENTE = "A";
    private static final String UPDATED_VIGENTE = "B";

    private static final String ENTITY_API_URL = "/api/centros-saluds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CentrosSaludRepository centrosSaludRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCentrosSaludMockMvc;

    private CentrosSalud centrosSalud;

    private CentrosSalud insertedCentrosSalud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentrosSalud createEntity() {
        return new CentrosSalud()
            .centroSaludID(DEFAULT_CENTRO_SALUD_ID)
            .nombre(DEFAULT_NOMBRE)
            .direccion(DEFAULT_DIRECCION)
            .telefono(DEFAULT_TELEFONO)
            .vigente(DEFAULT_VIGENTE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentrosSalud createUpdatedEntity() {
        return new CentrosSalud()
            .centroSaludID(UPDATED_CENTRO_SALUD_ID)
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .vigente(UPDATED_VIGENTE);
    }

    @BeforeEach
    public void initTest() {
        centrosSalud = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCentrosSalud != null) {
            centrosSaludRepository.delete(insertedCentrosSalud);
            insertedCentrosSalud = null;
        }
    }

    @Test
    @Transactional
    void createCentrosSalud() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CentrosSalud
        var returnedCentrosSalud = om.readValue(
            restCentrosSaludMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centrosSalud))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CentrosSalud.class
        );

        // Validate the CentrosSalud in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCentrosSaludUpdatableFieldsEquals(returnedCentrosSalud, getPersistedCentrosSalud(returnedCentrosSalud));

        insertedCentrosSalud = returnedCentrosSalud;
    }

    @Test
    @Transactional
    void createCentrosSaludWithExistingId() throws Exception {
        // Create the CentrosSalud with an existing ID
        centrosSalud.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentrosSaludMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centrosSalud)))
            .andExpect(status().isBadRequest());

        // Validate the CentrosSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCentroSaludIDIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        centrosSalud.setCentroSaludID(null);

        // Create the CentrosSalud, which fails.

        restCentrosSaludMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centrosSalud)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        centrosSalud.setNombre(null);

        // Create the CentrosSalud, which fails.

        restCentrosSaludMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centrosSalud)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDireccionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        centrosSalud.setDireccion(null);

        // Create the CentrosSalud, which fails.

        restCentrosSaludMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centrosSalud)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCentrosSaluds() throws Exception {
        // Initialize the database
        insertedCentrosSalud = centrosSaludRepository.saveAndFlush(centrosSalud);

        // Get all the centrosSaludList
        restCentrosSaludMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centrosSalud.getId().intValue())))
            .andExpect(jsonPath("$.[*].centroSaludID").value(hasItem(DEFAULT_CENTRO_SALUD_ID)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].vigente").value(hasItem(DEFAULT_VIGENTE)));
    }

    @Test
    @Transactional
    void getCentrosSalud() throws Exception {
        // Initialize the database
        insertedCentrosSalud = centrosSaludRepository.saveAndFlush(centrosSalud);

        // Get the centrosSalud
        restCentrosSaludMockMvc
            .perform(get(ENTITY_API_URL_ID, centrosSalud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centrosSalud.getId().intValue()))
            .andExpect(jsonPath("$.centroSaludID").value(DEFAULT_CENTRO_SALUD_ID))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.vigente").value(DEFAULT_VIGENTE));
    }

    @Test
    @Transactional
    void getNonExistingCentrosSalud() throws Exception {
        // Get the centrosSalud
        restCentrosSaludMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCentrosSalud() throws Exception {
        // Initialize the database
        insertedCentrosSalud = centrosSaludRepository.saveAndFlush(centrosSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centrosSalud
        CentrosSalud updatedCentrosSalud = centrosSaludRepository.findById(centrosSalud.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCentrosSalud are not directly saved in db
        em.detach(updatedCentrosSalud);
        updatedCentrosSalud
            .centroSaludID(UPDATED_CENTRO_SALUD_ID)
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .vigente(UPDATED_VIGENTE);

        restCentrosSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCentrosSalud.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCentrosSalud))
            )
            .andExpect(status().isOk());

        // Validate the CentrosSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCentrosSaludToMatchAllProperties(updatedCentrosSalud);
    }

    @Test
    @Transactional
    void putNonExistingCentrosSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centrosSalud.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentrosSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centrosSalud.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centrosSalud))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentrosSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCentrosSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centrosSalud.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentrosSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centrosSalud))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentrosSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCentrosSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centrosSalud.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentrosSaludMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centrosSalud)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentrosSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCentrosSaludWithPatch() throws Exception {
        // Initialize the database
        insertedCentrosSalud = centrosSaludRepository.saveAndFlush(centrosSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centrosSalud using partial update
        CentrosSalud partialUpdatedCentrosSalud = new CentrosSalud();
        partialUpdatedCentrosSalud.setId(centrosSalud.getId());

        partialUpdatedCentrosSalud.nombre(UPDATED_NOMBRE).vigente(UPDATED_VIGENTE);

        restCentrosSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentrosSalud.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentrosSalud))
            )
            .andExpect(status().isOk());

        // Validate the CentrosSalud in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentrosSaludUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCentrosSalud, centrosSalud),
            getPersistedCentrosSalud(centrosSalud)
        );
    }

    @Test
    @Transactional
    void fullUpdateCentrosSaludWithPatch() throws Exception {
        // Initialize the database
        insertedCentrosSalud = centrosSaludRepository.saveAndFlush(centrosSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centrosSalud using partial update
        CentrosSalud partialUpdatedCentrosSalud = new CentrosSalud();
        partialUpdatedCentrosSalud.setId(centrosSalud.getId());

        partialUpdatedCentrosSalud
            .centroSaludID(UPDATED_CENTRO_SALUD_ID)
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .vigente(UPDATED_VIGENTE);

        restCentrosSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentrosSalud.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentrosSalud))
            )
            .andExpect(status().isOk());

        // Validate the CentrosSalud in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentrosSaludUpdatableFieldsEquals(partialUpdatedCentrosSalud, getPersistedCentrosSalud(partialUpdatedCentrosSalud));
    }

    @Test
    @Transactional
    void patchNonExistingCentrosSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centrosSalud.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentrosSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, centrosSalud.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centrosSalud))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentrosSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCentrosSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centrosSalud.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentrosSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centrosSalud))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentrosSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCentrosSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centrosSalud.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentrosSaludMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(centrosSalud))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentrosSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCentrosSalud() throws Exception {
        // Initialize the database
        insertedCentrosSalud = centrosSaludRepository.saveAndFlush(centrosSalud);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the centrosSalud
        restCentrosSaludMockMvc
            .perform(delete(ENTITY_API_URL_ID, centrosSalud.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return centrosSaludRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected CentrosSalud getPersistedCentrosSalud(CentrosSalud centrosSalud) {
        return centrosSaludRepository.findById(centrosSalud.getId()).orElseThrow();
    }

    protected void assertPersistedCentrosSaludToMatchAllProperties(CentrosSalud expectedCentrosSalud) {
        assertCentrosSaludAllPropertiesEquals(expectedCentrosSalud, getPersistedCentrosSalud(expectedCentrosSalud));
    }

    protected void assertPersistedCentrosSaludToMatchUpdatableProperties(CentrosSalud expectedCentrosSalud) {
        assertCentrosSaludAllUpdatablePropertiesEquals(expectedCentrosSalud, getPersistedCentrosSalud(expectedCentrosSalud));
    }
}

package cl.aiep.sumativa.web.rest;

import static cl.aiep.sumativa.domain.MedicoAsserts.*;
import static cl.aiep.sumativa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.aiep.sumativa.IntegrationTest;
import cl.aiep.sumativa.domain.Medico;
import cl.aiep.sumativa.repository.MedicoRepository;
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
 * Integration tests for the {@link MedicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MedicoResourceIT {

    private static final Integer DEFAULT_MEDICO_ID = 1;
    private static final Integer UPDATED_MEDICO_ID = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_MATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_ESPECIALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CENTRO_SALUD_ID = 1;
    private static final Integer UPDATED_CENTRO_SALUD_ID = 2;

    private static final String ENTITY_API_URL = "/api/medicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicoMockMvc;

    private Medico medico;

    private Medico insertedMedico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medico createEntity() {
        return new Medico()
            .medicoId(DEFAULT_MEDICO_ID)
            .nombre(DEFAULT_NOMBRE)
            .apellidoPaterno(DEFAULT_APELLIDO_PATERNO)
            .apellidoMaterno(DEFAULT_APELLIDO_MATERNO)
            .especialidad(DEFAULT_ESPECIALIDAD)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO)
            .centroSaludId(DEFAULT_CENTRO_SALUD_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medico createUpdatedEntity() {
        return new Medico()
            .medicoId(UPDATED_MEDICO_ID)
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .especialidad(UPDATED_ESPECIALIDAD)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .centroSaludId(UPDATED_CENTRO_SALUD_ID);
    }

    @BeforeEach
    public void initTest() {
        medico = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedMedico != null) {
            medicoRepository.delete(insertedMedico);
            insertedMedico = null;
        }
    }

    @Test
    @Transactional
    void createMedico() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Medico
        var returnedMedico = om.readValue(
            restMedicoMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medico)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Medico.class
        );

        // Validate the Medico in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMedicoUpdatableFieldsEquals(returnedMedico, getPersistedMedico(returnedMedico));

        insertedMedico = returnedMedico;
    }

    @Test
    @Transactional
    void createMedicoWithExistingId() throws Exception {
        // Create the Medico with an existing ID
        medico.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medico)))
            .andExpect(status().isBadRequest());

        // Validate the Medico in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMedicoIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        medico.setMedicoId(null);

        // Create the Medico, which fails.

        restMedicoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medico)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        medico.setNombre(null);

        // Create the Medico, which fails.

        restMedicoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medico)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidoPaternoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        medico.setApellidoPaterno(null);

        // Create the Medico, which fails.

        restMedicoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medico)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        medico.setTelefono(null);

        // Create the Medico, which fails.

        restMedicoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medico)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCorreoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        medico.setCorreo(null);

        // Create the Medico, which fails.

        restMedicoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medico)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCentroSaludIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        medico.setCentroSaludId(null);

        // Create the Medico, which fails.

        restMedicoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medico)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMedicos() throws Exception {
        // Initialize the database
        insertedMedico = medicoRepository.saveAndFlush(medico);

        // Get all the medicoList
        restMedicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medico.getId().intValue())))
            .andExpect(jsonPath("$.[*].medicoId").value(hasItem(DEFAULT_MEDICO_ID)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidoPaterno").value(hasItem(DEFAULT_APELLIDO_PATERNO)))
            .andExpect(jsonPath("$.[*].apellidoMaterno").value(hasItem(DEFAULT_APELLIDO_MATERNO)))
            .andExpect(jsonPath("$.[*].especialidad").value(hasItem(DEFAULT_ESPECIALIDAD)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].centroSaludId").value(hasItem(DEFAULT_CENTRO_SALUD_ID)));
    }

    @Test
    @Transactional
    void getMedico() throws Exception {
        // Initialize the database
        insertedMedico = medicoRepository.saveAndFlush(medico);

        // Get the medico
        restMedicoMockMvc
            .perform(get(ENTITY_API_URL_ID, medico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medico.getId().intValue()))
            .andExpect(jsonPath("$.medicoId").value(DEFAULT_MEDICO_ID))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellidoPaterno").value(DEFAULT_APELLIDO_PATERNO))
            .andExpect(jsonPath("$.apellidoMaterno").value(DEFAULT_APELLIDO_MATERNO))
            .andExpect(jsonPath("$.especialidad").value(DEFAULT_ESPECIALIDAD))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.centroSaludId").value(DEFAULT_CENTRO_SALUD_ID));
    }

    @Test
    @Transactional
    void getNonExistingMedico() throws Exception {
        // Get the medico
        restMedicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMedico() throws Exception {
        // Initialize the database
        insertedMedico = medicoRepository.saveAndFlush(medico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medico
        Medico updatedMedico = medicoRepository.findById(medico.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMedico are not directly saved in db
        em.detach(updatedMedico);
        updatedMedico
            .medicoId(UPDATED_MEDICO_ID)
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .especialidad(UPDATED_ESPECIALIDAD)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .centroSaludId(UPDATED_CENTRO_SALUD_ID);

        restMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMedico.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMedico))
            )
            .andExpect(status().isOk());

        // Validate the Medico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMedicoToMatchAllProperties(updatedMedico);
    }

    @Test
    @Transactional
    void putNonExistingMedico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medico.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(medico))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(medico))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicoMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medico)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedicoWithPatch() throws Exception {
        // Initialize the database
        insertedMedico = medicoRepository.saveAndFlush(medico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medico using partial update
        Medico partialUpdatedMedico = new Medico();
        partialUpdatedMedico.setId(medico.getId());

        partialUpdatedMedico
            .medicoId(UPDATED_MEDICO_ID)
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO);

        restMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedico.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMedico))
            )
            .andExpect(status().isOk());

        // Validate the Medico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMedicoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMedico, medico), getPersistedMedico(medico));
    }

    @Test
    @Transactional
    void fullUpdateMedicoWithPatch() throws Exception {
        // Initialize the database
        insertedMedico = medicoRepository.saveAndFlush(medico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medico using partial update
        Medico partialUpdatedMedico = new Medico();
        partialUpdatedMedico.setId(medico.getId());

        partialUpdatedMedico
            .medicoId(UPDATED_MEDICO_ID)
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .especialidad(UPDATED_ESPECIALIDAD)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .centroSaludId(UPDATED_CENTRO_SALUD_ID);

        restMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedico.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMedico))
            )
            .andExpect(status().isOk());

        // Validate the Medico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMedicoUpdatableFieldsEquals(partialUpdatedMedico, getPersistedMedico(partialUpdatedMedico));
    }

    @Test
    @Transactional
    void patchNonExistingMedico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medico.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(medico))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(medico))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicoMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(medico)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedico() throws Exception {
        // Initialize the database
        insertedMedico = medicoRepository.saveAndFlush(medico);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the medico
        restMedicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, medico.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return medicoRepository.count();
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

    protected Medico getPersistedMedico(Medico medico) {
        return medicoRepository.findById(medico.getId()).orElseThrow();
    }

    protected void assertPersistedMedicoToMatchAllProperties(Medico expectedMedico) {
        assertMedicoAllPropertiesEquals(expectedMedico, getPersistedMedico(expectedMedico));
    }

    protected void assertPersistedMedicoToMatchUpdatableProperties(Medico expectedMedico) {
        assertMedicoAllUpdatablePropertiesEquals(expectedMedico, getPersistedMedico(expectedMedico));
    }
}

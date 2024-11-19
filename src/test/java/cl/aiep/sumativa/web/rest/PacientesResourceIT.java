package cl.aiep.sumativa.web.rest;

import static cl.aiep.sumativa.domain.PacientesAsserts.*;
import static cl.aiep.sumativa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.aiep.sumativa.IntegrationTest;
import cl.aiep.sumativa.domain.Pacientes;
import cl.aiep.sumativa.repository.PacientesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PacientesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PacientesResourceIT {

    private static final Integer DEFAULT_PACIENTE_ID = 1;
    private static final Integer UPDATED_PACIENTE_ID = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_MATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_RUT = "AAAAAAAAAA";
    private static final String UPDATED_RUT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pacientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PacientesRepository pacientesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPacientesMockMvc;

    private Pacientes pacientes;

    private Pacientes insertedPacientes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pacientes createEntity() {
        return new Pacientes()
            .pacienteId(DEFAULT_PACIENTE_ID)
            .nombre(DEFAULT_NOMBRE)
            .apellidoPaterno(DEFAULT_APELLIDO_PATERNO)
            .apellidoMaterno(DEFAULT_APELLIDO_MATERNO)
            .rut(DEFAULT_RUT)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .genero(DEFAULT_GENERO)
            .telefono(DEFAULT_TELEFONO)
            .email(DEFAULT_EMAIL)
            .direccion(DEFAULT_DIRECCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pacientes createUpdatedEntity() {
        return new Pacientes()
            .pacienteId(UPDATED_PACIENTE_ID)
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .rut(UPDATED_RUT)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .genero(UPDATED_GENERO)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
            .direccion(UPDATED_DIRECCION);
    }

    @BeforeEach
    public void initTest() {
        pacientes = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPacientes != null) {
            pacientesRepository.delete(insertedPacientes);
            insertedPacientes = null;
        }
    }

    @Test
    @Transactional
    void createPacientes() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pacientes
        var returnedPacientes = om.readValue(
            restPacientesMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pacientes)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Pacientes.class
        );

        // Validate the Pacientes in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPacientesUpdatableFieldsEquals(returnedPacientes, getPersistedPacientes(returnedPacientes));

        insertedPacientes = returnedPacientes;
    }

    @Test
    @Transactional
    void createPacientesWithExistingId() throws Exception {
        // Create the Pacientes with an existing ID
        pacientes.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacientesMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pacientes)))
            .andExpect(status().isBadRequest());

        // Validate the Pacientes in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPacienteIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pacientes.setPacienteId(null);

        // Create the Pacientes, which fails.

        restPacientesMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pacientes)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPacientes() throws Exception {
        // Initialize the database
        insertedPacientes = pacientesRepository.saveAndFlush(pacientes);

        // Get all the pacientesList
        restPacientesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientes.getId().intValue())))
            .andExpect(jsonPath("$.[*].pacienteId").value(hasItem(DEFAULT_PACIENTE_ID)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidoPaterno").value(hasItem(DEFAULT_APELLIDO_PATERNO)))
            .andExpect(jsonPath("$.[*].apellidoMaterno").value(hasItem(DEFAULT_APELLIDO_MATERNO)))
            .andExpect(jsonPath("$.[*].rut").value(hasItem(DEFAULT_RUT)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)));
    }

    @Test
    @Transactional
    void getPacientes() throws Exception {
        // Initialize the database
        insertedPacientes = pacientesRepository.saveAndFlush(pacientes);

        // Get the pacientes
        restPacientesMockMvc
            .perform(get(ENTITY_API_URL_ID, pacientes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pacientes.getId().intValue()))
            .andExpect(jsonPath("$.pacienteId").value(DEFAULT_PACIENTE_ID))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellidoPaterno").value(DEFAULT_APELLIDO_PATERNO))
            .andExpect(jsonPath("$.apellidoMaterno").value(DEFAULT_APELLIDO_MATERNO))
            .andExpect(jsonPath("$.rut").value(DEFAULT_RUT))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION));
    }

    @Test
    @Transactional
    void getNonExistingPacientes() throws Exception {
        // Get the pacientes
        restPacientesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPacientes() throws Exception {
        // Initialize the database
        insertedPacientes = pacientesRepository.saveAndFlush(pacientes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pacientes
        Pacientes updatedPacientes = pacientesRepository.findById(pacientes.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPacientes are not directly saved in db
        em.detach(updatedPacientes);
        updatedPacientes
            .pacienteId(UPDATED_PACIENTE_ID)
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .rut(UPDATED_RUT)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .genero(UPDATED_GENERO)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
            .direccion(UPDATED_DIRECCION);

        restPacientesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPacientes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPacientes))
            )
            .andExpect(status().isOk());

        // Validate the Pacientes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPacientesToMatchAllProperties(updatedPacientes);
    }

    @Test
    @Transactional
    void putNonExistingPacientes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pacientes.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacientesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pacientes.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pacientes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pacientes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPacientes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pacientes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacientesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pacientes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pacientes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPacientes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pacientes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacientesMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pacientes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pacientes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePacientesWithPatch() throws Exception {
        // Initialize the database
        insertedPacientes = pacientesRepository.saveAndFlush(pacientes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pacientes using partial update
        Pacientes partialUpdatedPacientes = new Pacientes();
        partialUpdatedPacientes.setId(pacientes.getId());

        partialUpdatedPacientes
            .pacienteId(UPDATED_PACIENTE_ID)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .rut(UPDATED_RUT)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .genero(UPDATED_GENERO)
            .email(UPDATED_EMAIL)
            .direccion(UPDATED_DIRECCION);

        restPacientesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPacientes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPacientes))
            )
            .andExpect(status().isOk());

        // Validate the Pacientes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPacientesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPacientes, pacientes),
            getPersistedPacientes(pacientes)
        );
    }

    @Test
    @Transactional
    void fullUpdatePacientesWithPatch() throws Exception {
        // Initialize the database
        insertedPacientes = pacientesRepository.saveAndFlush(pacientes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pacientes using partial update
        Pacientes partialUpdatedPacientes = new Pacientes();
        partialUpdatedPacientes.setId(pacientes.getId());

        partialUpdatedPacientes
            .pacienteId(UPDATED_PACIENTE_ID)
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .rut(UPDATED_RUT)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .genero(UPDATED_GENERO)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL)
            .direccion(UPDATED_DIRECCION);

        restPacientesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPacientes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPacientes))
            )
            .andExpect(status().isOk());

        // Validate the Pacientes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPacientesUpdatableFieldsEquals(partialUpdatedPacientes, getPersistedPacientes(partialUpdatedPacientes));
    }

    @Test
    @Transactional
    void patchNonExistingPacientes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pacientes.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacientesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pacientes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pacientes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pacientes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPacientes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pacientes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacientesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pacientes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pacientes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPacientes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pacientes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacientesMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pacientes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pacientes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePacientes() throws Exception {
        // Initialize the database
        insertedPacientes = pacientesRepository.saveAndFlush(pacientes);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pacientes
        restPacientesMockMvc
            .perform(delete(ENTITY_API_URL_ID, pacientes.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pacientesRepository.count();
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

    protected Pacientes getPersistedPacientes(Pacientes pacientes) {
        return pacientesRepository.findById(pacientes.getId()).orElseThrow();
    }

    protected void assertPersistedPacientesToMatchAllProperties(Pacientes expectedPacientes) {
        assertPacientesAllPropertiesEquals(expectedPacientes, getPersistedPacientes(expectedPacientes));
    }

    protected void assertPersistedPacientesToMatchUpdatableProperties(Pacientes expectedPacientes) {
        assertPacientesAllUpdatablePropertiesEquals(expectedPacientes, getPersistedPacientes(expectedPacientes));
    }
}

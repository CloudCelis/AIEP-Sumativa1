package cl.aiep.sumativa.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PacientesAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPacientesAllPropertiesEquals(Pacientes expected, Pacientes actual) {
        assertPacientesAutoGeneratedPropertiesEquals(expected, actual);
        assertPacientesAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPacientesAllUpdatablePropertiesEquals(Pacientes expected, Pacientes actual) {
        assertPacientesUpdatableFieldsEquals(expected, actual);
        assertPacientesUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPacientesAutoGeneratedPropertiesEquals(Pacientes expected, Pacientes actual) {
        assertThat(expected)
            .as("Verify Pacientes auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPacientesUpdatableFieldsEquals(Pacientes expected, Pacientes actual) {
        assertThat(expected)
            .as("Verify Pacientes relevant properties")
            .satisfies(e -> assertThat(e.getPacienteId()).as("check pacienteId").isEqualTo(actual.getPacienteId()))
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()))
            .satisfies(e -> assertThat(e.getApellidoPaterno()).as("check apellidoPaterno").isEqualTo(actual.getApellidoPaterno()))
            .satisfies(e -> assertThat(e.getApellidoMaterno()).as("check apellidoMaterno").isEqualTo(actual.getApellidoMaterno()))
            .satisfies(e -> assertThat(e.getRut()).as("check rut").isEqualTo(actual.getRut()))
            .satisfies(e -> assertThat(e.getFechaNacimiento()).as("check fechaNacimiento").isEqualTo(actual.getFechaNacimiento()))
            .satisfies(e -> assertThat(e.getGenero()).as("check genero").isEqualTo(actual.getGenero()))
            .satisfies(e -> assertThat(e.getTelefono()).as("check telefono").isEqualTo(actual.getTelefono()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getDireccion()).as("check direccion").isEqualTo(actual.getDireccion()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPacientesUpdatableRelationshipsEquals(Pacientes expected, Pacientes actual) {
        // empty method
    }
}

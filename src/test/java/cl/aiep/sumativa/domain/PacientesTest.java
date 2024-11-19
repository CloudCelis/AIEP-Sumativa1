package cl.aiep.sumativa.domain;

import static cl.aiep.sumativa.domain.PacientesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.aiep.sumativa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PacientesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pacientes.class);
        Pacientes pacientes1 = getPacientesSample1();
        Pacientes pacientes2 = new Pacientes();
        assertThat(pacientes1).isNotEqualTo(pacientes2);

        pacientes2.setId(pacientes1.getId());
        assertThat(pacientes1).isEqualTo(pacientes2);

        pacientes2 = getPacientesSample2();
        assertThat(pacientes1).isNotEqualTo(pacientes2);
    }
}

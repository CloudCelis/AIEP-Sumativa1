package cl.aiep.sumativa.domain;

import static cl.aiep.sumativa.domain.CentrosSaludTestSamples.*;
import static cl.aiep.sumativa.domain.MedicoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.aiep.sumativa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CentrosSaludTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentrosSalud.class);
        CentrosSalud centrosSalud1 = getCentrosSaludSample1();
        CentrosSalud centrosSalud2 = new CentrosSalud();
        assertThat(centrosSalud1).isNotEqualTo(centrosSalud2);

        centrosSalud2.setId(centrosSalud1.getId());
        assertThat(centrosSalud1).isEqualTo(centrosSalud2);

        centrosSalud2 = getCentrosSaludSample2();
        assertThat(centrosSalud1).isNotEqualTo(centrosSalud2);
    }

    @Test
    void medicoTest() {
        CentrosSalud centrosSalud = getCentrosSaludRandomSampleGenerator();
        Medico medicoBack = getMedicoRandomSampleGenerator();

        centrosSalud.setMedico(medicoBack);
        assertThat(centrosSalud.getMedico()).isEqualTo(medicoBack);
        assertThat(medicoBack.getCentrosSalud()).isEqualTo(centrosSalud);

        centrosSalud.medico(null);
        assertThat(centrosSalud.getMedico()).isNull();
        assertThat(medicoBack.getCentrosSalud()).isNull();
    }
}

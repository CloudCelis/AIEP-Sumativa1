package cl.aiep.sumativa.domain;

import static cl.aiep.sumativa.domain.CentrosSaludTestSamples.*;
import static cl.aiep.sumativa.domain.MedicoTestSamples.*;
import static cl.aiep.sumativa.domain.PacientesTestSamples.*;
import static cl.aiep.sumativa.domain.ReservaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.aiep.sumativa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reserva.class);
        Reserva reserva1 = getReservaSample1();
        Reserva reserva2 = new Reserva();
        assertThat(reserva1).isNotEqualTo(reserva2);

        reserva2.setId(reserva1.getId());
        assertThat(reserva1).isEqualTo(reserva2);

        reserva2 = getReservaSample2();
        assertThat(reserva1).isNotEqualTo(reserva2);
    }

    @Test
    void pacientesTest() {
        Reserva reserva = getReservaRandomSampleGenerator();
        Pacientes pacientesBack = getPacientesRandomSampleGenerator();

        reserva.setPacientes(pacientesBack);
        assertThat(reserva.getPacientes()).isEqualTo(pacientesBack);

        reserva.pacientes(null);
        assertThat(reserva.getPacientes()).isNull();
    }

    @Test
    void fkMedicoTest() {
        Reserva reserva = getReservaRandomSampleGenerator();
        Medico medicoBack = getMedicoRandomSampleGenerator();

        reserva.setFkMedico(medicoBack);
        assertThat(reserva.getFkMedico()).isEqualTo(medicoBack);

        reserva.fkMedico(null);
        assertThat(reserva.getFkMedico()).isNull();
    }

    @Test
    void centrosSaludTest() {
        Reserva reserva = getReservaRandomSampleGenerator();
        CentrosSalud centrosSaludBack = getCentrosSaludRandomSampleGenerator();

        reserva.setCentrosSalud(centrosSaludBack);
        assertThat(reserva.getCentrosSalud()).isEqualTo(centrosSaludBack);

        reserva.centrosSalud(null);
        assertThat(reserva.getCentrosSalud()).isNull();
    }
}

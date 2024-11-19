package cl.aiep.sumativa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PacientesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Pacientes getPacientesSample1() {
        return new Pacientes()
            .id(1L)
            .pacienteId(1)
            .nombre("nombre1")
            .apellidoPaterno("apellidoPaterno1")
            .apellidoMaterno("apellidoMaterno1")
            .rut("rut1")
            .genero("genero1")
            .telefono("telefono1")
            .email("email1")
            .direccion("direccion1");
    }

    public static Pacientes getPacientesSample2() {
        return new Pacientes()
            .id(2L)
            .pacienteId(2)
            .nombre("nombre2")
            .apellidoPaterno("apellidoPaterno2")
            .apellidoMaterno("apellidoMaterno2")
            .rut("rut2")
            .genero("genero2")
            .telefono("telefono2")
            .email("email2")
            .direccion("direccion2");
    }

    public static Pacientes getPacientesRandomSampleGenerator() {
        return new Pacientes()
            .id(longCount.incrementAndGet())
            .pacienteId(intCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .apellidoPaterno(UUID.randomUUID().toString())
            .apellidoMaterno(UUID.randomUUID().toString())
            .rut(UUID.randomUUID().toString())
            .genero(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString());
    }
}

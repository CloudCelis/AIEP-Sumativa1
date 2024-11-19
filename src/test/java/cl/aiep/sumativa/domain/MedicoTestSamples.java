package cl.aiep.sumativa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MedicoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Medico getMedicoSample1() {
        return new Medico()
            .id(1L)
            .medicoId(1)
            .nombre("nombre1")
            .apellidoPaterno("apellidoPaterno1")
            .apellidoMaterno("apellidoMaterno1")
            .especialidad("especialidad1")
            .telefono("telefono1")
            .correo("correo1")
            .centroSaludId(1);
    }

    public static Medico getMedicoSample2() {
        return new Medico()
            .id(2L)
            .medicoId(2)
            .nombre("nombre2")
            .apellidoPaterno("apellidoPaterno2")
            .apellidoMaterno("apellidoMaterno2")
            .especialidad("especialidad2")
            .telefono("telefono2")
            .correo("correo2")
            .centroSaludId(2);
    }

    public static Medico getMedicoRandomSampleGenerator() {
        return new Medico()
            .id(longCount.incrementAndGet())
            .medicoId(intCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .apellidoPaterno(UUID.randomUUID().toString())
            .apellidoMaterno(UUID.randomUUID().toString())
            .especialidad(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .correo(UUID.randomUUID().toString())
            .centroSaludId(intCount.incrementAndGet());
    }
}

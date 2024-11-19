package cl.aiep.sumativa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CentrosSaludTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CentrosSalud getCentrosSaludSample1() {
        return new CentrosSalud()
            .id(1L)
            .centroSaludID(1)
            .nombre("nombre1")
            .direccion("direccion1")
            .telefono("telefono1")
            .vigente("vigente1");
    }

    public static CentrosSalud getCentrosSaludSample2() {
        return new CentrosSalud()
            .id(2L)
            .centroSaludID(2)
            .nombre("nombre2")
            .direccion("direccion2")
            .telefono("telefono2")
            .vigente("vigente2");
    }

    public static CentrosSalud getCentrosSaludRandomSampleGenerator() {
        return new CentrosSalud()
            .id(longCount.incrementAndGet())
            .centroSaludID(intCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .vigente(UUID.randomUUID().toString());
    }
}

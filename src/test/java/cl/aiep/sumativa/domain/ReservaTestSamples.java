package cl.aiep.sumativa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ReservaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Reserva getReservaSample1() {
        return new Reserva().id(1L).medico(1).reserva(1).paciente(1).centroSalud(1).estado("estado1");
    }

    public static Reserva getReservaSample2() {
        return new Reserva().id(2L).medico(2).reserva(2).paciente(2).centroSalud(2).estado("estado2");
    }

    public static Reserva getReservaRandomSampleGenerator() {
        return new Reserva()
            .id(longCount.incrementAndGet())
            .medico(intCount.incrementAndGet())
            .reserva(intCount.incrementAndGet())
            .paciente(intCount.incrementAndGet())
            .centroSalud(intCount.incrementAndGet())
            .estado(UUID.randomUUID().toString());
    }
}

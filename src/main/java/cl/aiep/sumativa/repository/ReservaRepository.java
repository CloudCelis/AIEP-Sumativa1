package cl.aiep.sumativa.repository;

import cl.aiep.sumativa.domain.Reserva;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Reserva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {}

package cl.aiep.sumativa.repository;

import cl.aiep.sumativa.domain.Pacientes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pacientes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacientesRepository extends JpaRepository<Pacientes, Long> {}

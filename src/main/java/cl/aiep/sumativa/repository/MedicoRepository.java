package cl.aiep.sumativa.repository;

import cl.aiep.sumativa.domain.Medico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Medico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {}

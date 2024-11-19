package cl.aiep.sumativa.repository;

import cl.aiep.sumativa.domain.CentrosSalud;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CentrosSalud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentrosSaludRepository extends JpaRepository<CentrosSalud, Long> {}

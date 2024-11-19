package cl.aiep.sumativa.service;

import cl.aiep.sumativa.domain.CentrosSalud;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link cl.aiep.sumativa.domain.CentrosSalud}.
 */
public interface CentrosSaludService {
    /**
     * Save a centrosSalud.
     *
     * @param centrosSalud the entity to save.
     * @return the persisted entity.
     */
    CentrosSalud save(CentrosSalud centrosSalud);

    /**
     * Updates a centrosSalud.
     *
     * @param centrosSalud the entity to update.
     * @return the persisted entity.
     */
    CentrosSalud update(CentrosSalud centrosSalud);

    /**
     * Partially updates a centrosSalud.
     *
     * @param centrosSalud the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CentrosSalud> partialUpdate(CentrosSalud centrosSalud);

    /**
     * Get all the centrosSaluds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CentrosSalud> findAll(Pageable pageable);

    /**
     * Get all the CentrosSalud where Medico is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CentrosSalud> findAllWhereMedicoIsNull();

    /**
     * Get the "id" centrosSalud.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CentrosSalud> findOne(Long id);

    /**
     * Delete the "id" centrosSalud.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

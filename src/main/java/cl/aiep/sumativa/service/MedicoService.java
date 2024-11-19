package cl.aiep.sumativa.service;

import cl.aiep.sumativa.domain.Medico;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link cl.aiep.sumativa.domain.Medico}.
 */
public interface MedicoService {
    /**
     * Save a medico.
     *
     * @param medico the entity to save.
     * @return the persisted entity.
     */
    Medico save(Medico medico);

    /**
     * Updates a medico.
     *
     * @param medico the entity to update.
     * @return the persisted entity.
     */
    Medico update(Medico medico);

    /**
     * Partially updates a medico.
     *
     * @param medico the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Medico> partialUpdate(Medico medico);

    /**
     * Get all the medicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Medico> findAll(Pageable pageable);

    /**
     * Get the "id" medico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Medico> findOne(Long id);

    /**
     * Delete the "id" medico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

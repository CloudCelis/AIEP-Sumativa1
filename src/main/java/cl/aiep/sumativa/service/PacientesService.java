package cl.aiep.sumativa.service;

import cl.aiep.sumativa.domain.Pacientes;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link cl.aiep.sumativa.domain.Pacientes}.
 */
public interface PacientesService {
    /**
     * Save a pacientes.
     *
     * @param pacientes the entity to save.
     * @return the persisted entity.
     */
    Pacientes save(Pacientes pacientes);

    /**
     * Updates a pacientes.
     *
     * @param pacientes the entity to update.
     * @return the persisted entity.
     */
    Pacientes update(Pacientes pacientes);

    /**
     * Partially updates a pacientes.
     *
     * @param pacientes the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Pacientes> partialUpdate(Pacientes pacientes);

    /**
     * Get all the pacientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Pacientes> findAll(Pageable pageable);

    /**
     * Get the "id" pacientes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pacientes> findOne(Long id);

    /**
     * Delete the "id" pacientes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

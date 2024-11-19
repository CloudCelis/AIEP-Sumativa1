package cl.aiep.sumativa.service;

import cl.aiep.sumativa.domain.Reserva;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link cl.aiep.sumativa.domain.Reserva}.
 */
public interface ReservaService {
    /**
     * Save a reserva.
     *
     * @param reserva the entity to save.
     * @return the persisted entity.
     */
    Reserva save(Reserva reserva);

    /**
     * Updates a reserva.
     *
     * @param reserva the entity to update.
     * @return the persisted entity.
     */
    Reserva update(Reserva reserva);

    /**
     * Partially updates a reserva.
     *
     * @param reserva the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Reserva> partialUpdate(Reserva reserva);

    /**
     * Get all the reservas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Reserva> findAll(Pageable pageable);

    /**
     * Get the "id" reserva.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Reserva> findOne(Long id);

    /**
     * Delete the "id" reserva.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

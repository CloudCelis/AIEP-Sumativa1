package cl.aiep.sumativa.service.impl;

import cl.aiep.sumativa.domain.Reserva;
import cl.aiep.sumativa.repository.ReservaRepository;
import cl.aiep.sumativa.service.ReservaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.aiep.sumativa.domain.Reserva}.
 */
@Service
@Transactional
public class ReservaServiceImpl implements ReservaService {

    private static final Logger LOG = LoggerFactory.getLogger(ReservaServiceImpl.class);

    private final ReservaRepository reservaRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public Reserva save(Reserva reserva) {
        LOG.debug("Request to save Reserva : {}", reserva);
        return reservaRepository.save(reserva);
    }

    @Override
    public Reserva update(Reserva reserva) {
        LOG.debug("Request to update Reserva : {}", reserva);
        return reservaRepository.save(reserva);
    }

    @Override
    public Optional<Reserva> partialUpdate(Reserva reserva) {
        LOG.debug("Request to partially update Reserva : {}", reserva);

        return reservaRepository
            .findById(reserva.getId())
            .map(existingReserva -> {
                if (reserva.getMedico() != null) {
                    existingReserva.setMedico(reserva.getMedico());
                }
                if (reserva.getReserva() != null) {
                    existingReserva.setReserva(reserva.getReserva());
                }
                if (reserva.getPaciente() != null) {
                    existingReserva.setPaciente(reserva.getPaciente());
                }
                if (reserva.getCentroSalud() != null) {
                    existingReserva.setCentroSalud(reserva.getCentroSalud());
                }
                if (reserva.getFechaHora() != null) {
                    existingReserva.setFechaHora(reserva.getFechaHora());
                }
                if (reserva.getEstado() != null) {
                    existingReserva.setEstado(reserva.getEstado());
                }

                return existingReserva;
            })
            .map(reservaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reserva> findAll(Pageable pageable) {
        LOG.debug("Request to get all Reservas");
        return reservaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reserva> findOne(Long id) {
        LOG.debug("Request to get Reserva : {}", id);
        return reservaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Reserva : {}", id);
        reservaRepository.deleteById(id);
    }
}

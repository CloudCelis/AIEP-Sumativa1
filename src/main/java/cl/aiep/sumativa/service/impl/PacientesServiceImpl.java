package cl.aiep.sumativa.service.impl;

import cl.aiep.sumativa.domain.Pacientes;
import cl.aiep.sumativa.repository.PacientesRepository;
import cl.aiep.sumativa.service.PacientesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.aiep.sumativa.domain.Pacientes}.
 */
@Service
@Transactional
public class PacientesServiceImpl implements PacientesService {

    private static final Logger LOG = LoggerFactory.getLogger(PacientesServiceImpl.class);

    private final PacientesRepository pacientesRepository;

    public PacientesServiceImpl(PacientesRepository pacientesRepository) {
        this.pacientesRepository = pacientesRepository;
    }

    @Override
    public Pacientes save(Pacientes pacientes) {
        LOG.debug("Request to save Pacientes : {}", pacientes);
        return pacientesRepository.save(pacientes);
    }

    @Override
    public Pacientes update(Pacientes pacientes) {
        LOG.debug("Request to update Pacientes : {}", pacientes);
        return pacientesRepository.save(pacientes);
    }

    @Override
    public Optional<Pacientes> partialUpdate(Pacientes pacientes) {
        LOG.debug("Request to partially update Pacientes : {}", pacientes);

        return pacientesRepository
            .findById(pacientes.getId())
            .map(existingPacientes -> {
                if (pacientes.getPacienteId() != null) {
                    existingPacientes.setPacienteId(pacientes.getPacienteId());
                }
                if (pacientes.getNombre() != null) {
                    existingPacientes.setNombre(pacientes.getNombre());
                }
                if (pacientes.getApellidoPaterno() != null) {
                    existingPacientes.setApellidoPaterno(pacientes.getApellidoPaterno());
                }
                if (pacientes.getApellidoMaterno() != null) {
                    existingPacientes.setApellidoMaterno(pacientes.getApellidoMaterno());
                }
                if (pacientes.getRut() != null) {
                    existingPacientes.setRut(pacientes.getRut());
                }
                if (pacientes.getFechaNacimiento() != null) {
                    existingPacientes.setFechaNacimiento(pacientes.getFechaNacimiento());
                }
                if (pacientes.getGenero() != null) {
                    existingPacientes.setGenero(pacientes.getGenero());
                }
                if (pacientes.getTelefono() != null) {
                    existingPacientes.setTelefono(pacientes.getTelefono());
                }
                if (pacientes.getEmail() != null) {
                    existingPacientes.setEmail(pacientes.getEmail());
                }
                if (pacientes.getDireccion() != null) {
                    existingPacientes.setDireccion(pacientes.getDireccion());
                }

                return existingPacientes;
            })
            .map(pacientesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pacientes> findAll(Pageable pageable) {
        LOG.debug("Request to get all Pacientes");
        return pacientesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pacientes> findOne(Long id) {
        LOG.debug("Request to get Pacientes : {}", id);
        return pacientesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Pacientes : {}", id);
        pacientesRepository.deleteById(id);
    }
}

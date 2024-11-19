package cl.aiep.sumativa.service.impl;

import cl.aiep.sumativa.domain.Medico;
import cl.aiep.sumativa.repository.MedicoRepository;
import cl.aiep.sumativa.service.MedicoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.aiep.sumativa.domain.Medico}.
 */
@Service
@Transactional
public class MedicoServiceImpl implements MedicoService {

    private static final Logger LOG = LoggerFactory.getLogger(MedicoServiceImpl.class);

    private final MedicoRepository medicoRepository;

    public MedicoServiceImpl(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    public Medico save(Medico medico) {
        LOG.debug("Request to save Medico : {}", medico);
        return medicoRepository.save(medico);
    }

    @Override
    public Medico update(Medico medico) {
        LOG.debug("Request to update Medico : {}", medico);
        return medicoRepository.save(medico);
    }

    @Override
    public Optional<Medico> partialUpdate(Medico medico) {
        LOG.debug("Request to partially update Medico : {}", medico);

        return medicoRepository
            .findById(medico.getId())
            .map(existingMedico -> {
                if (medico.getMedicoId() != null) {
                    existingMedico.setMedicoId(medico.getMedicoId());
                }
                if (medico.getNombre() != null) {
                    existingMedico.setNombre(medico.getNombre());
                }
                if (medico.getApellidoPaterno() != null) {
                    existingMedico.setApellidoPaterno(medico.getApellidoPaterno());
                }
                if (medico.getApellidoMaterno() != null) {
                    existingMedico.setApellidoMaterno(medico.getApellidoMaterno());
                }
                if (medico.getEspecialidad() != null) {
                    existingMedico.setEspecialidad(medico.getEspecialidad());
                }
                if (medico.getTelefono() != null) {
                    existingMedico.setTelefono(medico.getTelefono());
                }
                if (medico.getCorreo() != null) {
                    existingMedico.setCorreo(medico.getCorreo());
                }
                if (medico.getCentroSaludId() != null) {
                    existingMedico.setCentroSaludId(medico.getCentroSaludId());
                }

                return existingMedico;
            })
            .map(medicoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Medico> findAll(Pageable pageable) {
        LOG.debug("Request to get all Medicos");
        return medicoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medico> findOne(Long id) {
        LOG.debug("Request to get Medico : {}", id);
        return medicoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Medico : {}", id);
        medicoRepository.deleteById(id);
    }
}

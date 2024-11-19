package cl.aiep.sumativa.service.impl;

import cl.aiep.sumativa.domain.CentrosSalud;
import cl.aiep.sumativa.repository.CentrosSaludRepository;
import cl.aiep.sumativa.service.CentrosSaludService;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.aiep.sumativa.domain.CentrosSalud}.
 */
@Service
@Transactional
public class CentrosSaludServiceImpl implements CentrosSaludService {

    private static final Logger LOG = LoggerFactory.getLogger(CentrosSaludServiceImpl.class);

    private final CentrosSaludRepository centrosSaludRepository;

    public CentrosSaludServiceImpl(CentrosSaludRepository centrosSaludRepository) {
        this.centrosSaludRepository = centrosSaludRepository;
    }

    @Override
    public CentrosSalud save(CentrosSalud centrosSalud) {
        LOG.debug("Request to save CentrosSalud : {}", centrosSalud);
        return centrosSaludRepository.save(centrosSalud);
    }

    @Override
    public CentrosSalud update(CentrosSalud centrosSalud) {
        LOG.debug("Request to update CentrosSalud : {}", centrosSalud);
        return centrosSaludRepository.save(centrosSalud);
    }

    @Override
    public Optional<CentrosSalud> partialUpdate(CentrosSalud centrosSalud) {
        LOG.debug("Request to partially update CentrosSalud : {}", centrosSalud);

        return centrosSaludRepository
            .findById(centrosSalud.getId())
            .map(existingCentrosSalud -> {
                if (centrosSalud.getCentroSaludID() != null) {
                    existingCentrosSalud.setCentroSaludID(centrosSalud.getCentroSaludID());
                }
                if (centrosSalud.getNombre() != null) {
                    existingCentrosSalud.setNombre(centrosSalud.getNombre());
                }
                if (centrosSalud.getDireccion() != null) {
                    existingCentrosSalud.setDireccion(centrosSalud.getDireccion());
                }
                if (centrosSalud.getTelefono() != null) {
                    existingCentrosSalud.setTelefono(centrosSalud.getTelefono());
                }
                if (centrosSalud.getVigente() != null) {
                    existingCentrosSalud.setVigente(centrosSalud.getVigente());
                }

                return existingCentrosSalud;
            })
            .map(centrosSaludRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CentrosSalud> findAll(Pageable pageable) {
        LOG.debug("Request to get all CentrosSaluds");
        return centrosSaludRepository.findAll(pageable);
    }

    /**
     *  Get all the centrosSaluds where Medico is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CentrosSalud> findAllWhereMedicoIsNull() {
        LOG.debug("Request to get all centrosSaluds where Medico is null");
        return StreamSupport.stream(centrosSaludRepository.findAll().spliterator(), false)
            .filter(centrosSalud -> centrosSalud.getMedico() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CentrosSalud> findOne(Long id) {
        LOG.debug("Request to get CentrosSalud : {}", id);
        return centrosSaludRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CentrosSalud : {}", id);
        centrosSaludRepository.deleteById(id);
    }
}

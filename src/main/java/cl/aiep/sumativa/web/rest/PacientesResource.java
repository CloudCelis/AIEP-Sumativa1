package cl.aiep.sumativa.web.rest;

import cl.aiep.sumativa.domain.Pacientes;
import cl.aiep.sumativa.repository.PacientesRepository;
import cl.aiep.sumativa.service.PacientesService;
import cl.aiep.sumativa.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cl.aiep.sumativa.domain.Pacientes}.
 */
@RestController
@RequestMapping("/api/pacientes")
public class PacientesResource {

    private static final Logger LOG = LoggerFactory.getLogger(PacientesResource.class);

    private static final String ENTITY_NAME = "pacientes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PacientesService pacientesService;

    private final PacientesRepository pacientesRepository;

    public PacientesResource(PacientesService pacientesService, PacientesRepository pacientesRepository) {
        this.pacientesService = pacientesService;
        this.pacientesRepository = pacientesRepository;
    }

    /**
     * {@code POST  /pacientes} : Create a new pacientes.
     *
     * @param pacientes the pacientes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pacientes, or with status {@code 400 (Bad Request)} if the pacientes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Pacientes> createPacientes(@Valid @RequestBody Pacientes pacientes) throws URISyntaxException {
        LOG.debug("REST request to save Pacientes : {}", pacientes);
        if (pacientes.getId() != null) {
            throw new BadRequestAlertException("A new pacientes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pacientes = pacientesService.save(pacientes);
        return ResponseEntity.created(new URI("/api/pacientes/" + pacientes.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pacientes.getId().toString()))
            .body(pacientes);
    }

    /**
     * {@code PUT  /pacientes/:id} : Updates an existing pacientes.
     *
     * @param id the id of the pacientes to save.
     * @param pacientes the pacientes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pacientes,
     * or with status {@code 400 (Bad Request)} if the pacientes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pacientes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pacientes> updatePacientes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Pacientes pacientes
    ) throws URISyntaxException {
        LOG.debug("REST request to update Pacientes : {}, {}", id, pacientes);
        if (pacientes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pacientes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pacientesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pacientes = pacientesService.update(pacientes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pacientes.getId().toString()))
            .body(pacientes);
    }

    /**
     * {@code PATCH  /pacientes/:id} : Partial updates given fields of an existing pacientes, field will ignore if it is null
     *
     * @param id the id of the pacientes to save.
     * @param pacientes the pacientes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pacientes,
     * or with status {@code 400 (Bad Request)} if the pacientes is not valid,
     * or with status {@code 404 (Not Found)} if the pacientes is not found,
     * or with status {@code 500 (Internal Server Error)} if the pacientes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pacientes> partialUpdatePacientes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Pacientes pacientes
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Pacientes partially : {}, {}", id, pacientes);
        if (pacientes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pacientes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pacientesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pacientes> result = pacientesService.partialUpdate(pacientes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pacientes.getId().toString())
        );
    }

    /**
     * {@code GET  /pacientes} : get all the pacientes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pacientes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Pacientes>> getAllPacientes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Pacientes");
        Page<Pacientes> page = pacientesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pacientes/:id} : get the "id" pacientes.
     *
     * @param id the id of the pacientes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pacientes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pacientes> getPacientes(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Pacientes : {}", id);
        Optional<Pacientes> pacientes = pacientesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pacientes);
    }

    /**
     * {@code DELETE  /pacientes/:id} : delete the "id" pacientes.
     *
     * @param id the id of the pacientes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePacientes(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Pacientes : {}", id);
        pacientesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

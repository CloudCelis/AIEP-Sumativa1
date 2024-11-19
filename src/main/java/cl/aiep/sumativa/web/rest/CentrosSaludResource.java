package cl.aiep.sumativa.web.rest;

import cl.aiep.sumativa.domain.CentrosSalud;
import cl.aiep.sumativa.repository.CentrosSaludRepository;
import cl.aiep.sumativa.service.CentrosSaludService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cl.aiep.sumativa.domain.CentrosSalud}.
 */
@RestController
@RequestMapping("/api/centros-saluds")
public class CentrosSaludResource {

    private static final Logger LOG = LoggerFactory.getLogger(CentrosSaludResource.class);

    private static final String ENTITY_NAME = "centrosSalud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentrosSaludService centrosSaludService;

    private final CentrosSaludRepository centrosSaludRepository;

    public CentrosSaludResource(CentrosSaludService centrosSaludService, CentrosSaludRepository centrosSaludRepository) {
        this.centrosSaludService = centrosSaludService;
        this.centrosSaludRepository = centrosSaludRepository;
    }

    /**
     * {@code POST  /centros-saluds} : Create a new centrosSalud.
     *
     * @param centrosSalud the centrosSalud to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centrosSalud, or with status {@code 400 (Bad Request)} if the centrosSalud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CentrosSalud> createCentrosSalud(@Valid @RequestBody CentrosSalud centrosSalud) throws URISyntaxException {
        LOG.debug("REST request to save CentrosSalud : {}", centrosSalud);
        if (centrosSalud.getId() != null) {
            throw new BadRequestAlertException("A new centrosSalud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        centrosSalud = centrosSaludService.save(centrosSalud);
        return ResponseEntity.created(new URI("/api/centros-saluds/" + centrosSalud.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, centrosSalud.getId().toString()))
            .body(centrosSalud);
    }

    /**
     * {@code PUT  /centros-saluds/:id} : Updates an existing centrosSalud.
     *
     * @param id the id of the centrosSalud to save.
     * @param centrosSalud the centrosSalud to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centrosSalud,
     * or with status {@code 400 (Bad Request)} if the centrosSalud is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centrosSalud couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CentrosSalud> updateCentrosSalud(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CentrosSalud centrosSalud
    ) throws URISyntaxException {
        LOG.debug("REST request to update CentrosSalud : {}, {}", id, centrosSalud);
        if (centrosSalud.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centrosSalud.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centrosSaludRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        centrosSalud = centrosSaludService.update(centrosSalud);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centrosSalud.getId().toString()))
            .body(centrosSalud);
    }

    /**
     * {@code PATCH  /centros-saluds/:id} : Partial updates given fields of an existing centrosSalud, field will ignore if it is null
     *
     * @param id the id of the centrosSalud to save.
     * @param centrosSalud the centrosSalud to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centrosSalud,
     * or with status {@code 400 (Bad Request)} if the centrosSalud is not valid,
     * or with status {@code 404 (Not Found)} if the centrosSalud is not found,
     * or with status {@code 500 (Internal Server Error)} if the centrosSalud couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CentrosSalud> partialUpdateCentrosSalud(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CentrosSalud centrosSalud
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CentrosSalud partially : {}, {}", id, centrosSalud);
        if (centrosSalud.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centrosSalud.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centrosSaludRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CentrosSalud> result = centrosSaludService.partialUpdate(centrosSalud);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centrosSalud.getId().toString())
        );
    }

    /**
     * {@code GET  /centros-saluds} : get all the centrosSaluds.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centrosSaluds in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CentrosSalud>> getAllCentrosSaluds(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("medico-is-null".equals(filter)) {
            LOG.debug("REST request to get all CentrosSaluds where medico is null");
            return new ResponseEntity<>(centrosSaludService.findAllWhereMedicoIsNull(), HttpStatus.OK);
        }
        LOG.debug("REST request to get a page of CentrosSaluds");
        Page<CentrosSalud> page = centrosSaludService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /centros-saluds/:id} : get the "id" centrosSalud.
     *
     * @param id the id of the centrosSalud to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centrosSalud, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CentrosSalud> getCentrosSalud(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CentrosSalud : {}", id);
        Optional<CentrosSalud> centrosSalud = centrosSaludService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centrosSalud);
    }

    /**
     * {@code DELETE  /centros-saluds/:id} : delete the "id" centrosSalud.
     *
     * @param id the id of the centrosSalud to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCentrosSalud(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CentrosSalud : {}", id);
        centrosSaludService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

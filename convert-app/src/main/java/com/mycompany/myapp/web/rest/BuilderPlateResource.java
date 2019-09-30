package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.service.BuilderPlateService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.BuilderPlateDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BuilderPlate.
 */
@RestController
@RequestMapping("/api")
public class BuilderPlateResource {

    private final Logger log = LoggerFactory.getLogger(BuilderPlateResource.class);

    private static final String ENTITY_NAME = "convertappBuilderPlate";

    private final BuilderPlateService builderPlateService;

    public BuilderPlateResource(BuilderPlateService builderPlateService) {
        this.builderPlateService = builderPlateService;
    }

    /**
     * POST  /builder-plates : Create a new builderPlate.
     *
     * @param builderPlateDTO the builderPlateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new builderPlateDTO, or with status 400 (Bad Request) if the builderPlate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/builder-plates")
    public ResponseEntity<BuilderPlateDTO> createBuilderPlate(@RequestBody BuilderPlateDTO builderPlateDTO) throws URISyntaxException {
        log.debug("REST request to save BuilderPlate : {}", builderPlateDTO);
        if (builderPlateDTO.getId() != null) {
            throw new BadRequestAlertException("A new builderPlate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuilderPlateDTO result = builderPlateService.save(builderPlateDTO);
        return ResponseEntity.created(new URI("/api/builder-plates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /builder-plates : Updates an existing builderPlate.
     *
     * @param builderPlateDTO the builderPlateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated builderPlateDTO,
     * or with status 400 (Bad Request) if the builderPlateDTO is not valid,
     * or with status 500 (Internal Server Error) if the builderPlateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/builder-plates")
    public ResponseEntity<BuilderPlateDTO> updateBuilderPlate(@RequestBody BuilderPlateDTO builderPlateDTO) throws URISyntaxException {
        log.debug("REST request to update BuilderPlate : {}", builderPlateDTO);
        if (builderPlateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BuilderPlateDTO result = builderPlateService.save(builderPlateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, builderPlateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /builder-plates : get all the builderPlates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of builderPlates in body
     */
    @GetMapping("/builder-plates")
    public List<BuilderPlateDTO> getAllBuilderPlates() {
        log.debug("REST request to get all BuilderPlates");
        return builderPlateService.findAll();
    }

    /**
     * GET  /builder-plates/:id : get the "id" builderPlate.
     *
     * @param id the id of the builderPlateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the builderPlateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/builder-plates/{id}")
    public ResponseEntity<BuilderPlateDTO> getBuilderPlate(@PathVariable Long id) {
        log.debug("REST request to get BuilderPlate : {}", id);
        Optional<BuilderPlateDTO> builderPlateDTO = builderPlateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(builderPlateDTO);
    }

    /**
     * DELETE  /builder-plates/:id : delete the "id" builderPlate.
     *
     * @param id the id of the builderPlateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/builder-plates/{id}")
    public ResponseEntity<Void> deleteBuilderPlate(@PathVariable Long id) {
        log.debug("REST request to delete BuilderPlate : {}", id);
        builderPlateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

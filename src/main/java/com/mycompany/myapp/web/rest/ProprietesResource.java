package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.service.ProprietesService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.ProprietesDTO;
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
 * REST controller for managing Proprietes.
 */
@RestController
@RequestMapping("/api")
public class ProprietesResource {

    private final Logger log = LoggerFactory.getLogger(ProprietesResource.class);

    private static final String ENTITY_NAME = "convertappProprietes";

    private final ProprietesService proprietesService;

    public ProprietesResource(ProprietesService proprietesService) {
        this.proprietesService = proprietesService;
    }

    /**
     * POST  /proprietes : Create a new proprietes.
     *
     * @param proprietesDTO the proprietesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new proprietesDTO, or with status 400 (Bad Request) if the proprietes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/proprietes")
    public ResponseEntity<ProprietesDTO> createProprietes(@RequestBody ProprietesDTO proprietesDTO) throws URISyntaxException {
        log.debug("REST request to save Proprietes : {}", proprietesDTO);
        if (proprietesDTO.getId() != null) {
            throw new BadRequestAlertException("A new proprietes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProprietesDTO result = proprietesService.save(proprietesDTO);
        return ResponseEntity.created(new URI("/api/proprietes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proprietes : Updates an existing proprietes.
     *
     * @param proprietesDTO the proprietesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated proprietesDTO,
     * or with status 400 (Bad Request) if the proprietesDTO is not valid,
     * or with status 500 (Internal Server Error) if the proprietesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/proprietes")
    public ResponseEntity<ProprietesDTO> updateProprietes(@RequestBody ProprietesDTO proprietesDTO) throws URISyntaxException {
        log.debug("REST request to update Proprietes : {}", proprietesDTO);
        if (proprietesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProprietesDTO result = proprietesService.save(proprietesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, proprietesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proprietes : get all the proprietes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of proprietes in body
     */
    @GetMapping("/proprietes")
    public List<ProprietesDTO> getAllProprietes() {
        log.debug("REST request to get all Proprietes");
        return proprietesService.findAll();
    }

    /**
     * GET  /proprietes/:id : get the "id" proprietes.
     *
     * @param id the id of the proprietesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the proprietesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/proprietes/{id}")
    public ResponseEntity<ProprietesDTO> getProprietes(@PathVariable Long id) {
        log.debug("REST request to get Proprietes : {}", id);
        Optional<ProprietesDTO> proprietesDTO = proprietesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proprietesDTO);
    }

    /**
     * DELETE  /proprietes/:id : delete the "id" proprietes.
     *
     * @param id the id of the proprietesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/proprietes/{id}")
    public ResponseEntity<Void> deleteProprietes(@PathVariable Long id) {
        log.debug("REST request to delete Proprietes : {}", id);
        proprietesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

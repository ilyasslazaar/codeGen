package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.service.FonctionsService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.FonctionsDTO;
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
 * REST controller for managing Fonctions.
 */
@RestController
@RequestMapping("/api")
public class FonctionsResource {

    private final Logger log = LoggerFactory.getLogger(FonctionsResource.class);

    private static final String ENTITY_NAME = "convertappFonctions";

    private final FonctionsService fonctionsService;

    public FonctionsResource(FonctionsService fonctionsService) {
        this.fonctionsService = fonctionsService;
    }

    /**
     * POST  /fonctions : Create a new fonctions.
     *
     * @param fonctionsDTO the fonctionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fonctionsDTO, or with status 400 (Bad Request) if the fonctions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fonctions")
    public ResponseEntity<FonctionsDTO> createFonctions(@RequestBody FonctionsDTO fonctionsDTO) throws URISyntaxException {
        log.debug("REST request to save Fonctions : {}", fonctionsDTO);
        if (fonctionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new fonctions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FonctionsDTO result = fonctionsService.save(fonctionsDTO);
        return ResponseEntity.created(new URI("/api/fonctions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fonctions : Updates an existing fonctions.
     *
     * @param fonctionsDTO the fonctionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fonctionsDTO,
     * or with status 400 (Bad Request) if the fonctionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the fonctionsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fonctions")
    public ResponseEntity<FonctionsDTO> updateFonctions(@RequestBody FonctionsDTO fonctionsDTO) throws URISyntaxException {
        log.debug("REST request to update Fonctions : {}", fonctionsDTO);
        if (fonctionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FonctionsDTO result = fonctionsService.save(fonctionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fonctionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fonctions : get all the fonctions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fonctions in body
     */
    @GetMapping("/fonctions")
    public List<FonctionsDTO> getAllFonctions() {
        log.debug("REST request to get all Fonctions");
        return fonctionsService.findAll();
    }

    /**
     * GET  /fonctions/:id : get the "id" fonctions.
     *
     * @param id the id of the fonctionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fonctionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fonctions/{id}")
    public ResponseEntity<FonctionsDTO> getFonctions(@PathVariable Long id) {
        log.debug("REST request to get Fonctions : {}", id);
        Optional<FonctionsDTO> fonctionsDTO = fonctionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fonctionsDTO);
    }

    /**
     * DELETE  /fonctions/:id : delete the "id" fonctions.
     *
     * @param id the id of the fonctionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fonctions/{id}")
    public ResponseEntity<Void> deleteFonctions(@PathVariable Long id) {
        log.debug("REST request to delete Fonctions : {}", id);
        fonctionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

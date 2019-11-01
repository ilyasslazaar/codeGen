package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.service.ArgumtsService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.ArgumtsDTO;
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
 * REST controller for managing Argumts.
 */
@RestController
@RequestMapping("/api")
public class ArgumtsResource {

    private final Logger log = LoggerFactory.getLogger(ArgumtsResource.class);

    private static final String ENTITY_NAME = "convertappArgumts";

    private final ArgumtsService argumtsService;

    public ArgumtsResource(ArgumtsService argumtsService) {
        this.argumtsService = argumtsService;
    }

    /**
     * POST  /argumts : Create a new argumts.
     *
     * @param argumtsDTO the argumtsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new argumtsDTO, or with status 400 (Bad Request) if the argumts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/argumts")
    public ResponseEntity<ArgumtsDTO> createArgumts(@RequestBody ArgumtsDTO argumtsDTO) throws URISyntaxException {
        log.debug("REST request to save Argumts : {}", argumtsDTO);
        if (argumtsDTO.getId() != null) {
            throw new BadRequestAlertException("A new argumts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArgumtsDTO result = argumtsService.save(argumtsDTO);
        return ResponseEntity.created(new URI("/api/argumts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /argumts : Updates an existing argumts.
     *
     * @param argumtsDTO the argumtsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated argumtsDTO,
     * or with status 400 (Bad Request) if the argumtsDTO is not valid,
     * or with status 500 (Internal Server Error) if the argumtsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/argumts")
    public ResponseEntity<ArgumtsDTO> updateArgumts(@RequestBody ArgumtsDTO argumtsDTO) throws URISyntaxException {
        log.debug("REST request to update Argumts : {}", argumtsDTO);
        if (argumtsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArgumtsDTO result = argumtsService.save(argumtsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, argumtsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /argumts : get all the argumts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of argumts in body
     */
    @GetMapping("/argumts")
    public List<ArgumtsDTO> getAllArgumts() {
        log.debug("REST request to get all Argumts");
        return argumtsService.findAll();
    }

    /**
     * GET  /argumts/:id : get the "id" argumts.
     *
     * @param id the id of the argumtsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the argumtsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/argumts/{id}")
    public ResponseEntity<ArgumtsDTO> getArgumts(@PathVariable Long id) {
        log.debug("REST request to get Argumts : {}", id);
        Optional<ArgumtsDTO> argumtsDTO = argumtsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(argumtsDTO);
    }

    /**
     * DELETE  /argumts/:id : delete the "id" argumts.
     *
     * @param id the id of the argumtsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/argumts/{id}")
    public ResponseEntity<Void> deleteArgumts(@PathVariable Long id) {
        log.debug("REST request to delete Argumts : {}", id);
        argumtsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

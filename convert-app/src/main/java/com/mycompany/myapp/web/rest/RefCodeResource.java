package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.service.RefCodeService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.RefCodeDTO;
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
 * REST controller for managing RefCode.
 */
@RestController
@RequestMapping("/api")
public class RefCodeResource {

    private final Logger log = LoggerFactory.getLogger(RefCodeResource.class);

    private static final String ENTITY_NAME = "convertappRefCode";

    private final RefCodeService refCodeService;

    public RefCodeResource(RefCodeService refCodeService) {
        this.refCodeService = refCodeService;
    }

    /**
     * POST  /ref-codes : Create a new refCode.
     *
     * @param refCodeDTO the refCodeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refCodeDTO, or with status 400 (Bad Request) if the refCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-codes")
    public ResponseEntity<RefCodeDTO> createRefCode(@RequestBody RefCodeDTO refCodeDTO) throws URISyntaxException {
        log.debug("REST request to save RefCode : {}", refCodeDTO);
        if (refCodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new refCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RefCodeDTO result = refCodeService.save(refCodeDTO);
        return ResponseEntity.created(new URI("/api/ref-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-codes : Updates an existing refCode.
     *
     * @param refCodeDTO the refCodeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refCodeDTO,
     * or with status 400 (Bad Request) if the refCodeDTO is not valid,
     * or with status 500 (Internal Server Error) if the refCodeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-codes")
    public ResponseEntity<RefCodeDTO> updateRefCode(@RequestBody RefCodeDTO refCodeDTO) throws URISyntaxException {
        log.debug("REST request to update RefCode : {}", refCodeDTO);
        if (refCodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RefCodeDTO result = refCodeService.save(refCodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refCodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-codes : get all the refCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of refCodes in body
     */
    @GetMapping("/ref-codes")
    public List<RefCodeDTO> getAllRefCodes() {
        log.debug("REST request to get all RefCodes");
        return refCodeService.findAll();
    }

    /**
     * GET  /ref-codes/:id : get the "id" refCode.
     *
     * @param id the id of the refCodeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refCodeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-codes/{id}")
    public ResponseEntity<RefCodeDTO> getRefCode(@PathVariable Long id) {
        log.debug("REST request to get RefCode : {}", id);
        Optional<RefCodeDTO> refCodeDTO = refCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(refCodeDTO);
    }

    /**
     * DELETE  /ref-codes/:id : delete the "id" refCode.
     *
     * @param id the id of the refCodeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-codes/{id}")
    public ResponseEntity<Void> deleteRefCode(@PathVariable Long id) {
        log.debug("REST request to delete RefCode : {}", id);
        refCodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

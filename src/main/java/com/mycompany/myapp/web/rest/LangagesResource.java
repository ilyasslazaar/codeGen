package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.service.LangagesService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.LangagesDTO;
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
 * REST controller for managing Langages.
 */
@RestController
@RequestMapping("/api")
public class LangagesResource {

    private final Logger log = LoggerFactory.getLogger(LangagesResource.class);

    private static final String ENTITY_NAME = "convertappLangages";

    private final LangagesService langagesService;

    public LangagesResource(LangagesService langagesService) {
        this.langagesService = langagesService;
    }

    /**
     * POST  /langages : Create a new langages.
     *
     * @param langagesDTO the langagesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new langagesDTO, or with status 400 (Bad Request) if the langages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/langages")
    public ResponseEntity<LangagesDTO> createLangages(@RequestBody LangagesDTO langagesDTO) throws URISyntaxException {
        log.debug("REST request to save Langages : {}", langagesDTO);
        if (langagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new langages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LangagesDTO result = langagesService.save(langagesDTO);
        return ResponseEntity.created(new URI("/api/langages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /langages : Updates an existing langages.
     *
     * @param langagesDTO the langagesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated langagesDTO,
     * or with status 400 (Bad Request) if the langagesDTO is not valid,
     * or with status 500 (Internal Server Error) if the langagesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/langages")
    public ResponseEntity<LangagesDTO> updateLangages(@RequestBody LangagesDTO langagesDTO) throws URISyntaxException {
        log.debug("REST request to update Langages : {}", langagesDTO);
        if (langagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LangagesDTO result = langagesService.save(langagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, langagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /langages : get all the langages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of langages in body
     */
    @GetMapping("/langages")
    public List<LangagesDTO> getAllLangages() {
        log.debug("REST request to get all Langages");
        return langagesService.findAll();
    }

    /**
     * GET  /langages/:id : get the "id" langages.
     *
     * @param id the id of the langagesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the langagesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/langages/{id}")
    public ResponseEntity<LangagesDTO> getLangages(@PathVariable Long id) {
        log.debug("REST request to get Langages : {}", id);
        Optional<LangagesDTO> langagesDTO = langagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(langagesDTO);
    }

    /**
     * DELETE  /langages/:id : delete the "id" langages.
     *
     * @param id the id of the langagesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/langages/{id}")
    public ResponseEntity<Void> deleteLangages(@PathVariable Long id) {
        log.debug("REST request to delete Langages : {}", id);
        langagesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

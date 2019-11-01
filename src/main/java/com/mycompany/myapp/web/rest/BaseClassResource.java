package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.BaseClassService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.BaseClassDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BaseClass.
 */
@RestController
@RequestMapping("/api")
public class BaseClassResource {

	private final Logger log = LoggerFactory.getLogger(BaseClassResource.class);

	private static final String ENTITY_NAME = "convertappBaseClass";

	private final BaseClassService baseClassService;

	public BaseClassResource(BaseClassService baseClassService) {
		this.baseClassService = baseClassService;
	}

	
	

	/**
	 * PUT /base-classes : Updates an existing baseClass.
	 *
	 * @param baseClassDTO the baseClassDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         baseClassDTO, or with status 400 (Bad Request) if the baseClassDTO is
	 *         not valid, or with status 500 (Internal Server Error) if the
	 *         baseClassDTO couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/base-classes")
	public ResponseEntity<BaseClassDTO> updateBaseClass(@RequestBody BaseClassDTO baseClassDTO)
			throws URISyntaxException {
		log.debug("REST request to update BaseClass : {}", baseClassDTO);
		if (baseClassDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		BaseClassDTO result = baseClassService.save(baseClassDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, baseClassDTO.getId().toString())).body(result);
	}

	/**
	 * GET /base-classes : get all the baseClasses.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of baseClasses
	 *         in body
	 */
	@GetMapping("/base-classes")
	public List<BaseClassDTO> getAllBaseClasses() {
		log.debug("REST request to get all BaseClasses");
		return baseClassService.findAll();
	}

	/**
	 * GET /base-classes/:id : get the "id" baseClass.
	 *
	 * @param id the id of the baseClassDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         baseClassDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/base-classes/{id}")
	public ResponseEntity<BaseClassDTO> getBaseClass(@PathVariable Long id) {
		log.debug("REST request to get BaseClass : {}", id);
		Optional<BaseClassDTO> baseClassDTO = baseClassService.findOne(id);
		return ResponseUtil.wrapOrNotFound(baseClassDTO);
	}

	/**
	 * DELETE /base-classes/:id : delete the "id" baseClass.
	 *
	 * @param id the id of the baseClassDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/base-classes/{id}")
	public ResponseEntity<Void> deleteBaseClass(@PathVariable Long id) {
		log.debug("REST request to delete BaseClass : {}", id);
		baseClassService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}

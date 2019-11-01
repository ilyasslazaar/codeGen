package com.mycompany.myapp.web.rest;

import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.domain.DefaultCode;
import com.mycompany.myapp.service.DefaultCodeService;

/**
 * REST controller for managing BaseClass.
 */
@RestController
@RequestMapping("/api")
public class DefaultCodeResource {

	
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(BaseClassResource.class);
	@SuppressWarnings("unused")
	private static final String ENTITY_NAME = "convertappDefaultCode";
	
	private final DefaultCodeService defaultCodeService;

	public DefaultCodeResource(DefaultCodeService defaultCodeService) {
		super();
		this.defaultCodeService = defaultCodeService;
	}
	
	/**
	 * POST /default-code : Create a new defaultCode.
	 *
	 * @param DefaultCodeDTO the DefaultCodeDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         DefaultCodeDTO, or with status 400 (Bad Request) if the baseClass has
	 *         already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	
	@PostMapping("/default-codes")
	public List<DefaultCode> createDefaultCode(@RequestBody String Fjson ) throws URISyntaxException {
		System.out.println("JSON*******"+Fjson);

		List<DefaultCode> test=defaultCodeService.save(Fjson);
		
		return test;
	}

}

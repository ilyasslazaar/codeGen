package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.BaseClassService;
import com.mycompany.myapp.domain.BaseClass;
import com.mycompany.myapp.repository.BaseClassRepository;
import com.mycompany.myapp.service.dto.BaseClassDTO;
import com.mycompany.myapp.service.mapper.BaseClassMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BaseClass}.
 */
@Service
@Transactional
public class BaseClassServiceImpl implements BaseClassService {

	private final Logger log = LoggerFactory.getLogger(BaseClassServiceImpl.class);

	private final BaseClassRepository baseClassRepository;

	private final BaseClassMapper baseClassMapper;
	


	

	public BaseClassServiceImpl(BaseClassRepository baseClassRepository, BaseClassMapper baseClassMapper) {
		super();
		this.baseClassRepository = baseClassRepository;
		this.baseClassMapper = baseClassMapper;
	}

	/**
	 * Get all the baseClasses.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<BaseClassDTO> findAll() {
		log.debug("Request to get all BaseClasses");
		return baseClassRepository.findAll().stream().map(baseClassMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one baseClass by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<BaseClassDTO> findOne(Long id) {
		log.debug("Request to get BaseClass : {}", id);
		return baseClassRepository.findById(id).map(baseClassMapper::toDto);
	}

	/**
	 * Delete the baseClass by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete BaseClass : {}", id);
		baseClassRepository.deleteById(id);
	}

	@Override
	public BaseClassDTO save(BaseClassDTO baseClassDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

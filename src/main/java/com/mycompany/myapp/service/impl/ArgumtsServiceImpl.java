package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ArgumtsService;
import com.mycompany.myapp.domain.Argumts;
import com.mycompany.myapp.repository.ArgumtsRepository;
import com.mycompany.myapp.service.dto.ArgumtsDTO;
import com.mycompany.myapp.service.mapper.ArgumtsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Argumts.
 */
@Service
@Transactional
public class ArgumtsServiceImpl implements ArgumtsService {

    private final Logger log = LoggerFactory.getLogger(ArgumtsServiceImpl.class);

    private final ArgumtsRepository argumtsRepository;

    private final ArgumtsMapper argumtsMapper;

    public ArgumtsServiceImpl(ArgumtsRepository argumtsRepository, ArgumtsMapper argumtsMapper) {
        this.argumtsRepository = argumtsRepository;
        this.argumtsMapper = argumtsMapper;
    }

    /**
     * Save a argumts.
     *
     * @param argumtsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ArgumtsDTO save(ArgumtsDTO argumtsDTO) {
        log.debug("Request to save Argumts : {}", argumtsDTO);
        Argumts argumts = argumtsMapper.toEntity(argumtsDTO);
        argumts = argumtsRepository.save(argumts);
        return argumtsMapper.toDto(argumts);
    }

    /**
     * Get all the argumts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ArgumtsDTO> findAll() {
        log.debug("Request to get all Argumts");
        return argumtsRepository.findAll().stream()
            .map(argumtsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one argumts by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ArgumtsDTO> findOne(Long id) {
        log.debug("Request to get Argumts : {}", id);
        return argumtsRepository.findById(id)
            .map(argumtsMapper::toDto);
    }

    /**
     * Delete the argumts by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Argumts : {}", id);
        argumtsRepository.deleteById(id);
    }
}

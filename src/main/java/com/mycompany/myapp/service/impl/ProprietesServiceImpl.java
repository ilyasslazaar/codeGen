package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ProprietesService;
import com.mycompany.myapp.domain.Proprietes;
import com.mycompany.myapp.repository.ProprietesRepository;
import com.mycompany.myapp.service.dto.ProprietesDTO;
import com.mycompany.myapp.service.mapper.ProprietesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Proprietes.
 */
@Service
@Transactional
public class ProprietesServiceImpl implements ProprietesService {

    private final Logger log = LoggerFactory.getLogger(ProprietesServiceImpl.class);

    private final ProprietesRepository proprietesRepository;

    private final ProprietesMapper proprietesMapper;

    public ProprietesServiceImpl(ProprietesRepository proprietesRepository, ProprietesMapper proprietesMapper) {
        this.proprietesRepository = proprietesRepository;
        this.proprietesMapper = proprietesMapper;
    }

    /**
     * Save a proprietes.
     *
     * @param proprietesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProprietesDTO save(ProprietesDTO proprietesDTO) {
        log.debug("Request to save Proprietes : {}", proprietesDTO);
        Proprietes proprietes = proprietesMapper.toEntity(proprietesDTO);
        proprietes = proprietesRepository.save(proprietes);
        return proprietesMapper.toDto(proprietes);
    }

    /**
     * Get all the proprietes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProprietesDTO> findAll() {
        log.debug("Request to get all Proprietes");
        return proprietesRepository.findAll().stream()
            .map(proprietesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one proprietes by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProprietesDTO> findOne(Long id) {
        log.debug("Request to get Proprietes : {}", id);
        return proprietesRepository.findById(id)
            .map(proprietesMapper::toDto);
    }

    /**
     * Delete the proprietes by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proprietes : {}", id);
        proprietesRepository.deleteById(id);
    }
}

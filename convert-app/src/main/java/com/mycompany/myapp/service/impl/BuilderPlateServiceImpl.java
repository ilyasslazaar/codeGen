package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.BuilderPlateService;
import com.mycompany.myapp.domain.BuilderPlate;
import com.mycompany.myapp.repository.BuilderPlateRepository;
import com.mycompany.myapp.service.dto.BuilderPlateDTO;
import com.mycompany.myapp.service.mapper.BuilderPlateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BuilderPlate.
 */
@Service
@Transactional
public class BuilderPlateServiceImpl implements BuilderPlateService {

    private final Logger log = LoggerFactory.getLogger(BuilderPlateServiceImpl.class);

    private final BuilderPlateRepository builderPlateRepository;

    private final BuilderPlateMapper builderPlateMapper;

    public BuilderPlateServiceImpl(BuilderPlateRepository builderPlateRepository, BuilderPlateMapper builderPlateMapper) {
        this.builderPlateRepository = builderPlateRepository;
        this.builderPlateMapper = builderPlateMapper;
    }

    /**
     * Save a builderPlate.
     *
     * @param builderPlateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BuilderPlateDTO save(BuilderPlateDTO builderPlateDTO) {
        log.debug("Request to save BuilderPlate : {}", builderPlateDTO);
        BuilderPlate builderPlate = builderPlateMapper.toEntity(builderPlateDTO);
        builderPlate = builderPlateRepository.save(builderPlate);
        return builderPlateMapper.toDto(builderPlate);
    }

    /**
     * Get all the builderPlates.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BuilderPlateDTO> findAll() {
        log.debug("Request to get all BuilderPlates");
        return builderPlateRepository.findAll().stream()
            .map(builderPlateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one builderPlate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BuilderPlateDTO> findOne(Long id) {
        log.debug("Request to get BuilderPlate : {}", id);
        return builderPlateRepository.findById(id)
            .map(builderPlateMapper::toDto);
    }

    /**
     * Delete the builderPlate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BuilderPlate : {}", id);
        builderPlateRepository.deleteById(id);
    }
}

package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.BuilderPlateDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BuilderPlate.
 */
public interface BuilderPlateService {

    /**
     * Save a builderPlate.
     *
     * @param builderPlateDTO the entity to save
     * @return the persisted entity
     */
    BuilderPlateDTO save(BuilderPlateDTO builderPlateDTO);

    /**
     * Get all the builderPlates.
     *
     * @return the list of entities
     */
    List<BuilderPlateDTO> findAll();


    /**
     * Get the "id" builderPlate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BuilderPlateDTO> findOne(Long id);

    /**
     * Delete the "id" builderPlate.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

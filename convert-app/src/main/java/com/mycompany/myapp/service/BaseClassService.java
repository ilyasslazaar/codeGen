package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.BaseClassDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BaseClass.
 */
public interface BaseClassService {

    /**
     * Save a baseClass.
     *
     * @param baseClassDTO the entity to save
     * @return the persisted entity
     */
    BaseClassDTO save(BaseClassDTO baseClassDTO);
    BaseClassDTO save(String c);

    /**
     * Get all the baseClasses.
     *
     * @return the list of entities
     */
    List<BaseClassDTO> findAll();


    /**
     * Get the "id" baseClass.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BaseClassDTO> findOne(Long id);

    /**
     * Delete the "id" baseClass.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

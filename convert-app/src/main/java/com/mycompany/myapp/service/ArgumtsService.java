package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ArgumtsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Argumts.
 */
public interface ArgumtsService {

    /**
     * Save a argumts.
     *
     * @param argumtsDTO the entity to save
     * @return the persisted entity
     */
    ArgumtsDTO save(ArgumtsDTO argumtsDTO);

    /**
     * Get all the argumts.
     *
     * @return the list of entities
     */
    List<ArgumtsDTO> findAll();


    /**
     * Get the "id" argumts.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ArgumtsDTO> findOne(Long id);

    /**
     * Delete the "id" argumts.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

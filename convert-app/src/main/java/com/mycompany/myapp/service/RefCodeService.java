package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.RefCodeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing RefCode.
 */
public interface RefCodeService {

    /**
     * Save a refCode.
     *
     * @param refCodeDTO the entity to save
     * @return the persisted entity
     */
    RefCodeDTO save(RefCodeDTO refCodeDTO);

    /**
     * Get all the refCodes.
     *
     * @return the list of entities
     */
    List<RefCodeDTO> findAll();


    /**
     * Get the "id" refCode.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RefCodeDTO> findOne(Long id);

    /**
     * Delete the "id" refCode.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

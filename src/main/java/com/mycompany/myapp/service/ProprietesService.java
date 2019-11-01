package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ProprietesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Proprietes.
 */
public interface ProprietesService {

    /**
     * Save a proprietes.
     *
     * @param proprietesDTO the entity to save
     * @return the persisted entity
     */
    ProprietesDTO save(ProprietesDTO proprietesDTO);

    /**
     * Get all the proprietes.
     *
     * @return the list of entities
     */
    List<ProprietesDTO> findAll();


    /**
     * Get the "id" proprietes.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProprietesDTO> findOne(Long id);

    /**
     * Delete the "id" proprietes.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

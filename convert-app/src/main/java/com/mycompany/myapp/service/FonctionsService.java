package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.FonctionsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Fonctions.
 */
public interface FonctionsService {

    /**
     * Save a fonctions.
     *
     * @param fonctionsDTO the entity to save
     * @return the persisted entity
     */
    FonctionsDTO save(FonctionsDTO fonctionsDTO);

    /**
     * Get all the fonctions.
     *
     * @return the list of entities
     */
    List<FonctionsDTO> findAll();


    /**
     * Get the "id" fonctions.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FonctionsDTO> findOne(Long id);

    /**
     * Delete the "id" fonctions.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

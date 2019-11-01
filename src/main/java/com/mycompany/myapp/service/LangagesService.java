package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.LangagesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Langages.
 */
public interface LangagesService {

    /**
     * Save a langages.
     *
     * @param langagesDTO the entity to save
     * @return the persisted entity
     */
    LangagesDTO save(LangagesDTO langagesDTO);

    /**
     * Get all the langages.
     *
     * @return the list of entities
     */
    List<LangagesDTO> findAll();


    /**
     * Get the "id" langages.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LangagesDTO> findOne(Long id);

    /**
     * Delete the "id" langages.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

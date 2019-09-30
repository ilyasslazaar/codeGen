package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.FonctionsService;
import com.mycompany.myapp.domain.Fonctions;
import com.mycompany.myapp.repository.FonctionsRepository;
import com.mycompany.myapp.service.dto.FonctionsDTO;
import com.mycompany.myapp.service.mapper.FonctionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Fonctions.
 */
@Service
@Transactional
public class FonctionsServiceImpl implements FonctionsService {

    private final Logger log = LoggerFactory.getLogger(FonctionsServiceImpl.class);

    private final FonctionsRepository fonctionsRepository;

    private final FonctionsMapper fonctionsMapper;

    public FonctionsServiceImpl(FonctionsRepository fonctionsRepository, FonctionsMapper fonctionsMapper) {
        this.fonctionsRepository = fonctionsRepository;
        this.fonctionsMapper = fonctionsMapper;
    }

    /**
     * Save a fonctions.
     *
     * @param fonctionsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FonctionsDTO save(FonctionsDTO fonctionsDTO) {
        log.debug("Request to save Fonctions : {}", fonctionsDTO);
        Fonctions fonctions = fonctionsMapper.toEntity(fonctionsDTO);
        fonctions = fonctionsRepository.save(fonctions);
        return fonctionsMapper.toDto(fonctions);
    }

    /**
     * Get all the fonctions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FonctionsDTO> findAll() {
        log.debug("Request to get all Fonctions");
        return fonctionsRepository.findAll().stream()
            .map(fonctionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one fonctions by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FonctionsDTO> findOne(Long id) {
        log.debug("Request to get Fonctions : {}", id);
        return fonctionsRepository.findById(id)
            .map(fonctionsMapper::toDto);
    }

    /**
     * Delete the fonctions by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fonctions : {}", id);
        fonctionsRepository.deleteById(id);
    }
}

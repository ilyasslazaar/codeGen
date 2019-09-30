package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.LangagesService;
import com.mycompany.myapp.domain.Langages;
import com.mycompany.myapp.repository.LangagesRepository;
import com.mycompany.myapp.service.dto.LangagesDTO;
import com.mycompany.myapp.service.mapper.LangagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Langages.
 */
@Service
@Transactional
public class LangagesServiceImpl implements LangagesService {

    private final Logger log = LoggerFactory.getLogger(LangagesServiceImpl.class);

    private final LangagesRepository langagesRepository;

    private final LangagesMapper langagesMapper;

    public LangagesServiceImpl(LangagesRepository langagesRepository, LangagesMapper langagesMapper) {
        this.langagesRepository = langagesRepository;
        this.langagesMapper = langagesMapper;
    }

    /**
     * Save a langages.
     *
     * @param langagesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LangagesDTO save(LangagesDTO langagesDTO) {
        log.debug("Request to save Langages : {}", langagesDTO);
        Langages langages = langagesMapper.toEntity(langagesDTO);
        langages = langagesRepository.save(langages);
        return langagesMapper.toDto(langages);
    }

    /**
     * Get all the langages.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LangagesDTO> findAll() {
        log.debug("Request to get all Langages");
        return langagesRepository.findAll().stream()
            .map(langagesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one langages by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LangagesDTO> findOne(Long id) {
        log.debug("Request to get Langages : {}", id);
        return langagesRepository.findById(id)
            .map(langagesMapper::toDto);
    }

    /**
     * Delete the langages by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Langages : {}", id);
        langagesRepository.deleteById(id);
    }
}

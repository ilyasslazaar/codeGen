package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.RefCodeService;
import com.mycompany.myapp.domain.RefCode;
import com.mycompany.myapp.repository.RefCodeRepository;
import com.mycompany.myapp.service.dto.RefCodeDTO;
import com.mycompany.myapp.service.mapper.RefCodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RefCode.
 */
@Service
@Transactional
public class RefCodeServiceImpl implements RefCodeService {

    private final Logger log = LoggerFactory.getLogger(RefCodeServiceImpl.class);

    private final RefCodeRepository refCodeRepository;

    private final RefCodeMapper refCodeMapper;

    public RefCodeServiceImpl(RefCodeRepository refCodeRepository, RefCodeMapper refCodeMapper) {
        this.refCodeRepository = refCodeRepository;
        this.refCodeMapper = refCodeMapper;
    }

    /**
     * Save a refCode.
     *
     * @param refCodeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefCodeDTO save(RefCodeDTO refCodeDTO) {
        log.debug("Request to save RefCode : {}", refCodeDTO);
        RefCode refCode = refCodeMapper.toEntity(refCodeDTO);
        refCode = refCodeRepository.save(refCode);
        return refCodeMapper.toDto(refCode);
    }

    /**
     * Get all the refCodes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RefCodeDTO> findAll() {
        log.debug("Request to get all RefCodes");
        return refCodeRepository.findAll().stream()
            .map(refCodeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one refCode by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RefCodeDTO> findOne(Long id) {
        log.debug("Request to get RefCode : {}", id);
        return refCodeRepository.findById(id)
            .map(refCodeMapper::toDto);
    }

    /**
     * Delete the refCode by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefCode : {}", id);
        refCodeRepository.deleteById(id);
    }
}

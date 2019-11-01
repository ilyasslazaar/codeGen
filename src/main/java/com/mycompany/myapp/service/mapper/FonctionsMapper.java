package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.FonctionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Fonctions and its DTO FonctionsDTO.
 */
@Mapper(componentModel = "spring", uses = {BaseClassMapper.class})
public interface FonctionsMapper extends EntityMapper<FonctionsDTO, Fonctions> {

    @Mapping(source = "baseClass.id", target = "baseClassId")
    FonctionsDTO toDto(Fonctions fonctions);

    @Mapping(target = "argumts", ignore = true)
    @Mapping(source = "baseClassId", target = "baseClass")
    Fonctions toEntity(FonctionsDTO fonctionsDTO);

    default Fonctions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fonctions fonctions = new Fonctions();
        fonctions.setId(id);
        return fonctions;
    }
}

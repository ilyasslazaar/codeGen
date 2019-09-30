package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ArgumtsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Argumts and its DTO ArgumtsDTO.
 */
@Mapper(componentModel = "spring", uses = {FonctionsMapper.class})
public interface ArgumtsMapper extends EntityMapper<ArgumtsDTO, Argumts> {

    @Mapping(source = "fonctions.id", target = "fonctionsId")
    ArgumtsDTO toDto(Argumts argumts);

    @Mapping(source = "fonctionsId", target = "fonctions")
    Argumts toEntity(ArgumtsDTO argumtsDTO);

    default Argumts fromId(Long id) {
        if (id == null) {
            return null;
        }
        Argumts argumts = new Argumts();
        argumts.setId(id);
        return argumts;
    }
}

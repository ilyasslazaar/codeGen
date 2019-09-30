package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.LangagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Langages and its DTO LangagesDTO.
 */
@Mapper(componentModel = "spring", uses = {BaseClassMapper.class})
public interface LangagesMapper extends EntityMapper<LangagesDTO, Langages> {

    @Mapping(source = "baseClass.id", target = "baseClassId")
    LangagesDTO toDto(Langages langages);

    @Mapping(source = "baseClassId", target = "baseClass")
    Langages toEntity(LangagesDTO langagesDTO);

    default Langages fromId(Long id) {
        if (id == null) {
            return null;
        }
        Langages langages = new Langages();
        langages.setId(id);
        return langages;
    }
}

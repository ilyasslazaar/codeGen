package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.BaseClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BaseClass and its DTO BaseClassDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BaseClassMapper extends EntityMapper<BaseClassDTO, BaseClass> {


    @Mapping(target = "fonctions", ignore = true)
    @Mapping(target = "langages", ignore = true)
    @Mapping(target = "proprietes", ignore = true)
    @Mapping(target = "builderPlates", ignore = true)
    BaseClass toEntity(BaseClassDTO baseClassDTO);

    default BaseClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        BaseClass baseClass = new BaseClass();
        baseClass.setId(id);
        return baseClass;
    }
}

package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.RefCodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefCode and its DTO RefCodeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefCodeMapper extends EntityMapper<RefCodeDTO, RefCode> {


    @Mapping(target = "builderPlates", ignore = true)
    RefCode toEntity(RefCodeDTO refCodeDTO);

    default RefCode fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefCode refCode = new RefCode();
        refCode.setId(id);
        return refCode;
    }
}

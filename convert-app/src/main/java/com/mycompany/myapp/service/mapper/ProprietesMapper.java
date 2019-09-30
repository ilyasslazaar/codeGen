package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ProprietesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Proprietes and its DTO ProprietesDTO.
 */
@Mapper(componentModel = "spring", uses = {BaseClassMapper.class})
public interface ProprietesMapper extends EntityMapper<ProprietesDTO, Proprietes> {

    @Mapping(source = "baseClass.id", target = "baseClassId")
    ProprietesDTO toDto(Proprietes proprietes);

    @Mapping(source = "baseClassId", target = "baseClass")
    Proprietes toEntity(ProprietesDTO proprietesDTO);

    default Proprietes fromId(Long id) {
        if (id == null) {
            return null;
        }
        Proprietes proprietes = new Proprietes();
        proprietes.setId(id);
        return proprietes;
    }
}

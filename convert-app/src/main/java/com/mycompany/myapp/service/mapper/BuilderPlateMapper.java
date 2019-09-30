package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.BuilderPlateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BuilderPlate and its DTO BuilderPlateDTO.
 */
@Mapper(componentModel = "spring", uses = {BaseClassMapper.class, RefCodeMapper.class})
public interface BuilderPlateMapper extends EntityMapper<BuilderPlateDTO, BuilderPlate> {

    @Mapping(source = "baseClass.id", target = "baseClassId")
    @Mapping(source = "refCode.id", target = "refCodeId")
    BuilderPlateDTO toDto(BuilderPlate builderPlate);

    @Mapping(source = "baseClassId", target = "baseClass")
    @Mapping(source = "refCodeId", target = "refCode")
    BuilderPlate toEntity(BuilderPlateDTO builderPlateDTO);

    default BuilderPlate fromId(Long id) {
        if (id == null) {
            return null;
        }
        BuilderPlate builderPlate = new BuilderPlate();
        builderPlate.setId(id);
        return builderPlate;
    }
}

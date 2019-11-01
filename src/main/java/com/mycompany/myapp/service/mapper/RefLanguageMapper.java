package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.RefLanguageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefLanguage and its DTO RefLanguageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefLanguageMapper extends EntityMapper<RefLanguageDTO, RefLanguage> {



    default RefLanguage fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefLanguage refLanguage = new RefLanguage();
        refLanguage.setId(id);
        return refLanguage;
    }
}

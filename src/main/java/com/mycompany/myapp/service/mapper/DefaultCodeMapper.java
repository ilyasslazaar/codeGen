package com.mycompany.myapp.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mycompany.myapp.domain.DefaultCode;
import com.mycompany.myapp.service.dto.DefaultCodeDTO;

@Mapper(componentModel = "spring", uses = {RefLanguageMapper.class})

public interface DefaultCodeMapper  extends EntityMapper<DefaultCodeDTO, DefaultCode>{
	
	@Mapping(source = "refLanguage.id", target = "refLangId")
	DefaultCodeDTO toDto(DefaultCode defaultCode);
    @Mapping(source = "refLangId", target = "refLanguage")
    DefaultCode toEntity(DefaultCodeDTO defaultCodeDTO);
    
    default DefaultCode fromId(Long id) {
        if (id == null) {
            return null;
        }
        DefaultCode defaultCode = new DefaultCode();
        defaultCode.setId(id);
        return defaultCode;
    }
    
}

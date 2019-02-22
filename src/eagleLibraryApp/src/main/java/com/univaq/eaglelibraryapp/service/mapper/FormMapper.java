package com.univaq.eaglelibraryapp.service.mapper;

import com.univaq.eaglelibraryapp.domain.*;
import com.univaq.eaglelibraryapp.service.dto.FormDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Form and its DTO FormDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface FormMapper extends EntityMapper<FormDTO, Form> {

    @Mapping(source = "user.id", target = "userId")
    FormDTO toDto(Form form);

    @Mapping(source = "userId", target = "user")
    Form toEntity(FormDTO formDTO);

    default Form fromId(Long id) {
        if (id == null) {
            return null;
        }
        Form form = new Form();
        form.setId(id);
        return form;
    }
}

package com.univaq.eaglelibraryapp.service.mapper;

import com.univaq.eaglelibraryapp.domain.*;
import com.univaq.eaglelibraryapp.service.dto.Literary_workDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Literary_work and its DTO Literary_workDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Literary_workMapper extends EntityMapper<Literary_workDTO, Literary_work> {



    default Literary_work fromId(Long id) {
        if (id == null) {
            return null;
        }
        Literary_work literary_work = new Literary_work();
        literary_work.setId(id);
        return literary_work;
    }
}

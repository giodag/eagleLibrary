package com.univaq.eaglelibraryapp.service.mapper;

import com.univaq.eaglelibraryapp.domain.*;
import com.univaq.eaglelibraryapp.service.dto.PageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Page and its DTO PageDTO.
 */
@Mapper(componentModel = "spring", uses = {Literary_workMapper.class})
public interface PageMapper extends EntityMapper<PageDTO, Page> {

    @Mapping(source = "literary_work.id", target = "literary_workId")
    PageDTO toDto(Page page);

    @Mapping(source = "literary_workId", target = "literary_work")
    Page toEntity(PageDTO pageDTO);

    default Page fromId(Long id) {
        if (id == null) {
            return null;
        }
        Page page = new Page();
        page.setId(id);
        return page;
    }
}

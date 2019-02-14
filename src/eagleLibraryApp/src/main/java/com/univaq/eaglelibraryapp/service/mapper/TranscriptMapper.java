package com.univaq.eaglelibraryapp.service.mapper;

import com.univaq.eaglelibraryapp.domain.*;
import com.univaq.eaglelibraryapp.service.dto.TranscriptDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Transcript and its DTO TranscriptDTO.
 */
@Mapper(componentModel = "spring", uses = {PageMapper.class})
public interface TranscriptMapper extends EntityMapper<TranscriptDTO, Transcript> {

    @Mapping(source = "page.id", target = "pageId")
    @Mapping(source = "page.number", target = "pageNumber")
    TranscriptDTO toDto(Transcript transcript);

    @Mapping(source = "pageId", target = "page")
    Transcript toEntity(TranscriptDTO transcriptDTO);

    default Transcript fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transcript transcript = new Transcript();
        transcript.setId(id);
        return transcript;
    }
}

package com.hruthvik.jobtracker.mapper;

import com.hruthvik.jobtracker.dto.request.NoteRequest;
import com.hruthvik.jobtracker.dto.response.NoteResponse;
import com.hruthvik.jobtracker.entity.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "jobApplication", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Note toEntity(NoteRequest request);

    NoteResponse toResponse(Note entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "jobApplication", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(NoteRequest request, @MappingTarget Note entity);
}

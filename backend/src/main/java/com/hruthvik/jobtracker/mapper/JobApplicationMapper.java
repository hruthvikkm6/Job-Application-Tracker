package com.hruthvik.jobtracker.mapper;

import com.hruthvik.jobtracker.dto.request.JobApplicationRequest;
import com.hruthvik.jobtracker.dto.response.JobApplicationResponse;
import com.hruthvik.jobtracker.entity.JobApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ResumeMapper.class})
public interface JobApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "resume", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "reminders", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    JobApplication toEntity(JobApplicationRequest request);

    JobApplicationResponse toResponse(JobApplication entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "resume", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "reminders", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(JobApplicationRequest request, @MappingTarget JobApplication entity);
}

package com.hruthvik.jobtracker.mapper;

import com.hruthvik.jobtracker.dto.response.ResumeResponse;
import com.hruthvik.jobtracker.entity.Resume;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    ResumeResponse toResponse(Resume resume);
}

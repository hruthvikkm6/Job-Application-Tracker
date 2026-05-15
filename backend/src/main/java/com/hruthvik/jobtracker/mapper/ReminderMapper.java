package com.hruthvik.jobtracker.mapper;

import com.hruthvik.jobtracker.dto.request.ReminderRequest;
import com.hruthvik.jobtracker.dto.response.ReminderResponse;
import com.hruthvik.jobtracker.entity.Reminder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReminderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "jobApplication", ignore = true)
    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Reminder toEntity(ReminderRequest request);

    @Mapping(target = "jobApplicationId", source = "jobApplication.id")
    @Mapping(target = "companyName", source = "jobApplication.companyName")
    ReminderResponse toResponse(Reminder entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "jobApplication", ignore = true)
    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(ReminderRequest request, @MappingTarget Reminder entity);
}

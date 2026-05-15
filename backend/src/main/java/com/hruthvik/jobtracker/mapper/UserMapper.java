package com.hruthvik.jobtracker.mapper;

import com.hruthvik.jobtracker.dto.response.UserResponse;
import com.hruthvik.jobtracker.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
}

package com.hruthvik.jobtracker.mapper;

import com.hruthvik.jobtracker.dto.response.UserResponse;
import com.hruthvik.jobtracker.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T14:42:00+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.email( user.getEmail() );
        userResponse.fullName( user.getFullName() );
        userResponse.role( user.getRole() );

        return userResponse.build();
    }
}

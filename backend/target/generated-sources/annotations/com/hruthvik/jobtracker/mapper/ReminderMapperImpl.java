package com.hruthvik.jobtracker.mapper;

import com.hruthvik.jobtracker.dto.request.ReminderRequest;
import com.hruthvik.jobtracker.dto.response.ReminderResponse;
import com.hruthvik.jobtracker.entity.JobApplication;
import com.hruthvik.jobtracker.entity.Reminder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T14:42:00+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class ReminderMapperImpl implements ReminderMapper {

    @Override
    public Reminder toEntity(ReminderRequest request) {
        if ( request == null ) {
            return null;
        }

        Reminder.ReminderBuilder<?, ?> reminder = Reminder.builder();

        reminder.message( request.getMessage() );
        reminder.reminderDate( request.getReminderDate() );

        return reminder.build();
    }

    @Override
    public ReminderResponse toResponse(Reminder entity) {
        if ( entity == null ) {
            return null;
        }

        ReminderResponse.ReminderResponseBuilder reminderResponse = ReminderResponse.builder();

        reminderResponse.jobApplicationId( entityJobApplicationId( entity ) );
        reminderResponse.companyName( entityJobApplicationCompanyName( entity ) );
        reminderResponse.id( entity.getId() );
        reminderResponse.message( entity.getMessage() );
        reminderResponse.reminderDate( entity.getReminderDate() );
        reminderResponse.completed( entity.isCompleted() );
        reminderResponse.createdAt( entity.getCreatedAt() );

        return reminderResponse.build();
    }

    @Override
    public void updateEntity(ReminderRequest request, Reminder entity) {
        if ( request == null ) {
            return;
        }

        entity.setMessage( request.getMessage() );
        entity.setReminderDate( request.getReminderDate() );
    }

    private Long entityJobApplicationId(Reminder reminder) {
        if ( reminder == null ) {
            return null;
        }
        JobApplication jobApplication = reminder.getJobApplication();
        if ( jobApplication == null ) {
            return null;
        }
        Long id = jobApplication.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityJobApplicationCompanyName(Reminder reminder) {
        if ( reminder == null ) {
            return null;
        }
        JobApplication jobApplication = reminder.getJobApplication();
        if ( jobApplication == null ) {
            return null;
        }
        String companyName = jobApplication.getCompanyName();
        if ( companyName == null ) {
            return null;
        }
        return companyName;
    }
}

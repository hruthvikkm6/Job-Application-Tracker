package com.hruthvik.jobtracker.mapper;

import com.hruthvik.jobtracker.dto.request.JobApplicationRequest;
import com.hruthvik.jobtracker.dto.response.JobApplicationResponse;
import com.hruthvik.jobtracker.entity.JobApplication;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T14:42:00+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class JobApplicationMapperImpl implements JobApplicationMapper {

    @Autowired
    private ResumeMapper resumeMapper;

    @Override
    public JobApplication toEntity(JobApplicationRequest request) {
        if ( request == null ) {
            return null;
        }

        JobApplication.JobApplicationBuilder<?, ?> jobApplication = JobApplication.builder();

        jobApplication.companyName( request.getCompanyName() );
        jobApplication.jobTitle( request.getJobTitle() );
        jobApplication.jobDescription( request.getJobDescription() );
        jobApplication.applicationUrl( request.getApplicationUrl() );
        jobApplication.salary( request.getSalary() );
        jobApplication.location( request.getLocation() );
        jobApplication.jobType( request.getJobType() );
        jobApplication.source( request.getSource() );
        jobApplication.status( request.getStatus() );
        jobApplication.dateApplied( request.getDateApplied() );

        return jobApplication.build();
    }

    @Override
    public JobApplicationResponse toResponse(JobApplication entity) {
        if ( entity == null ) {
            return null;
        }

        JobApplicationResponse.JobApplicationResponseBuilder jobApplicationResponse = JobApplicationResponse.builder();

        jobApplicationResponse.id( entity.getId() );
        jobApplicationResponse.companyName( entity.getCompanyName() );
        jobApplicationResponse.jobTitle( entity.getJobTitle() );
        jobApplicationResponse.jobDescription( entity.getJobDescription() );
        jobApplicationResponse.applicationUrl( entity.getApplicationUrl() );
        jobApplicationResponse.salary( entity.getSalary() );
        jobApplicationResponse.location( entity.getLocation() );
        jobApplicationResponse.jobType( entity.getJobType() );
        jobApplicationResponse.source( entity.getSource() );
        jobApplicationResponse.status( entity.getStatus() );
        jobApplicationResponse.dateApplied( entity.getDateApplied() );
        jobApplicationResponse.resume( resumeMapper.toResponse( entity.getResume() ) );
        jobApplicationResponse.createdAt( entity.getCreatedAt() );
        jobApplicationResponse.updatedAt( entity.getUpdatedAt() );

        return jobApplicationResponse.build();
    }

    @Override
    public void updateEntity(JobApplicationRequest request, JobApplication entity) {
        if ( request == null ) {
            return;
        }

        entity.setCompanyName( request.getCompanyName() );
        entity.setJobTitle( request.getJobTitle() );
        entity.setJobDescription( request.getJobDescription() );
        entity.setApplicationUrl( request.getApplicationUrl() );
        entity.setSalary( request.getSalary() );
        entity.setLocation( request.getLocation() );
        entity.setJobType( request.getJobType() );
        entity.setSource( request.getSource() );
        entity.setStatus( request.getStatus() );
        entity.setDateApplied( request.getDateApplied() );
    }
}

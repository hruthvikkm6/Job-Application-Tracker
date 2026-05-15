package com.hruthvik.jobtracker.service;

import com.hruthvik.jobtracker.dto.request.JobApplicationRequest;
import com.hruthvik.jobtracker.dto.response.JobApplicationResponse;
import com.hruthvik.jobtracker.entity.JobStatus;
import com.hruthvik.jobtracker.entity.Source;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface JobApplicationService {
    JobApplicationResponse create(JobApplicationRequest request);
    JobApplicationResponse update(Long id, JobApplicationRequest request);
    void delete(Long id);
    JobApplicationResponse getById(Long id);
    Page<JobApplicationResponse> getAll(
            String company,
            String title,
            JobStatus status,
            Source source,
            String location,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}

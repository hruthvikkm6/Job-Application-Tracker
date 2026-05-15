package com.hruthvik.jobtracker.dto.request;

import com.hruthvik.jobtracker.entity.JobStatus;
import com.hruthvik.jobtracker.entity.JobType;
import com.hruthvik.jobtracker.entity.Source;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    private String jobDescription;

    private String applicationUrl;

    private String salary;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Job type is required")
    private JobType jobType;

    @NotNull(message = "Source is required")
    private Source source;

    @NotNull(message = "Status is required")
    private JobStatus status;

    @NotNull(message = "Date applied is required")
    private LocalDate dateApplied;

    private Long resumeId;
}

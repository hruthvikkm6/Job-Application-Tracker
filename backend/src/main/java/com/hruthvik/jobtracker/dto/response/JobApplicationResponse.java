package com.hruthvik.jobtracker.dto.response;

import com.hruthvik.jobtracker.entity.JobStatus;
import com.hruthvik.jobtracker.entity.JobType;
import com.hruthvik.jobtracker.entity.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationResponse {
    private Long id;
    private String companyName;
    private String jobTitle;
    private String jobDescription;
    private String applicationUrl;
    private String salary;
    private String location;
    private JobType jobType;
    private Source source;
    private JobStatus status;
    private LocalDate dateApplied;
    private ResumeResponse resume;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

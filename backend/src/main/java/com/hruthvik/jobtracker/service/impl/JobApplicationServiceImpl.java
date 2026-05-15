package com.hruthvik.jobtracker.service.impl;

import com.hruthvik.jobtracker.dto.request.JobApplicationRequest;
import com.hruthvik.jobtracker.dto.response.JobApplicationResponse;
import com.hruthvik.jobtracker.entity.*;
import com.hruthvik.jobtracker.exception.ResourceNotFoundException;
import com.hruthvik.jobtracker.mapper.JobApplicationMapper;
import com.hruthvik.jobtracker.repository.JobApplicationRepository;
import com.hruthvik.jobtracker.repository.ResumeRepository;
import com.hruthvik.jobtracker.service.AuthService;
import com.hruthvik.jobtracker.service.JobApplicationService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository repository;
    private final JobApplicationMapper mapper;
    private final AuthService authService;
    private final ResumeRepository resumeRepository;

    @Override
    @Transactional
    public JobApplicationResponse create(JobApplicationRequest request) {
        User user = authService.getCurrentUser();
        JobApplication entity = mapper.toEntity(request);
        entity.setUser(user);

        if (request.getResumeId() != null) {
            Resume resume = resumeRepository.findByIdAndUser(request.getResumeId(), user)
                    .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));
            entity.setResume(resume);
        }

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public JobApplicationResponse update(Long id, JobApplicationRequest request) {
        User user = authService.getCurrentUser();
        JobApplication entity = repository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found"));

        mapper.updateEntity(request, entity);

        if (request.getResumeId() != null) {
            Resume resume = resumeRepository.findByIdAndUser(request.getResumeId(), user)
                    .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));
            entity.setResume(resume);
        } else {
            entity.setResume(null);
        }

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = authService.getCurrentUser();
        JobApplication entity = repository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found"));
        repository.delete(entity);
    }

    @Override
    public JobApplicationResponse getById(Long id) {
        User user = authService.getCurrentUser();
        JobApplication entity = repository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found"));
        return mapper.toResponse(entity);
    }

    @Override
    public Page<JobApplicationResponse> getAll(
            String company,
            String title,
            JobStatus status,
            Source source,
            String location,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    ) {
        User user = authService.getCurrentUser();
        Specification<JobApplication> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("user"), user));

            if (company != null && !company.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("companyName")), "%" + company.toLowerCase() + "%"));
            }
            if (title != null && !title.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("jobTitle")), "%" + title.toLowerCase() + "%"));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (source != null) {
                predicates.add(cb.equal(root.get("source"), source));
            }
            if (location != null && !location.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%"));
            }
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateApplied"), startDate));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateApplied"), endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return repository.findAll(spec, pageable).map(mapper::toResponse);
    }
}

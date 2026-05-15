package com.hruthvik.jobtracker.controller;

import com.hruthvik.jobtracker.dto.request.JobApplicationRequest;
import com.hruthvik.jobtracker.dto.response.JobApplicationResponse;
import com.hruthvik.jobtracker.entity.JobStatus;
import com.hruthvik.jobtracker.entity.Source;
import com.hruthvik.jobtracker.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService service;

    @PostMapping
    public ResponseEntity<JobApplicationResponse> create(@Valid @RequestBody JobApplicationRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<JobApplicationResponse>> getAll(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) JobStatus status,
            @RequestParam(required = false) Source source,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @PageableDefault(size = 10, sort = "dateApplied") Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAll(company, title, status, source, location, startDate, endDate, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> update(@PathVariable Long id, @Valid @RequestBody JobApplicationRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

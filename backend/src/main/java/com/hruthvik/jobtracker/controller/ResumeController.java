package com.hruthvik.jobtracker.controller;

import com.hruthvik.jobtracker.dto.response.ResumeResponse;
import com.hruthvik.jobtracker.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private static final String UPLOAD_DIR = "uploads/resumes";

    @PostMapping("/upload")
    public ResponseEntity<ResumeResponse> uploadResume(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return new ResponseEntity<>(
                resumeService.uploadResume(file),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<ResumeResponse>> getMyResumes() {
        return ResponseEntity.ok(resumeService.getMyResumes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long id)
            throws IOException {
        resumeService.deleteResume(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view/{filename:.+}")
    public ResponseEntity<Resource> viewResume(
            @PathVariable String filename
    ) throws MalformedURLException {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename(filename)
                                .build()
                                .toString()
                )
                .body(resource);
    }
}
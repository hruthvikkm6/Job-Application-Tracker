package com.hruthvik.jobtracker.service.impl;

import com.hruthvik.jobtracker.dto.response.ResumeResponse;
import com.hruthvik.jobtracker.entity.Resume;
import com.hruthvik.jobtracker.entity.User;
import com.hruthvik.jobtracker.exception.ResourceNotFoundException;
import com.hruthvik.jobtracker.mapper.ResumeMapper;
import com.hruthvik.jobtracker.repository.ResumeRepository;
import com.hruthvik.jobtracker.service.AuthService;
import com.hruthvik.jobtracker.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository repository;
    private final ResumeMapper mapper;
    private final AuthService authService;

    private static final String UPLOAD_DIR = "uploads/resumes";

    @Override
    @Transactional
    public ResumeResponse uploadResume(MultipartFile file) throws IOException {
        User user = authService.getCurrentUser();

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        if (file.getContentType() == null ||
                !file.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String safeFilename = UUID.randomUUID() + "_" + originalFilename;

        Path filePath = uploadPath.resolve(safeFilename);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        Resume resume = Resume.builder()
                .originalFilename(originalFilename)
                .url("http://localhost:8080/api/resumes/view/" + safeFilename)
                .publicId(safeFilename)
                .version("local")
                .user(user)
                .build();

        return mapper.toResponse(repository.save(resume));
    }

    @Override
    public List<ResumeResponse> getMyResumes() {
        User user = authService.getCurrentUser();

        return repository.findAllByUser(user)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteResume(Long id) throws IOException {
        User user = authService.getCurrentUser();

        Resume resume = repository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Resume not found"));

        Path filePath = Paths.get(UPLOAD_DIR, resume.getPublicId());

        Files.deleteIfExists(filePath);

        repository.delete(resume);
    }

    @Override
    public Resume getEntityById(Long id) {
        User user = authService.getCurrentUser();

        return repository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Resume not found"));
    }
}
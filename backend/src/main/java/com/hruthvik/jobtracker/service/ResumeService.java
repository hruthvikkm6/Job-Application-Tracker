package com.hruthvik.jobtracker.service;

import com.hruthvik.jobtracker.dto.response.ResumeResponse;
import com.hruthvik.jobtracker.entity.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ResumeService {
    ResumeResponse uploadResume(MultipartFile file) throws IOException;

    List<ResumeResponse> getMyResumes();

    void deleteResume(Long id) throws IOException;

    Resume getEntityById(Long id);
}
package com.hruthvik.jobtracker.service.impl;

import com.hruthvik.jobtracker.dto.request.NoteRequest;
import com.hruthvik.jobtracker.dto.response.NoteResponse;
import com.hruthvik.jobtracker.entity.JobApplication;
import com.hruthvik.jobtracker.entity.Note;
import com.hruthvik.jobtracker.entity.User;
import com.hruthvik.jobtracker.exception.ResourceNotFoundException;
import com.hruthvik.jobtracker.mapper.NoteMapper;
import com.hruthvik.jobtracker.repository.JobApplicationRepository;
import com.hruthvik.jobtracker.repository.NoteRepository;
import com.hruthvik.jobtracker.service.AuthService;
import com.hruthvik.jobtracker.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final JobApplicationRepository jobRepository;
    private final NoteMapper mapper;
    private final AuthService authService;

    @Override
    @Transactional
    public NoteResponse addNote(Long jobId, NoteRequest request) {
        User user = authService.getCurrentUser();
        JobApplication job = jobRepository.findByIdAndUser(jobId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found"));

        Note note = mapper.toEntity(request);
        note.setJobApplication(job);

        return mapper.toResponse(noteRepository.save(note));
    }

    @Override
    @Transactional
    public NoteResponse updateNote(Long id, NoteRequest request) {
        User user = authService.getCurrentUser();
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

        if (!note.getJobApplication().getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Note not found");
        }

        mapper.updateEntity(request, note);
        return mapper.toResponse(noteRepository.save(note));
    }

    @Override
    @Transactional
    public void deleteNote(Long id) {
        User user = authService.getCurrentUser();
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

        if (!note.getJobApplication().getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Note not found");
        }

        noteRepository.delete(note);
    }

    @Override
    public List<NoteResponse> getNotesByJob(Long jobId) {
        User user = authService.getCurrentUser();
        JobApplication job = jobRepository.findByIdAndUser(jobId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found"));

        return job.getNotes().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}

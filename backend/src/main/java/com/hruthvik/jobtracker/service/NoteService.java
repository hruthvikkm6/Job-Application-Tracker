package com.hruthvik.jobtracker.service;

import com.hruthvik.jobtracker.dto.request.NoteRequest;
import com.hruthvik.jobtracker.dto.response.NoteResponse;

import java.util.List;

public interface NoteService {
    NoteResponse addNote(Long jobId, NoteRequest request);
    NoteResponse updateNote(Long id, NoteRequest request);
    void deleteNote(Long id);
    List<NoteResponse> getNotesByJob(Long jobId);
}

package com.hruthvik.jobtracker.controller;

import com.hruthvik.jobtracker.dto.request.NoteRequest;
import com.hruthvik.jobtracker.dto.response.NoteResponse;
import com.hruthvik.jobtracker.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/jobs/{jobId}/notes")
    public ResponseEntity<NoteResponse> addNote(@PathVariable Long jobId, @Valid @RequestBody NoteRequest request) {
        return new ResponseEntity<>(noteService.addNote(jobId, request), HttpStatus.CREATED);
    }

    @GetMapping("/jobs/{jobId}/notes")
    public ResponseEntity<List<NoteResponse>> getNotesByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(noteService.getNotesByJob(jobId));
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable Long id, @Valid @RequestBody NoteRequest request) {
        return ResponseEntity.ok(noteService.updateNote(id, request));
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}

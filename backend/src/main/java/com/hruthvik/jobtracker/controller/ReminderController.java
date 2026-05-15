package com.hruthvik.jobtracker.controller;

import com.hruthvik.jobtracker.dto.request.ReminderRequest;
import com.hruthvik.jobtracker.dto.response.ReminderResponse;
import com.hruthvik.jobtracker.service.ReminderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping("/jobs/{jobId}/reminders")
    public ResponseEntity<ReminderResponse> addReminder(@PathVariable Long jobId, @Valid @RequestBody ReminderRequest request) {
        return new ResponseEntity<>(reminderService.addReminder(jobId, request), HttpStatus.CREATED);
    }

    @GetMapping("/reminders")
    public ResponseEntity<List<ReminderResponse>> getMyReminders() {
        return ResponseEntity.ok(reminderService.getMyReminders());
    }

    @PutMapping("/reminders/{id}/complete")
    public ResponseEntity<ReminderResponse> completeReminder(@PathVariable Long id) {
        return ResponseEntity.ok(reminderService.completeReminder(id));
    }

    @DeleteMapping("/reminders/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        reminderService.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }
}

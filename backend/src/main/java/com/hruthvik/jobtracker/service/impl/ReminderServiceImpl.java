package com.hruthvik.jobtracker.service.impl;

import com.hruthvik.jobtracker.dto.request.ReminderRequest;
import com.hruthvik.jobtracker.dto.response.ReminderResponse;
import com.hruthvik.jobtracker.entity.JobApplication;
import com.hruthvik.jobtracker.entity.Reminder;
import com.hruthvik.jobtracker.entity.User;
import com.hruthvik.jobtracker.exception.ResourceNotFoundException;
import com.hruthvik.jobtracker.mapper.ReminderMapper;
import com.hruthvik.jobtracker.repository.JobApplicationRepository;
import com.hruthvik.jobtracker.repository.ReminderRepository;
import com.hruthvik.jobtracker.service.AuthService;
import com.hruthvik.jobtracker.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;
    private final JobApplicationRepository jobRepository;
    private final ReminderMapper mapper;
    private final AuthService authService;

    @Override
    @Transactional
    public ReminderResponse addReminder(Long jobId, ReminderRequest request) {
        User user = authService.getCurrentUser();
        JobApplication job = jobRepository.findByIdAndUser(jobId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found"));

        Reminder reminder = mapper.toEntity(request);
        reminder.setJobApplication(job);
        reminder.setUser(user);
        reminder.setCompleted(false);

        return mapper.toResponse(reminderRepository.save(reminder));
    }

    @Override
    @Transactional
    public ReminderResponse completeReminder(Long id) {
        User user = authService.getCurrentUser();
        Reminder reminder = reminderRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found"));

        reminder.setCompleted(true);
        return mapper.toResponse(reminderRepository.save(reminder));
    }

    @Override
    public List<ReminderResponse> getMyReminders() {
        User user = authService.getCurrentUser();
        return reminderRepository.findAllByUser(user).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteReminder(Long id) {
        User user = authService.getCurrentUser();
        Reminder reminder = reminderRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found"));
        reminderRepository.delete(reminder);
    }
}

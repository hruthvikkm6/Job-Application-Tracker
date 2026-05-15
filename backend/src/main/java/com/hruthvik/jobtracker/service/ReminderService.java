package com.hruthvik.jobtracker.service;

import com.hruthvik.jobtracker.dto.request.ReminderRequest;
import com.hruthvik.jobtracker.dto.response.ReminderResponse;

import java.util.List;

public interface ReminderService {
    ReminderResponse addReminder(Long jobId, ReminderRequest request);
    ReminderResponse completeReminder(Long id);
    List<ReminderResponse> getMyReminders();
    void deleteReminder(Long id);
}

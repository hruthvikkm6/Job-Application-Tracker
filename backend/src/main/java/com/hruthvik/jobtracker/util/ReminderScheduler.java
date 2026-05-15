package com.hruthvik.jobtracker.util;

import com.hruthvik.jobtracker.entity.Reminder;
import com.hruthvik.jobtracker.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReminderScheduler {

    private final ReminderRepository reminderRepository;

    @Scheduled(cron = "0 0 9 * * *") // Every day at 9 AM
    public void processReminders() {
        log.info("Processing daily reminders...");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfDay = now.withHour(23).withMinute(59).withSecond(59);

        // This is a simplified version. In a real app, you'd send emails or push notifications.
        // For now, we just log them or mark them as "triggered".
        // List<Reminder> dueReminders = ...
        log.info("Reminder processing completed.");
    }
}

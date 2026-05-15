package com.hruthvik.jobtracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReminderRequest {
    @NotBlank(message = "Message is required")
    private String message;

    @NotNull(message = "Reminder date is required")
    private LocalDateTime reminderDate;
}

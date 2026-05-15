package com.hruthvik.jobtracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "reminders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Reminder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime reminderDate;

    @Builder.Default
    @Column(nullable = false)
    private boolean completed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_application_id", nullable = false)
    private JobApplication jobApplication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

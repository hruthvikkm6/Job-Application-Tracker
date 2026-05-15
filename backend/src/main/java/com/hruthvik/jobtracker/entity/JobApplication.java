package com.hruthvik.jobtracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "job_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class JobApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String jobTitle;

    @Column(columnDefinition = "TEXT")
    private String jobDescription;

    private String applicationUrl;

    private String salary;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Source source;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    @Column(nullable = false)
    private LocalDate dateApplied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @OneToMany(mappedBy = "jobApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

    @OneToMany(mappedBy = "jobApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reminder> reminders;
}

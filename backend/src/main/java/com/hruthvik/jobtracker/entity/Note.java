package com.hruthvik.jobtracker.entity;

import jakarta.persistence.*;
import lombok.*;

import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Note extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_application_id", nullable = false)
    private JobApplication jobApplication;
}

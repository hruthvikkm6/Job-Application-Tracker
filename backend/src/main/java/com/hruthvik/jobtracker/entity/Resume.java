package com.hruthvik.jobtracker.entity;

import jakarta.persistence.*;
import lombok.*;

import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "resumes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Resume extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String publicId;

    private String version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

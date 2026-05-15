package com.hruthvik.jobtracker.repository;

import com.hruthvik.jobtracker.entity.JobApplication;
import com.hruthvik.jobtracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long>, JpaSpecificationExecutor<JobApplication> {
    Page<JobApplication> findAllByUser(User user, Pageable pageable);
    Optional<JobApplication> findByIdAndUser(Long id, User user);
}

package com.hruthvik.jobtracker.repository;

import com.hruthvik.jobtracker.entity.Resume;
import com.hruthvik.jobtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findAllByUser(User user);
    Optional<Resume> findByIdAndUser(Long id, User user);
}

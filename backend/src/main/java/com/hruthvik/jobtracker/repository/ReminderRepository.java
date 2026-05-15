package com.hruthvik.jobtracker.repository;

import com.hruthvik.jobtracker.entity.Reminder;
import com.hruthvik.jobtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findAllByUser(User user);
    Optional<Reminder> findByIdAndUser(Long id, User user);
}

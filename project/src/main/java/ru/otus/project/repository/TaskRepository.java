package ru.otus.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.domain.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getByUserId(Long userId);

    List<Task> getByUserIdAndDeadlineAndDoneIsFalse(Long userId, LocalDate deadline);
}

package ru.otus.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

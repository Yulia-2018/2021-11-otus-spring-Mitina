package ru.otus.project.service;

import ru.otus.project.domain.Task;
import ru.otus.project.exception.NotFoundException;

import java.util.List;

public interface TaskService {

    Task insert(Task task, Long userId);

    void update(Task task, Long userId) throws NotFoundException;

    Task getById(Long id, Long userId) throws NotFoundException;

    List<Task> getAll(Long userId);

    void deleteById(Long id, Long userId) throws NotFoundException;
}

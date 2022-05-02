package ru.otus.project.service;

import ru.otus.project.domain.Task;
import ru.otus.project.exception.NotFoundException;

import java.util.List;

public interface TaskService {

    Task insert(Task task);

    void update(Task task) throws NotFoundException;

    Task getById(Long id) throws NotFoundException;

    List<Task> getAll();

    void deleteById(Long id) throws NotFoundException;
}

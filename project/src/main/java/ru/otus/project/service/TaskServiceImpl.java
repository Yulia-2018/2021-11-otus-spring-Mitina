package ru.otus.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.domain.Task;
import ru.otus.project.exception.NotFoundException;
import ru.otus.project.repository.TaskRepository;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Task insert(Task task) {
        return repository.save(task);
    }

    @Transactional
    @Override
    public void update(Task task) {
        getById(task.getId());
        repository.save(task);
    }

    @Override
    public Task getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Task " + id + " not exist"));
    }

    @Override
    public List<Task> getAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Task task = getById(id);
        repository.delete(task);
    }
}

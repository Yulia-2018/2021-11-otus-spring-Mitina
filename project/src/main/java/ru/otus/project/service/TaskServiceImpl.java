package ru.otus.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.domain.Task;
import ru.otus.project.exception.NotFoundException;
import ru.otus.project.repository.TaskRepository;
import ru.otus.project.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Task insert(Task task, Long userId) {
        task.setUser(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User " + userId + " not exist")));
        return repository.save(task);
    }

    @Transactional
    @Override
    public void update(Task task, Long userId) {
        getById(task.getId(), userId);
        repository.save(task);
    }

    @Override
    public Task getById(Long id, Long userId) {
        return repository.findById(id)
                .filter(task -> task.getUser().getId().equals(userId))
                .orElseThrow(() -> new NotFoundException("Task " + id + " not exist"));
    }

    @Override
    public List<Task> getAll(Long userId) {
        return repository.findAll()
                .stream()
                .filter(task -> task.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long id, Long userId) {
        Task task = getById(id, userId);
        repository.delete(task);
    }
}

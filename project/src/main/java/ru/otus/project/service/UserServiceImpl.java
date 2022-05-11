package ru.otus.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.project.domain.User;
import ru.otus.project.exception.NotFoundException;
import ru.otus.project.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public User insert(User user) {
        return repository.save(user);
    }

    @Override
    public User getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User " + id + " not exist"));
    }

    @Override
    public List<User> getAll() {
        return repository.findAll()
                .stream()
                .sorted(User::compareById)
                .collect(Collectors.toList());
    }
}

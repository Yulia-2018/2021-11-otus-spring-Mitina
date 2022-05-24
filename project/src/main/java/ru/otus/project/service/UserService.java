package ru.otus.project.service;

import ru.otus.project.domain.User;
import ru.otus.project.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User insert(User user);

    User getById(Long id) throws NotFoundException;

    List<User> getAll();
}

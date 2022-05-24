package ru.otus.project.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.domain.User;
import ru.otus.project.service.UserService;

import java.util.List;

@RestController
public class UserRestController {

    private final UserService service;

    public UserRestController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/rest/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/rest/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getById(@PathVariable Long id) {
        return service.getById(id);
    }
}

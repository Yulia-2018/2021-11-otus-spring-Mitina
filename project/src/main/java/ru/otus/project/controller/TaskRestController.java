package ru.otus.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.otus.project.domain.Task;
import ru.otus.project.service.TaskService;

import java.net.URI;
import java.util.List;

@RestController
public class TaskRestController {

    private final TaskService service;

    public TaskRestController(TaskService service) {
        this.service = service;
    }

    @GetMapping(value = "/rest/task", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/rest/task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task get(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping(value = "/rest/task/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping(value = "/rest/task/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Task task, @PathVariable Long id) {
        task.setId(id);
        service.update(task);
    }

    @PostMapping(value = "/rest/task", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> update(@RequestBody Task task) {
        Task newTask = service.insert(task);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/task/{id}")
                .buildAndExpand(newTask.getId())
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(newTask);
    }
}

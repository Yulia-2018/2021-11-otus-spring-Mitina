package ru.otus.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.dto.CommentDto;
import ru.otus.homework.service.CommentService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommentDto> getAllForBook(@RequestParam long bookId) {
        return commentService.getAllForBook(bookId).stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommentDto get(@PathVariable long id) {
        Comment comment = commentService.getById(id);
        return CommentDto.toDto(comment);
    }

    @DeleteMapping("/comments/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        commentService.deleteById(id);
    }

    @PutMapping(value = "/comments/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Comment comment, @PathVariable long id) {
        comment.setId(id);
        commentService.update(comment);
    }

    @PostMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> createWithLocation(@RequestBody Comment comment) {
        Comment created = commentService.insert(comment);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/comments/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}

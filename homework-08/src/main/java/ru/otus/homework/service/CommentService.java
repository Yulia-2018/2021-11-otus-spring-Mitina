package ru.otus.homework.service;

import ru.otus.homework.domain.Comment;
import ru.otus.homework.exception.NotFoundException;

import java.util.List;

public interface CommentService {

    Comment insert(Comment comment, String bookId) throws NotFoundException;

    void update(Comment comment) throws NotFoundException;

    Comment getById(String id) throws NotFoundException;

    List<Comment> getAllForBook(String bookId);

    void deleteById(String id) throws NotFoundException;
}

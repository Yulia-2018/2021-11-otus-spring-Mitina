package ru.otus.homework.service;

import ru.otus.homework.domain.Comment;
import ru.otus.homework.exception.NotFoundException;

import java.util.List;

public interface CommentService {

    Comment insert(Comment comment) throws NotFoundException;

    void update(Comment comment) throws NotFoundException;

    Comment getById(long id) throws NotFoundException;

    List<Comment> getAllForBook(long bookId) throws NotFoundException;

    void deleteById(long id) throws NotFoundException;

    void deleteAllForBook(long bookId) throws NotFoundException;

    void insertAllForBook(long bookId, List<String> commentsText) throws NotFoundException;
}

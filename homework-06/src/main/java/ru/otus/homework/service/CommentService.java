package ru.otus.homework.service;

import ru.otus.homework.domain.Comment;
import ru.otus.homework.exception.NotFoundException;

import java.util.List;

public interface CommentService {

    Comment insert(Comment comment, long bookId) throws NotFoundException;

    void update(Comment comment, long bookId) throws NotFoundException;

    Comment getById(long id, long bookId) throws NotFoundException;

    List<Comment> getAllForBook(long bookId);

    void deleteById(long id, long bookId) throws NotFoundException;
}

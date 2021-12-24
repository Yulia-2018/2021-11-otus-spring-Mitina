package ru.otus.homework.repository;

import ru.otus.homework.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> getById(long id);

    List<Comment> getAllForBook(long bookId);

    void deleteById(long id);

    void delete(Comment comment);
}

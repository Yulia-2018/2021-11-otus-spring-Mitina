package ru.otus.homework.repository;

import ru.otus.homework.domain.Comment;

import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> getById(long id);

    void deleteById(long id);

    void delete(Comment comment);
}

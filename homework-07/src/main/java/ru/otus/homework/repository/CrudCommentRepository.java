package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Comment;

import java.util.Optional;

public interface CrudCommentRepository extends JpaRepository<Comment, Long> {

    @Override
    Comment save(Comment comment);

    @Override
    Optional<Comment> findById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    void delete(Comment comment);
}

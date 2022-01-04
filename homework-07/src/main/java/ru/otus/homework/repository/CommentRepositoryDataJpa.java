package ru.otus.homework.repository;

import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Comment;

import java.util.Optional;

@Component
public class CommentRepositoryDataJpa implements CommentRepository {

    private final CrudCommentRepository crudCommentRepository;

    public CommentRepositoryDataJpa(CrudCommentRepository crudCommentRepository) {
        this.crudCommentRepository = crudCommentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return crudCommentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getById(long id) {
        return crudCommentRepository.findById(id);
    }

    @Override
    public void deleteById(long id) {
        crudCommentRepository.deleteById(id);
    }

    @Override
    public void delete(Comment comment) {
        crudCommentRepository.delete(comment);
    }
}

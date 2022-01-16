package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, Long> {

}

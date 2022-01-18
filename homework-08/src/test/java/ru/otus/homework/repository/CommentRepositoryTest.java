package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.homework.domain.Comment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.homework.TestData.*;

@DataMongoTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository repository;

    @BeforeEach
    void init() {
        repository.deleteAll();
        repository.save(COMMENT_1);
        repository.save(COMMENT_2);
        repository.save(COMMENT_3);
        repository.save(COMMENT_4);
    }

    @Test
    void insert() {
        Comment comment = new Comment("new comment");
        Comment newComment = new Comment(comment);
        Comment createdComment = repository.save(newComment);
        comment.setId(createdComment.getId());
        assertThat(createdComment).usingRecursiveComparison().isEqualTo(comment);
    }

    @Test
    void update() {
        Comment comment = new Comment(COMMENT_1_ID, "updated comment");
        Comment updatedComment = new Comment(comment);
        Comment actualComment = repository.save(updatedComment);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(comment);
    }

    @Test
    void getById() {
        Optional<Comment> actualComment = repository.findById(COMMENT_1_ID);
        assertThat(actualComment).isPresent().get().usingRecursiveComparison().isEqualTo(COMMENT_1);
    }

    @Test
    void deleteById() {
        Optional<Comment> comment = repository.findById(COMMENT_1_ID);
        assertThat(comment).isNotEmpty();
        repository.deleteById(COMMENT_1_ID);
        Optional<Comment> deletedComment = repository.findById(COMMENT_1_ID);
        assertThat(deletedComment).isEmpty();
    }

    @Test
    void delete() {
        Optional<Comment> comment = repository.findById(COMMENT_1_ID);
        assertThat(comment).isNotEmpty();
        repository.delete(comment.get());
        Optional<Comment> deletedComment = repository.findById(COMMENT_1_ID);
        assertThat(deletedComment).isEmpty();
    }
}
package ru.otus.homework.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Comment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.homework.TestData.*;

@DataJpaTest
@Import(CommentRepositoryDataJpa.class)
class CommentRepositoryDataJpaTest {

    @Autowired
    private CommentRepositoryDataJpa repository;

    @Autowired
    private TestEntityManager em;

    @Test
    void insert() {
        Comment comment = new Comment("new comment", BOOK_1);
        Comment newComment = new Comment(comment);
        Comment createdComment = repository.save(newComment);
        comment.setId(createdComment.getId());
        assertThat(createdComment).usingRecursiveComparison().ignoringFields("book.comments").isEqualTo(comment);
    }

    @Test
    void update() {
        Comment comment = new Comment(COMMENT_1_ID, "updated comment", BOOK_1);
        Comment updatedComment = new Comment(comment);
        Comment actualComment = repository.save(updatedComment);
        assertThat(actualComment).usingRecursiveComparison().ignoringFields("book.comments").isEqualTo(comment);
    }

    @Test
    void getById() {
        Optional<Comment> actualComment = repository.getById(COMMENT_1_ID);
        assertThat(actualComment).isPresent().get().usingRecursiveComparison().ignoringFields("book.comments").isEqualTo(COMMENT_1);
    }

    @Test
    void deleteById() {
        Comment comment = em.find(Comment.class, COMMENT_1_ID);
        assertThat(comment).isNotNull();
        repository.deleteById(COMMENT_1_ID);
        Comment deletedComment = em.find(Comment.class, COMMENT_1_ID);
        assertThat(deletedComment).isNull();
    }

    @Test
    void delete() {
        Comment comment = em.find(Comment.class, COMMENT_1_ID);
        assertThat(comment).isNotNull();
        repository.delete(comment);
        Comment deletedComment = em.find(Comment.class, COMMENT_1_ID);
        assertThat(deletedComment).isNull();
    }
}
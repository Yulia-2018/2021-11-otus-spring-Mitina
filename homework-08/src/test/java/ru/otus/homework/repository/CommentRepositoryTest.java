package ru.otus.homework.repository;

import org.springframework.boot.test.context.SpringBootTest;

//@DataJpaTest
@SpringBootTest
class CommentRepositoryTest {

    /*@Autowired
    private CommentRepository repository;

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
        Optional<Comment> actualComment = repository.findById(COMMENT_1_ID);
        assertThat(actualComment).isPresent().get().usingRecursiveComparison().ignoringFields("book.comments").isEqualTo(COMMENT_1);
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
    }*/
}
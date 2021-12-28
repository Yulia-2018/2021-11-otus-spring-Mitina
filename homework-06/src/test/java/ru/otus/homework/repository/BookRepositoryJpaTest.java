package ru.otus.homework.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.homework.TestData.*;

@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void insert() {
        Book book = new Book("new book", AUTHOR_1, GENRE_1);
        Book newBook = new Book(book);
        Book createdBook = repositoryJpa.save(newBook);
        book.setId(createdBook.getId());
        assertThat(createdBook).usingRecursiveComparison().ignoringFields("comments").isEqualTo(book);
        List<Book> books = repositoryJpa.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT + 1);
        assertThat(books).usingRecursiveFieldByFieldElementComparatorIgnoringFields("comments").containsExactlyElementsOf(List.of(BOOK_1, BOOK_2, book));
    }

    @Test
    void update() {
        Book book = new Book(BOOK_1_ID, "updated book", BOOK_1.getAuthor(), BOOK_2.getGenre());
        Book updatedBook = new Book(book);
        Book actualBook = repositoryJpa.save(updatedBook);
        assertThat(actualBook).usingRecursiveComparison().ignoringFields("comments").isEqualTo(book);
    }

    @Test
    void getById() {
        Optional<Book> actualBook = repositoryJpa.getById(BOOK_1_ID);
        assertThat(actualBook).isPresent().get().usingRecursiveComparison().ignoringFields("comments").isEqualTo(BOOK_1);
    }

    @Test
    void getByIdWithComments() {
        Book book = new Book(BOOK_2);
        book.setComments(List.of(COMMENT_4));
        Optional<Book> actualBookWithComments = repositoryJpa.getByIdWithComments(BOOK_2_ID);
        assertThat(actualBookWithComments).isPresent().get().usingRecursiveComparison().ignoringFields("comments.book").isEqualTo(book);
    }

    @Test
    void getAll() {
        List<Book> books = repositoryJpa.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT);
        assertThat(books).usingRecursiveFieldByFieldElementComparatorIgnoringFields("comments").containsExactlyElementsOf(List.of(BOOK_1, BOOK_2));
    }

    @Test
    void deleteById() {
        Book book = em.find(Book.class, BOOK_1_ID);
        assertThat(book).isNotNull();
        repositoryJpa.deleteById(BOOK_1_ID);
        Book deletedBook = em.find(Book.class, BOOK_1_ID);
        assertThat(deletedBook).isNull();
    }
}
package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.homework.TestData.*;

@DataMongoTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    void init() {
        repository.deleteAll();
        repository.save(BOOK_1);
        repository.save(BOOK_2);
    }

    @Test
    void insert() {
        Book book = new Book("new book", AUTHOR_1, GENRE_1);
        Book newBook = new Book(book);
        Book createdBook = repository.save(newBook);
        book.setId(createdBook.getId());
        assertThat(createdBook).usingRecursiveComparison().ignoringFields("comments").isEqualTo(book);
        List<Book> books = repository.findAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT + 1);
        assertThat(books).usingRecursiveFieldByFieldElementComparatorIgnoringFields("comments").containsExactlyElementsOf(List.of(BOOK_1, BOOK_2, book));
    }

    @Test
    void update() {
        Book book = new Book(BOOK_1_ID, "updated book", BOOK_1.getAuthor(), BOOK_2.getGenre());
        Book updatedBook = new Book(book);
        Book actualBook = repository.save(updatedBook);
        assertThat(actualBook).usingRecursiveComparison().ignoringFields("comments").isEqualTo(book);
    }

    @Test
    void getById() {
        Optional<Book> actualBook = repository.findById(BOOK_1_ID);
        assertThat(actualBook).isPresent().get().usingRecursiveComparison().ignoringFields("comments").isEqualTo(BOOK_1);
    }

    @Test
    void getAll() {
        List<Book> books = repository.findAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT);
        assertThat(books).usingRecursiveFieldByFieldElementComparatorIgnoringFields("comments").containsExactlyElementsOf(List.of(BOOK_1, BOOK_2));
    }

    @Test
    void deleteById() {
        Optional<Book> book = repository.findById(BOOK_1_ID);
        assertThat(book).isNotEmpty();
        repository.deleteById(BOOK_1_ID);
        Optional<Book> deletedBook = repository.findById(BOOK_1_ID);
        assertThat(deletedBook).isEmpty();
    }
}
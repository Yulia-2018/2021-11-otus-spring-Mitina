package ru.otus.homework.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.homework.TestData.*;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc dao;

    @Test
    void insert() {
        Book newBook = new Book("new book", AUTHOR_1, GENRE_1);
        Book createdBook = dao.insert(newBook);
        newBook.setId(createdBook.getId());
        assertThat(createdBook).usingRecursiveComparison().isEqualTo(newBook);
        List<Book> books = dao.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT + 1);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_1, BOOK_2, newBook));
    }

    @Test
    void update() {
        Book updatedBook = new Book(BOOK_1.getId(), "updated book", BOOK_1.getAuthor(), GENRE_1);
        Book actualBook = dao.update(updatedBook);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(updatedBook);
    }

    @Test
    void getById() {
        Book actualBook = dao.getById(BOOK_1.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(BOOK_1);
    }

    @Test
    void getAll() {
        List<Book> books = dao.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_1, BOOK_2));
    }

    @Test
    void deleteById() {
        dao.deleteById(BOOK_1.getId());
        List<Book> books = dao.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT - 1);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_2));
    }
}
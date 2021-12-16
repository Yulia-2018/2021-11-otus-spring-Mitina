package ru.otus.homework.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework.dao.BookDaoJdbc;
import ru.otus.homework.domain.Book;
import ru.otus.homework.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;
import static ru.otus.homework.TestData.*;

@SpringBootTest
class BookServiceJdbcTest {

    @Autowired
    private BookServiceJdbc service;

    @MockBean
    private BookDaoJdbc dao;

    @Test
    void insert() {
        Book newBook = new Book("new book", AUTHOR_1, GENRE_1);

        when(dao.insert(newBook)).thenReturn(new Book(100006, "new book", AUTHOR_1, GENRE_1));
        when(dao.getAll()).thenReturn(List.of(BOOK_1, BOOK_2, new Book(100006, "new book", AUTHOR_1, GENRE_1)));

        Book createdBook = service.insert(newBook);
        newBook.setId(createdBook.getId());
        assertThat(createdBook).usingRecursiveComparison().isEqualTo(newBook);
        List<Book> books = service.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT + 1);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_1, BOOK_2, newBook));

        verify(dao, times(1)).insert(newBook);
        verify(dao, times(1)).getAll();
    }

    @Test
    void update() {
        Book updatedBook = new Book(BOOK_1.getId(), "updated book", BOOK_1.getAuthor(), GENRE_1);

        when(dao.update(updatedBook)).thenReturn(updatedBook);
        when(dao.getById(BOOK_1.getId())).thenReturn(updatedBook);

        service.update(updatedBook);
        Book actualBook = service.getById(BOOK_1.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(updatedBook);

        verify(dao, times(1)).update(updatedBook);
        verify(dao, times(1)).getById(BOOK_1.getId());
    }

    @Test
    void updateNotFound() {
        Book updatedBook = new Book(1, "updated book", BOOK_1.getAuthor(), GENRE_1);
        when(dao.update(updatedBook)).thenReturn(null);
        assertThatCode(() -> service.update(updatedBook)).isInstanceOf(NotFoundException.class).hasMessage("Book 1 not exist");
        verify(dao, times(1)).update(updatedBook);
    }

    @Test
    void getById() {
        when(dao.getById(BOOK_1.getId())).thenReturn(BOOK_1);

        Book actualBook = service.getById(BOOK_1.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(BOOK_1);

        verify(dao, times(1)).getById(BOOK_1.getId());
    }

    @Test
    void getByIdNotFound() {
        when(dao.getById(1)).thenReturn(null);
        assertThatCode(() -> service.getById(1)).isInstanceOf(NotFoundException.class).hasMessage("Book 1 not exist");
        verify(dao, times(1)).getById(1);
    }

    @Test
    void getAll() {
        when(dao.getAll()).thenReturn(List.of(BOOK_1, BOOK_2));

        List<Book> books = service.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_1, BOOK_2));

        verify(dao, times(1)).getAll();
    }

    @Test
    void deleteById() {
        when(dao.deleteById(BOOK_1.getId())).thenReturn(true);
        when(dao.getAll()).thenReturn(List.of(BOOK_2));

        service.deleteById(BOOK_1.getId());
        List<Book> books = service.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT - 1);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_2));

        verify(dao, times(1)).deleteById(BOOK_1.getId());
        verify(dao, times(1)).getAll();
    }

    @Test
    void deleteByIdNotFound() {
        when(dao.deleteById(1)).thenReturn(false);
        assertThatCode(() -> service.deleteById(1)).isInstanceOf(NotFoundException.class).hasMessage("Book 1 not exist");
        verify(dao, times(1)).deleteById(1);
    }
}
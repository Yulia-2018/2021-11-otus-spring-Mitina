package ru.otus.homework.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework.dao.AuthorDaoJdbc;
import ru.otus.homework.dao.BookDaoJdbc;
import ru.otus.homework.dao.GenreDaoJdbc;
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
    private BookDaoJdbc bookDao;

    @MockBean
    private AuthorDaoJdbc authorDao;

    @MockBean
    private GenreDaoJdbc genreDao;

    @Test
    void insert() {
        Book newBook = new Book("new book", AUTHOR_1, GENRE_1);

        when(authorDao.getByName(AUTHOR_1.getName())).thenReturn(AUTHOR_1);
        when(genreDao.getByTitle(GENRE_1.getTitle())).thenReturn(GENRE_1);
        when(bookDao.insert(newBook)).thenReturn(new Book(100006, "new book", AUTHOR_1, GENRE_1));
        when(bookDao.getAll()).thenReturn(List.of(BOOK_1, BOOK_2, new Book(100006, "new book", AUTHOR_1, GENRE_1)));

        Book createdBook = service.insert(newBook);
        newBook.setId(createdBook.getId());
        assertThat(createdBook).usingRecursiveComparison().isEqualTo(newBook);
        List<Book> books = service.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT + 1);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_1, BOOK_2, newBook));

        verify(authorDao, times(1)).getByName(AUTHOR_1.getName());
        verify(authorDao, times(0)).insert(any());
        verify(genreDao, times(1)).getByTitle(GENRE_1.getTitle());
        verify(genreDao, times(0)).insert(any());
        verify(bookDao, times(1)).insert(newBook);
        verify(bookDao, times(1)).getAll();
    }

    @Test
    void update() {
        Book expectedBook = new Book(BOOK_1.getId(), "updated book", BOOK_1.getAuthor(), GENRE_1);
        Book updatedBook = new Book(expectedBook);

        when(authorDao.getByName(BOOK_1.getAuthor().getName())).thenReturn(BOOK_1.getAuthor());
        when(genreDao.getByTitle(GENRE_1.getTitle())).thenReturn(GENRE_1);
        when(bookDao.update(updatedBook)).thenReturn(expectedBook);
        when(bookDao.getById(BOOK_1.getId())).thenReturn(expectedBook);

        service.update(updatedBook);
        Book actualBook = service.getById(BOOK_1.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

        verify(authorDao, times(1)).getByName(BOOK_1.getAuthor().getName());
        verify(authorDao, times(0)).insert(any());
        verify(genreDao, times(1)).getByTitle(GENRE_1.getTitle());
        verify(genreDao, times(0)).insert(any());
        verify(bookDao, times(1)).update(updatedBook);
        verify(bookDao, times(1)).getById(BOOK_1.getId());
    }

    @Test
    void updateNotFound() {
        Book updatedBook = new Book(1, "updated book", BOOK_1.getAuthor(), GENRE_1);
        when(bookDao.update(updatedBook)).thenReturn(null);
        assertThatCode(() -> service.update(updatedBook)).isInstanceOf(NotFoundException.class).hasMessage("Book 1 not exist");
        verify(bookDao, times(1)).update(updatedBook);
    }

    @Test
    void getById() {
        when(bookDao.getById(BOOK_1.getId())).thenReturn(BOOK_1);

        Book actualBook = service.getById(BOOK_1.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(BOOK_1);

        verify(bookDao, times(1)).getById(BOOK_1.getId());
    }

    @Test
    void getByIdNotFound() {
        when(bookDao.getById(1)).thenReturn(null);
        assertThatCode(() -> service.getById(1)).isInstanceOf(NotFoundException.class).hasMessage("Book 1 not exist");
        verify(bookDao, times(1)).getById(1);
    }

    @Test
    void getAll() {
        when(bookDao.getAll()).thenReturn(List.of(BOOK_1, BOOK_2));

        List<Book> books = service.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_1, BOOK_2));

        verify(bookDao, times(1)).getAll();
    }

    @Test
    void deleteById() {
        when(bookDao.deleteById(BOOK_1.getId())).thenReturn(true);
        when(bookDao.getAll()).thenReturn(List.of(BOOK_2));

        service.deleteById(BOOK_1.getId());
        List<Book> books = service.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT - 1);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_2));

        verify(bookDao, times(1)).deleteById(BOOK_1.getId());
        verify(bookDao, times(1)).getAll();
    }

    @Test
    void deleteByIdNotFound() {
        when(bookDao.deleteById(1)).thenReturn(false);
        assertThatCode(() -> service.deleteById(1)).isInstanceOf(NotFoundException.class).hasMessage("Book 1 not exist");
        verify(bookDao, times(1)).deleteById(1);
    }
}
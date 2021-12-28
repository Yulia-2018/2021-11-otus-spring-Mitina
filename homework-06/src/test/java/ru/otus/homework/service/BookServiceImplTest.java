package ru.otus.homework.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework.repository.AuthorRepositoryJpa;
import ru.otus.homework.repository.BookRepositoryJpa;
import ru.otus.homework.repository.GenreRepositoryJpa;
import ru.otus.homework.domain.Book;
import ru.otus.homework.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;
import static ru.otus.homework.TestData.*;

@SpringBootTest
class BookServiceImplTest {

    @Autowired
    private BookServiceImpl service;

    @MockBean
    private BookRepositoryJpa bookRepository;

    @MockBean
    private AuthorRepositoryJpa authorRepository;

    @MockBean
    private GenreRepositoryJpa genreRepository;

    @Test
    void insert() {
        Book newBook = new Book("new book", AUTHOR_1, GENRE_1);

        when(authorRepository.getByName(AUTHOR_1.getName())).thenReturn(Optional.of(AUTHOR_1));
        when(genreRepository.getByTitle(GENRE_1.getTitle())).thenReturn(Optional.of(GENRE_1));
        when(bookRepository.save(newBook)).thenReturn(new Book(100006, "new book", AUTHOR_1, GENRE_1));
        when(bookRepository.getAll()).thenReturn(List.of(BOOK_1, BOOK_2, new Book(100006, "new book", AUTHOR_1, GENRE_1)));

        Book createdBook = service.insert(newBook);
        newBook.setId(createdBook.getId());
        assertThat(createdBook).usingRecursiveComparison().isEqualTo(newBook);
        List<Book> books = service.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT + 1);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_1, BOOK_2, newBook));

        verify(authorRepository, times(1)).getByName(AUTHOR_1.getName());
        verify(authorRepository, times(0)).save(any());
        verify(genreRepository, times(1)).getByTitle(GENRE_1.getTitle());
        verify(genreRepository, times(0)).save(any());
        verify(bookRepository, times(1)).save(newBook);
        verify(bookRepository, times(1)).getAll();
    }

    @Test
    void update() {
        Book expectedBook = new Book(BOOK_1_ID, "updated book", BOOK_1.getAuthor(), GENRE_1);
        Book updatedBook = new Book(expectedBook);

        when(authorRepository.getByName(BOOK_1.getAuthor().getName())).thenReturn(Optional.of(BOOK_1.getAuthor()));
        when(genreRepository.getByTitle(GENRE_1.getTitle())).thenReturn(Optional.of(GENRE_1));
        when(bookRepository.save(updatedBook)).thenReturn(expectedBook);
        when(bookRepository.getById(BOOK_1_ID)).thenReturn(Optional.of(expectedBook));

        service.update(updatedBook);
        Book actualBook = service.getById(BOOK_1_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

        verify(authorRepository, times(1)).getByName(BOOK_1.getAuthor().getName());
        verify(authorRepository, times(0)).save(any());
        verify(genreRepository, times(1)).getByTitle(GENRE_1.getTitle());
        verify(genreRepository, times(0)).save(any());
        verify(bookRepository, times(1)).save(updatedBook);
        verify(bookRepository, times(2)).getById(BOOK_1_ID);
    }

    @Test
    void updateNotFound() {
        Book updatedBook = new Book(1, "updated book", BOOK_1.getAuthor(), GENRE_1);
        when(bookRepository.getById(1)).thenReturn(Optional.empty());
        assertThatCode(() -> service.update(updatedBook)).isInstanceOf(NotFoundException.class).hasMessage("Book 1 not exist");
        verify(bookRepository, times(1)).getById(1);
        verify(bookRepository, times(0)).save(updatedBook);
    }

    @Test
    void getById() {
        when(bookRepository.getById(BOOK_1_ID)).thenReturn(Optional.of(BOOK_1));

        Book actualBook = service.getById(BOOK_1_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(BOOK_1);

        verify(bookRepository, times(1)).getById(BOOK_1_ID);
    }

    @Test
    void getByIdWithComments() {
        Book book = new Book(BOOK_2);
        book.setComments(List.of(COMMENT_4));

        when(bookRepository.getByIdWithComments(BOOK_2_ID)).thenReturn(Optional.of(book));

        Book actualBookWithComments = service.getByIdWithComments(BOOK_2_ID);
        assertThat(actualBookWithComments).usingRecursiveComparison().isEqualTo(book);

        verify(bookRepository, times(1)).getByIdWithComments(BOOK_2_ID);
    }

    @Test
    void getByIdNotFound() {
        when(bookRepository.getById(1)).thenReturn(Optional.empty());
        assertThatCode(() -> service.getById(1)).isInstanceOf(NotFoundException.class).hasMessage("Book 1 not exist");
        verify(bookRepository, times(1)).getById(1);
    }

    @Test
    void getByIdWithCommentsNotFound() {
        when(bookRepository.getByIdWithComments(1)).thenReturn(Optional.empty());
        assertThatCode(() -> service.getByIdWithComments(1)).isInstanceOf(NotFoundException.class).hasMessage("Book 1 not exist");
        verify(bookRepository, times(1)).getByIdWithComments(1);
    }

    @Test
    void getAll() {
        when(bookRepository.getAll()).thenReturn(List.of(BOOK_1, BOOK_2));

        List<Book> books = service.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_1, BOOK_2));

        verify(bookRepository, times(1)).getAll();
    }

    @Test
    void deleteById() {
        when(bookRepository.deleteById(BOOK_1_ID)).thenReturn(true);
        when(bookRepository.getAll()).thenReturn(List.of(BOOK_2));

        service.deleteById(BOOK_1_ID);
        List<Book> books = service.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT - 1);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_2));

        verify(bookRepository, times(1)).deleteById(BOOK_1_ID);
        verify(bookRepository, times(1)).getAll();
    }

    @Test
    void deleteByIdNotFound() {
        when(bookRepository.deleteById(1)).thenReturn(false);
        assertThatCode(() -> service.deleteById(1)).isInstanceOf(NotFoundException.class).hasMessage("Book 1 not exist");
        verify(bookRepository, times(1)).deleteById(1);
    }
}
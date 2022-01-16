package ru.otus.homework.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework.domain.Book;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.BookRepository;

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
    private BookRepository bookRepository;

    @Test
    void insert() {
        Book newBook = new Book("new book", AUTHOR_1, GENRE_1);

        when(bookRepository.save(newBook)).thenReturn(new Book("100006", "new book", AUTHOR_1, GENRE_1));
        when(bookRepository.findAll()).thenReturn(List.of(BOOK_1, BOOK_2, new Book("100006", "new book", AUTHOR_1, GENRE_1)));

        Book createdBook = service.insert(newBook);
        newBook.setId(createdBook.getId());
        assertThat(createdBook).usingRecursiveComparison().isEqualTo(newBook);
        List<Book> books = service.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT + 1);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_1, BOOK_2, newBook));

        verify(bookRepository, times(1)).save(newBook);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void update() {
        Book expectedBook = new Book(BOOK_1_ID, "updated book", BOOK_1.getAuthor(), GENRE_1);
        Book updatedBook = new Book(expectedBook);

        when(bookRepository.save(updatedBook)).thenReturn(expectedBook);
        when(bookRepository.findById(BOOK_1_ID)).thenReturn(Optional.of(expectedBook));

        service.update(updatedBook);
        Book actualBook = service.getById(BOOK_1_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

        verify(bookRepository, times(1)).save(updatedBook);
        verify(bookRepository, times(2)).findById(BOOK_1_ID);
    }

    @Test
    void updateNotFound() {
        Book updatedBook = new Book("1", "updated book", BOOK_1.getAuthor(), GENRE_1);
        when(bookRepository.findById("1")).thenReturn(Optional.empty());
        assertThatCode(() -> service.update(updatedBook)).isInstanceOf(NotFoundException.class).hasMessage("Book 1 not exist");
        verify(bookRepository, times(1)).findById("1");
        verify(bookRepository, times(0)).save(updatedBook);
    }

    @Test
    void getById() {
        when(bookRepository.findById(BOOK_1_ID)).thenReturn(Optional.of(BOOK_1));

        Book actualBook = service.getById(BOOK_1_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(BOOK_1);

        verify(bookRepository, times(1)).findById(BOOK_1_ID);
    }

    @Test
    void getByIdNotFound() {
        when(bookRepository.findById("1")).thenReturn(Optional.empty());
        assertThatCode(() -> service.getById("1")).isInstanceOf(NotFoundException.class).hasMessage("Book 1 not exist");
        verify(bookRepository, times(1)).findById("1");
    }

    @Test
    void getAll() {
        when(bookRepository.findAll()).thenReturn(List.of(BOOK_1, BOOK_2));

        List<Book> books = service.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_1, BOOK_2));

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void deleteById() {
        when(bookRepository.findById(BOOK_1_ID)).thenReturn(Optional.of(BOOK_1));
        when(bookRepository.findAll()).thenReturn(List.of(BOOK_2));

        service.deleteById(BOOK_1_ID);
        List<Book> books = service.getAll();
        assertThat(books.size()).isEqualTo(BOOKS_COUNT - 1);
        assertThat(books).containsExactlyElementsOf(List.of(BOOK_2));

        verify(bookRepository, times(1)).findById(BOOK_1_ID);
        verify(bookRepository, times(1)).delete(BOOK_1);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void deleteByIdNotFound() {
        when(bookRepository.findById("1")).thenReturn(Optional.empty());
        assertThatCode(() -> service.deleteById("1")).isInstanceOf(NotFoundException.class).hasMessage("Book 1 not exist");
        verify(bookRepository, times(1)).findById("1");
        verify(bookRepository, times(0)).delete(any());
    }
}
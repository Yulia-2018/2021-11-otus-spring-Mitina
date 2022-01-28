package ru.otus.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework.TestData.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Test
    void listPage() throws Exception {
        when(bookService.getAll()).thenReturn(List.of(BOOK_1, BOOK_2));

        mvc.perform(get("/"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).getAll();
    }

    @Test
    void editPage() throws Exception {
        when(bookService.getById(BOOK_1_ID)).thenReturn(BOOK_1);

        mvc.perform(get("/edit").param("id", String.valueOf(BOOK_1_ID)))
                .andExpect(status().isOk());

        verify(bookService, times(1)).getById(BOOK_1_ID);
        verify(authorService, times(1)).getAll();
        verify(genreService, times(1)).getAll();
    }

    @Test
    void saveBook() throws Exception {
        when(bookService.getById(BOOK_1_ID)).thenReturn(BOOK_1);
        when(authorService.getById(AUTHOR_1_ID)).thenReturn(AUTHOR_1);
        when(genreService.getById(GENRE_1_ID)).thenReturn(GENRE_1);

        mvc.perform(post("/edit")
                .param("id", String.valueOf(BOOK_1_ID)).param("title", "New title")
                .param("author", String.valueOf(AUTHOR_1_ID)).param("genre", String.valueOf(GENRE_1_ID)))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));

        verify(bookService, times(1)).getById(BOOK_1_ID);
        verify(authorService, times(1)).getById(AUTHOR_1_ID);
        verify(genreService, times(1)).getById(GENRE_1_ID);
        verify(bookService, times(1)).update(any());
    }

    @Test
    void deleteBook() throws Exception {
        mvc.perform(get("/delete").param("id", String.valueOf(BOOK_1_ID)))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));

        verify(bookService, times(1)).deleteById(BOOK_1_ID);
    }
}
package ru.otus.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

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
        mvc.perform(get("/"))
                .andExpect(status().isOk());
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
        when(authorService.getByNameOrCreate(AUTHOR_1.getName())).thenReturn(AUTHOR_1);
        when(genreService.getByTitleOrCreate(GENRE_1.getTitle())).thenReturn(GENRE_1);

        mvc.perform(post("/edit")
                .param("id", String.valueOf(BOOK_1_ID)).param("title", "New title")
                .param("authorName", AUTHOR_1.getName()).param("genreTitle", String.valueOf(GENRE_1.getTitle())))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));

        verify(authorService, times(1)).getByNameOrCreate(AUTHOR_1.getName());
        verify(genreService, times(1)).getByTitleOrCreate(GENRE_1.getTitle());
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
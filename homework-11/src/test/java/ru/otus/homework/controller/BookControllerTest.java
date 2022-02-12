package ru.otus.homework.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(BookController.class)
class BookControllerTest {

    /*@Autowired
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
        Book book = new Book(BOOK_1_ID, "New title", AUTHOR_1, GENRE_1);
        BookDto bookDto = BookDto.toDto(book);

        when(bookService.createBookOnDto(bookDto)).thenReturn(book);

        mvc.perform(post("/edit")
                .param("id", String.valueOf(BOOK_1_ID)).param("title", "New title")
                .param("authorName", AUTHOR_1.getName()).param("genreTitle", String.valueOf(GENRE_1.getTitle())))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));

        verify(bookService, times(1)).createBookOnDto(bookDto);
        verify(bookService, times(1)).update(any());
    }

    @Test
    void deleteBook() throws Exception {
        mvc.perform(get("/delete").param("id", String.valueOf(BOOK_1_ID)))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));

        verify(bookService, times(1)).deleteById(BOOK_1_ID);
    }*/
}
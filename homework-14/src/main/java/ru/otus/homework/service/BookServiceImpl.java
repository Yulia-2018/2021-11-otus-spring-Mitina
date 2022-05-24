package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.mongo.Book;
import ru.otus.homework.domain.mongo.Comment;
import ru.otus.homework.domain.relational.R_Author;
import ru.otus.homework.domain.relational.R_Book;
import ru.otus.homework.domain.relational.R_Comment;
import ru.otus.homework.domain.relational.R_Genre;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.relational.R_AuthorRepository;
import ru.otus.homework.repository.relational.R_GenreRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final R_AuthorRepository r_authorRepository;

    private final R_GenreRepository r_genreRepository;

    public BookServiceImpl(R_AuthorRepository r_authorRepository, R_GenreRepository r_genreRepository) {
        this.r_authorRepository = r_authorRepository;
        this.r_genreRepository = r_genreRepository;
    }

    @Override
    public R_Book convert(Book book) {
        String authorName = book.getAuthor().getName();
        String genreTitle = book.getGenre().getTitle();
        R_Author r_author = r_authorRepository.getByName(authorName)
                .orElseThrow(() -> new NotFoundException("Author with name " + authorName + " not exist"));
        R_Genre r_genre = r_genreRepository.getByTitle(genreTitle)
                .orElseThrow(() -> new NotFoundException("Genre with title " + genreTitle + " not exist"));

        R_Book r_book = new R_Book(book.getTitle(), r_author, r_genre);
        List<Comment> comments = book.getComments();
        comments.forEach(comment -> r_book.addComment(new R_Comment(comment.getText())));
        return r_book;
    }
}

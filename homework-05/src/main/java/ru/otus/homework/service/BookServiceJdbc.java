package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exception.NotFoundException;

import java.util.List;

@Service
public class BookServiceJdbc implements BookService {

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    public BookServiceJdbc(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Book insert(Book book) {
        addAuthorAndGenre(book);
        return bookDao.insert(book);
    }

    @Override
    public void update(Book book) {
        addAuthorAndGenre(book);
        if (bookDao.update(book) == null) {
            throw new NotFoundException("Book " + book.getId() + " not exist");
        }
    }

    @Override
    public Book getById(long id) {
        Book book = bookDao.getById(id);
        if (book == null) {
            throw new NotFoundException("Book " + id + " not exist");
        }
        return book;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public void deleteById(long id) {
        if (!bookDao.deleteById(id)) {
            throw new NotFoundException("Book " + id + " not exist");
        }
    }

    private void addAuthorAndGenre(Book book) {
        Author author = insertAuthorIfNotExist(book.getAuthor());
        Genre genre = insertGenreIfNotExist(book.getGenre());
        book.setAuthor(author);
        book.setGenre(genre);
    }

    private Author insertAuthorIfNotExist(Author author) {
        String name = author.getName();
        Author authorFromBase = authorDao.getByName(name);
        return (authorFromBase == null) ? authorDao.insert(author) : authorFromBase;
    }

    private Genre insertGenreIfNotExist(Genre genre) {
        String title = genre.getTitle();
        Genre genreFromBase = genreDao.getByTitle(title);
        return (genreFromBase == null) ? genreDao.insert(genre) : genreFromBase;
    }
}

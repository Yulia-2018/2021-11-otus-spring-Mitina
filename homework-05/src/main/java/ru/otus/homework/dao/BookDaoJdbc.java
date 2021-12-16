package ru.otus.homework.dao;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    private final SimpleJdbcInsertOperations insertBook;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc, AuthorDao authorDao, GenreDao genreDao) {
        this.jdbc = jdbc;
        this.insertBook = new SimpleJdbcInsert((JdbcTemplate) jdbc.getJdbcOperations())
                .withTableName("book")
                .usingGeneratedKeyColumns("id");
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Book insert(Book book) {
        Author author = insertAuthorIfNotExist(book.getAuthor());
        Genre genre = insertGenreIfNotExist(book.getGenre());
        Number newId = insertBook.executeAndReturnKey(Map.of("title", book.getTitle(), "author_id", author.getId(), "genre_id", genre.getId()));
        book.setId(newId.longValue());
        book.setAuthor(author);
        book.setGenre(genre);
        return book;
    }

    @Override
    public Book update(Book book) {
        Author author = insertAuthorIfNotExist(book.getAuthor());
        Genre genre = insertGenreIfNotExist(book.getGenre());

        if (jdbc.update("" +
                        "UPDATE book " +
                        "SET title = :title, author_id = :author_id, genre_id = :genre_id " +
                        "WHERE id = :id",
                Map.of("id", book.getId(), "title", book.getTitle(), "author_id", author.getId(), "genre_id", genre.getId())) == 0) {
            return null;
        }
        book.setAuthor(author);
        book.setGenre(genre);
        return book;
    }

    @Override
    public Book getById(long id) {
        List<Book> books = jdbc.query("" +
                        "SELECT b.id, b.title, b.author_id, b.genre_id, a.name AS author_name, g.title AS genre_title " +
                        "FROM book b " +
                        "  INNER JOIN author a ON b.author_id = a.id " +
                        "  INNER JOIN genre g ON b.genre_id = g.id " +
                        "WHERE b.id = :id",
                Map.of("id", id),
                new BookMapper());
        return DataAccessUtils.singleResult(books);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("" +
                        "SELECT b.id, b.title, b.author_id, b.genre_id, a.name AS author_name, g.title AS genre_title " +
                        "FROM book b " +
                        "  INNER JOIN author a ON b.author_id = a.id " +
                        "  INNER JOIN genre g ON b.genre_id = g.id " +
                        "ORDER BY b.id",
                new BookMapper());
    }

    @Override
    public boolean deleteById(long id) {
        return jdbc.update("DELETE FROM book WHERE id = :id", Map.of("id", id)) != 0;
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

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                    rs.getLong("id"),
                    rs.getString("title"),
                    new Author(rs.getLong("author_id"), rs.getString("author_name")),
                    new Genre(rs.getLong("genre_id"), rs.getString("genre_title")));
        }
    }
}

package ru.otus.homework.dao;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Author insert(Author author) {
        jdbc.update("INSERT INTO author (id, name) VALUES(:id, :name)",
                Map.of("id", author.getId(), "name", author.getName()));
        return author;
    }

    @Override
    public Author getById(long id) {
        List<Author> authors = jdbc.query("SELECT id, name FROM author WHERE id = :id", Map.of("id", id), new AuthorMapper());
        return DataAccessUtils.singleResult(authors);
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getLong("id"), rs.getString("name"));
        }
    }
}

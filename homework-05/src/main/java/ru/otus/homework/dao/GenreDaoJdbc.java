package ru.otus.homework.dao;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Genre insert(Genre genre) {
        jdbc.update("INSERT INTO genre (id, title) VALUES (:id, :title)",
                Map.of("id", genre.getId(), "title", genre.getTitle()));
        return genre;
    }

    @Override
    public Genre getById(long id) {
        List<Genre> genres = jdbc.query("SELECT id, title FROM genre WHERE id = :id", Map.of("id", id), new GenreMapper());
        return DataAccessUtils.singleResult(genres);
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getLong("id"), rs.getString("title"));
        }
    }
}

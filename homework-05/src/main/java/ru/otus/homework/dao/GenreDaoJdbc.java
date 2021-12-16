package ru.otus.homework.dao;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Genre;

import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private static final RowMapper<Genre> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Genre.class);

    private final NamedParameterJdbcOperations jdbc;

    private final SimpleJdbcInsertOperations insertGenre;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
        this.insertGenre = new SimpleJdbcInsert((JdbcTemplate) jdbc.getJdbcOperations())
                .withTableName("genre")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Genre insert(Genre genre) {
        Number newId = insertGenre.executeAndReturnKey(Map.of("title", genre.getTitle()));
        genre.setId(newId.longValue());
        return genre;
    }

    @Override
    public Genre getById(long id) {
        List<Genre> genres = jdbc.query("SELECT id, title FROM genre WHERE id = :id", Map.of("id", id), ROW_MAPPER);
        return DataAccessUtils.singleResult(genres);
    }

    @Override
    public Genre getByTitle(String title) {
        List<Genre> genres = jdbc.query("SELECT id, title FROM genre WHERE title = :title", Map.of("title", title), ROW_MAPPER);
        return DataAccessUtils.singleResult(genres);
    }
}

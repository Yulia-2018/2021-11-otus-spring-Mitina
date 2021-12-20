package ru.otus.homework.dao;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;

import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private static final RowMapper<Author> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Author.class);

    private final NamedParameterJdbcOperations jdbc;

    private final SimpleJdbcInsertOperations insertAuthor;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
        this.insertAuthor = new SimpleJdbcInsert((JdbcTemplate) jdbc.getJdbcOperations())
                .withTableName("author")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Author insert(Author author) {
        Number newId = insertAuthor.executeAndReturnKey(Map.of("name", author.getName()));
        author.setId(newId.longValue());
        return author;
    }

    @Override
    public Author getById(long id) {
        List<Author> authors = jdbc.query("SELECT id, name FROM author WHERE id = :id", Map.of("id", id), ROW_MAPPER);
        return DataAccessUtils.singleResult(authors);
    }

    @Override
    public Author getByName(String name) {
        List<Author> authors = jdbc.query("SELECT id, name FROM author WHERE name = :name", Map.of("name", name), ROW_MAPPER);
        return DataAccessUtils.singleResult(authors);
    }
}

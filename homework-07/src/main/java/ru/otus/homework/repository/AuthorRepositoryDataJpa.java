package ru.otus.homework.repository;

import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Author;

import java.util.Optional;

@Component
public class AuthorRepositoryDataJpa implements AuthorRepository {

    private final CrudAuthorRepository crudAuthorRepository;

    public AuthorRepositoryDataJpa(CrudAuthorRepository crudAuthorRepository) {
        this.crudAuthorRepository = crudAuthorRepository;
    }

    @Override
    public Author save(Author author) {
        return crudAuthorRepository.save(author);
    }

    @Override
    public Optional<Author> getById(long id) {
        return crudAuthorRepository.findById(id);
    }

    @Override
    public Optional<Author> getByName(String name) {
        return crudAuthorRepository.getByName(name);
    }
}

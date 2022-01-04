package ru.otus.homework.repository;

import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Genre;

import java.util.Optional;

@Component
public class GenreRepositoryDataJpa implements GenreRepository {

    private final CrudGenreRepository crudGenreRepository;

    public GenreRepositoryDataJpa(CrudGenreRepository crudGenreRepository) {
        this.crudGenreRepository = crudGenreRepository;
    }

    @Override
    public Genre save(Genre genre) {
        return crudGenreRepository.save(genre);
    }

    @Override
    public Optional<Genre> getById(long id) {
        return crudGenreRepository.findById(id);
    }

    @Override
    public Optional<Genre> getByTitle(String title) {
        return crudGenreRepository.getByTitle(title);
    }
}

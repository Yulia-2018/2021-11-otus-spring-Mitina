package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre getById(long id) {
        return genreRepository.getById(id);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getOrCreate(Genre genre) {
        String title = genre.getTitle();
        Optional<Genre> genreFromBase = genreRepository.getByTitle(title);
        return (genreFromBase.isEmpty()) ? genreRepository.save(genre) : genreFromBase.get();
    }
}

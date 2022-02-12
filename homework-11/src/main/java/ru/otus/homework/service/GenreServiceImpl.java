package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exception.NotFoundException;
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
    public Genre getById(String id) {
        return genreRepository.findById(id).orElseThrow(() -> new NotFoundException("Genre " + id + " not exist"));
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getByTitleOrCreate(String title) {
        Optional<Genre> genreFromBase = genreRepository.getByTitle(title);
        if (genreFromBase.isEmpty()) {
            Genre genre = new Genre(title);
            return genreRepository.save(genre);
        } else {
            return genreFromBase.get();
        }
    }
}

package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public void update(Genre genre) throws NotFoundException {
        String id = genre.getId();
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.save(genre);
        } else {
            throw new NotFoundException("Genre " + id + " not exist");
        }
    }

    @Override
    public Genre getById(String id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Genre " + id + " not exist"));
    }

    @Override
    public List<Genre> getAll() {
        return repository.findAll();
    }
}

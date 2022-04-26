package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @HystrixCommand(fallbackMethod = "getAllFallback")
    @Override
    public List<Genre> getAll() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return genreRepository.findAll();
    }

    public List<Genre> getAllFallback() {
        Genre genre = new Genre("Refresh the page to load the list of genres");
        List<Genre> genres = new ArrayList<>();
        genres.add(genre);
        return genres;
    }

    @HystrixCommand
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

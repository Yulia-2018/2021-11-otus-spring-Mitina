package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Author;
import ru.otus.homework.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @HystrixCommand(fallbackMethod = "getAllFallback")
    @Override
    public List<Author> getAll() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return authorRepository.findAll();
    }

    public List<Author> getAllFallback() {
        Author author = new Author("Refresh the page to load the list of authors");
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        return authors;
    }

    @HystrixCommand
    @Override
    public Author getByNameOrCreate(String name) {
        Optional<Author> authorFromBase = authorRepository.getByName(name);
        if (authorFromBase.isEmpty()) {
            Author author = new Author(name);
            return authorRepository.save(author);
        } else {
            return authorFromBase.get();
        }
    }
}

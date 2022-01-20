package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Author;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void update(Author author) throws NotFoundException {
        String id = author.getId();
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.save(author);
        } else {
            throw new NotFoundException("Author " + id + " not exist");
        }
    }
}

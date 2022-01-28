package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Author;
import ru.otus.homework.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getById(long id) {
        return authorRepository.getById(id);
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

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

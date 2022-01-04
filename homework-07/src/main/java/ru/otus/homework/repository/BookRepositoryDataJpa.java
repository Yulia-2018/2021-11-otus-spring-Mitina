package ru.otus.homework.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

@Component
public class BookRepositoryDataJpa implements BookRepository {

    private final CrudBookRepository crudBookRepository;

    public BookRepositoryDataJpa(CrudBookRepository crudBookRepository) {
        this.crudBookRepository = crudBookRepository;
    }

    @Override
    public Book save(Book book) {
        return crudBookRepository.save(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        return crudBookRepository.findById(id);
    }

    @Override
    public List<Book> getAll() {
        return crudBookRepository.findAll(Sort.by("id"));
    }

    @Override
    public boolean deleteById(long id) {
        try {
            crudBookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }
}

package ru.otus.homework.service;

import ru.otus.homework.domain.Author;

import java.util.List;

public interface AuthorService {

    Author getById(String id);

    List<Author> getAll();

    Author getByNameOrCreate(String name);
}

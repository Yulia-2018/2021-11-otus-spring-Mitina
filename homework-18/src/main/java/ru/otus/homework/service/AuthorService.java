package ru.otus.homework.service;

import ru.otus.homework.domain.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    Author getByNameOrCreate(String name);
}

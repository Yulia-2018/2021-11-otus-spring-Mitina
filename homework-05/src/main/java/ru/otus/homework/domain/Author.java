package ru.otus.homework.domain;

import lombok.Data;

@Data
public class Author {

    private final long id;

    private final String name;

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
    }
}

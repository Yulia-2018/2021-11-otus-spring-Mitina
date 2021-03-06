package ru.otus.homework.domain;

import lombok.Data;

@Data
public class Author {

    private long id;

    private String name;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
    }
}

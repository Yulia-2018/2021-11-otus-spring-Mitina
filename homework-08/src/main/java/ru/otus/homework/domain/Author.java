package ru.otus.homework.domain;

import lombok.Data;

@Data
public class Author {

    private String id;

    private String name;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }
}

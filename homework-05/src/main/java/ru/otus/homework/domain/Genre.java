package ru.otus.homework.domain;

import lombok.Data;

@Data
public class Genre {

    private long id;

    private final String title;

    public Genre(String title) {
        this.title = title;
    }

    public Genre(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public void setId(long id) {
        this.id = id;
    }
}

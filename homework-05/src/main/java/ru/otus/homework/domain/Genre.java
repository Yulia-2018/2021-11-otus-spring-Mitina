package ru.otus.homework.domain;

import lombok.Data;

@Data
public class Genre {

    private long id;

    private String title;

    public Genre(String title) {
        this.title = title;
    }

    public Genre() {
    }

    public Genre(long id, String title) {
        this.id = id;
        this.title = title;
    }
}

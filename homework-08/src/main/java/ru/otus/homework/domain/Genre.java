package ru.otus.homework.domain;

import lombok.Data;

@Data
public class Genre {

    private String title;

    public Genre() {
    }

    public Genre(String title) {
        this.title = title;
    }
}

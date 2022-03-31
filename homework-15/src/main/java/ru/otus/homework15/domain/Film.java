package ru.otus.homework15.domain;

public class Film {

    private final String title;

    private final String shortDescription;

    public Film(String title, String shortDescription) {
        this.title = title;
        this.shortDescription = shortDescription;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }
}

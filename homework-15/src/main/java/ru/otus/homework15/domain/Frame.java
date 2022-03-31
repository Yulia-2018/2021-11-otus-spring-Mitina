package ru.otus.homework15.domain;

public class Frame {

    private final String description;

    private final Boolean isDouble;

    private final Boolean isDefect;

    public Frame(String description, Boolean isDouble, Boolean isDefect) {
        this.description = description;
        this.isDouble = isDouble;
        this.isDefect = isDefect;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getDouble() {
        return isDouble;
    }

    public Boolean getDefect() {
        return isDefect;
    }
}

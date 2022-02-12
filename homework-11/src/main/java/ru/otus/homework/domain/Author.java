package ru.otus.homework.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "authors")
public class Author {

    @Id
    private String id;

    private String name;

    public Author() {
    }

    public Author(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public Author(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

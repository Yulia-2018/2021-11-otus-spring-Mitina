package ru.otus.homework.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "genres")
public class Genre {

    @Id
    private String id;

    private String title;

    public Genre() {
    }

    public Genre(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
    }

    public Genre(String id, String title) {
        this.id = id;
        this.title = title;
    }
}

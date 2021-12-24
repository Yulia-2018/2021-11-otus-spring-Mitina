package ru.otus.homework.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    public Genre() {
    }

    public Genre(String title) {
        this.title = title;
    }

    public Genre(long id, String title) {
        this.id = id;
        this.title = title;
    }
}

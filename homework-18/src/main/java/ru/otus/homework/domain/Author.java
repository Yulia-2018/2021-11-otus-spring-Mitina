package ru.otus.homework.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "author")
public class Author {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = 100_000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private long id;

    @Column(name = "name", nullable = false, unique = true)
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

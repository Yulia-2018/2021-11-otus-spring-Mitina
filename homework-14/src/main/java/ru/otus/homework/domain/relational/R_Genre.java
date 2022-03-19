package ru.otus.homework.domain.relational;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "genre")
public class R_Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    public R_Genre() {
    }

    public R_Genre(String title) {
        this.title = title;
    }
}

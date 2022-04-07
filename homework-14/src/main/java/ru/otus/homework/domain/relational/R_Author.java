package ru.otus.homework.domain.relational;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "author")
public class R_Author {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = 100_000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public R_Author() {
    }

    public R_Author(String name) {
        this.name = name;
    }
}

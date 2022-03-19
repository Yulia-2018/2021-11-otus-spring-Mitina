package ru.otus.homework.domain.relational;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comment")
public class R_Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private long id;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private R_Book book;

    public R_Comment() {
    }
}

package ru.otus.homework.dto;

import lombok.Getter;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

@Getter
public class BookDto {

    private long id;

    private String title;

    private String authorName;

    private String genreTitle;

    public BookDto() {
    }

    public BookDto(long id, String title, String authorName, String genreTitle) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.genreTitle = genreTitle;
    }

    public Book toDomain() {
        return new Book(id, title, new Author(authorName), new Genre(genreTitle));
    }

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getTitle());
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", genreTitle='" + genreTitle + '\'' +
                '}';
    }
}

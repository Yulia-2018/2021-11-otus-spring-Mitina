package ru.otus.homework.dto;

import lombok.Data;
import ru.otus.homework.domain.Book;

@Data
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

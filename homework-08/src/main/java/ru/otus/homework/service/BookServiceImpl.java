package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.CommentRepository;

import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, CommentRepository commentRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book insert(Book book) {
        addAuthorAndGenre(book);
        book.setId(UUID.randomUUID().toString());
        return bookRepository.save(book);
    }

    @Override
    public void update(Book book) {
        String id = book.getId();
        boolean exists = bookRepository.existsById(id);
        if (exists) {
            addAuthorAndGenre(book);
            bookRepository.save(book);
        } else {
            throw new NotFoundException("Book " + id + " not exist");
        }
    }

    @Override
    public Book getById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book " + id + " not exist"));
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        Book book = getById(id);
        book.getComments().forEach(commentRepository::delete);
        bookRepository.delete(book);
    }

    private void addAuthorAndGenre(Book book) {
        Author author = authorRepository.getOrCreate(book.getAuthor());
        //Genre genre = genreService.getOrCreate(book.getGenre());
        book.setAuthor(author);
        //book.setGenre(genre);
    }
}

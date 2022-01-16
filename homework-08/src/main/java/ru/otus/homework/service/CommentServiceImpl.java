package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.CommentRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookService bookService;

    public CommentServiceImpl(CommentRepository repository, BookService bookService) {
        this.commentRepository = repository;
        this.bookService = bookService;
    }

    @Override
    public Comment insert(Comment comment) throws NotFoundException {
        Book book = bookService.getById(comment.getBook().getId());
        comment.setBook(book);
        return commentRepository.save(comment);
    }

    @Override
    public void update(Comment comment) throws NotFoundException {
        getById(comment.getId());
        Book book = bookService.getById(comment.getBook().getId());
        comment.setBook(book);
        commentRepository.save(comment);
    }

    @Override
    public Comment getById(long id) throws NotFoundException {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment " + id + " not exist"));
    }

    @Override
    public List<Comment> getAllForBook(String bookId) {
        Book book = bookService.getById(bookId);
        return book.getComments();
    }

    @Override
    public void deleteById(long id) throws NotFoundException {
        Comment comment = getById(id);
        commentRepository.delete(comment);
    }
}

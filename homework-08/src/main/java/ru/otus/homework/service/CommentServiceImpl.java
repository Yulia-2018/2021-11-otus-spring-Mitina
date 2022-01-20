package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.CommentRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookService bookService;

    public CommentServiceImpl(CommentRepository repository, BookService bookService) {
        this.commentRepository = repository;
        this.bookService = bookService;
    }

    @Override
    public Comment insert(Comment comment, String bookId) throws NotFoundException {
        Book book = bookService.getById(bookId);
        comment.setId(UUID.randomUUID().toString());
        book.addComment(comment);
        bookService.update(book);
        return commentRepository.save(comment);
    }

    @Override
    public void update(Comment comment) throws NotFoundException {
        String id = comment.getId();
        boolean exists = commentRepository.existsById(id);
        if (exists) {
            commentRepository.save(comment);
        } else {
            throw new NotFoundException("Comment " + id + " not exist");
        }
    }

    @Override
    public Comment getById(String id) throws NotFoundException {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment " + id + " not exist"));
    }

    @Override
    public List<Comment> getAllForBook(String bookId) {
        Book book = bookService.getById(bookId);
        return book.getComments();
    }

    @Override
    public void deleteById(String id) throws NotFoundException {
        Comment comment = getById(id);
        commentRepository.delete(comment);
    }
}

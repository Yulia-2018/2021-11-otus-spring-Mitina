package ru.otus.homework.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @Override
    public Comment insert(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void update(Comment comment) throws NotFoundException {
        getById(comment.getId());
        commentRepository.save(comment);
    }

    @Override
    public Comment getById(long id) throws NotFoundException {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment " + id + " not exist"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllForBook(long bookId) throws NotFoundException {
        Book book = bookService.getById(bookId);
        List<Comment> comments = book.getComments();
        Hibernate.initialize(comments);
        return comments;
    }

    @Transactional
    @Override
    public void deleteById(long id) throws NotFoundException {
        Comment comment = getById(id);
        commentRepository.delete(comment);
    }
}

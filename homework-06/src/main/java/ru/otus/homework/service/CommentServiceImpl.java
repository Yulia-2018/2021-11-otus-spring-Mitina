package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository repository, BookRepository bookRepository) {
        this.commentRepository = repository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public Comment insert(Comment comment, long bookId) throws NotFoundException {
        Book book = getBook(bookId);
        comment.setBook(book);
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void update(Comment comment, long bookId) throws NotFoundException {
        getCommentForBook(comment.getId(), bookId);
        Book book = getBook(bookId);
        comment.setBook(book);
        commentRepository.save(comment);
    }

    @Override
    public Comment getById(long id, long bookId) throws NotFoundException {
        return getCommentForBook(id, bookId);
    }

    @Override
    public List<Comment> getAllForBook(long bookId) {
        return commentRepository.getAllForBook(bookId);
    }

    @Transactional
    @Override
    public void deleteById(long id, long bookId) throws NotFoundException {
        Comment comment = getCommentForBook(id, bookId);
        commentRepository.delete(comment);
    }

    private Book getBook(long bookId) throws NotFoundException {
        Optional<Book> book = bookRepository.getById(bookId);
        if (book.isEmpty()) {
            throw new NotFoundException("Book " + bookId + " not exist");
        }
        return book.get();
    }

    private Comment getCommentForBook(long id, long bookId) {
        Optional<Comment> comment = commentRepository.getById(id);
        if (comment.isEmpty()) {
            throw new NotFoundException("Comment " + id + " not exist");
        } else if (comment.get().getBook().getId() != bookId) {
            throw new NotFoundException("Comment " + id + " for book " + bookId + " not exist");
        }
        return comment.get();
    }
}

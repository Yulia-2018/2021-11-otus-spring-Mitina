package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.exception.NotFoundException;
import ru.otus.homework.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

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
    public Comment insert(Comment comment, long bookId) throws NotFoundException {
        Book book = bookService.getById(bookId);
        comment.setBook(book);
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void update(Comment comment, long bookId) throws NotFoundException {
        getCommentForBook(comment.getId(), bookId);
        Book book = bookService.getById(bookId);
        comment.setBook(book);
        commentRepository.save(comment);
    }

    @Override
    public Comment getById(long id, long bookId) throws NotFoundException {
        return getCommentForBook(id, bookId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllForBook(long bookId) {
        Book book = bookService.getById(bookId);
        //System.out.println(book.getComments());
        return book.getComments();
    }

    @Transactional
    @Override
    public void deleteById(long id, long bookId) throws NotFoundException {
        Comment comment = getCommentForBook(id, bookId);
        commentRepository.delete(comment);
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

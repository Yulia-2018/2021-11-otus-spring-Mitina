package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;

import java.util.Iterator;
import java.util.List;

@Controller
public class CommentController {

    private final CommentService commentService;

    private final BookService bookService;

    public CommentController(CommentService commentService, BookService bookService) {
        this.commentService = commentService;
        this.bookService = bookService;
    }

    @GetMapping("/{bookId}/comments")
    public String listPage(@PathVariable("bookId") int bookId, Model model) {
        List<Comment> comments = commentService.getAllForBook(bookId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("comments", comments);
        return "comments";
    }

    @PostMapping("/{bookId}/comments")
    public String saveComment(@PathVariable("bookId") int bookId, @RequestParam("comment-text") List<String> commentsText) {
        Book book = bookService.getById(bookId);
        List<Comment> comments = book.getComments();
        Iterator<Comment> iterator = comments.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Comment comment = iterator.next();
            String text = commentsText.get(i);
            i++;
            if (text.trim().isEmpty()) {
                iterator.remove();
                commentService.deleteById(comment.getId());
            } else {
                comment.setText(text);
                commentService.update(comment);
            }
        }

        String newText = commentsText.get(i).trim();
        if (!newText.isEmpty()) {
            Comment newComment = new Comment(newText, book);
            commentService.insert(newComment);
            comments.add(newComment);
        }
        book.setComments(comments);
        return "redirect:/";
    }
}

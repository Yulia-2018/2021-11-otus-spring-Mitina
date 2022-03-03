package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.service.CommentService;

import java.util.List;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{bookId}/comments")
    public String listPage(@PathVariable("bookId") int bookId, Model model) {
        List<Comment> comments = commentService.getAllForBook(bookId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("comments", comments);
        return "comments";
    }

    @PostMapping("/{bookId}/comments")
    public String saveComment(@PathVariable("bookId") int bookId, @RequestParam("commentsText") List<String> commentsText) {
        commentService.deleteAllForBook(bookId);
        commentService.insertAllForBook(bookId, commentsText);
        return "redirect:/";
    }
}

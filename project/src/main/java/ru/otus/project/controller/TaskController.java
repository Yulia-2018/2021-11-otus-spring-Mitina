package ru.otus.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.project.domain.Task;
import ru.otus.project.security.SecurityUtil;
import ru.otus.project.service.TaskService;

import javax.validation.Valid;

@Controller
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String listPage() {
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam Long id, Model model) {
        Long userId = SecurityUtil.authUserId();
        Task task;
        if (id != 0) {
            task = service.getById(id, userId);
        } else {
            task = new Task();
            task.setId(0L);
        }
        model.addAttribute("task", task);
        return "edit";
    }

    @PostMapping("/edit")
    public String save(@Valid @ModelAttribute("task") Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        Long userId = SecurityUtil.authUserId();
        Long id = task.getId();
        if (id != 0) {
            service.update(task, userId);
        } else {
            service.insert(task, userId);
        }
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        Long userId = SecurityUtil.authUserId();
        service.deleteById(id, userId);
        return "redirect:/";
    }
}

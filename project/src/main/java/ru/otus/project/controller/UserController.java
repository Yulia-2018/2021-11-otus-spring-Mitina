package ru.otus.project.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.project.domain.Role;
import ru.otus.project.domain.User;
import ru.otus.project.dto.UserDto;
import ru.otus.project.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        Role role_user = new Role("ROLE_USER");
        User user = new User(userDto.getName(), encodedPassword);
        user.addRole(role_user);
        service.insert(user);
        return "redirect:/";
    }
}

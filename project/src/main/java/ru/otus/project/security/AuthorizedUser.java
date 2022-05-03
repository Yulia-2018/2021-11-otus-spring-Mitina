package ru.otus.project.security;

import lombok.Getter;
import ru.otus.project.domain.User;

@Getter
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public AuthorizedUser(User user) {
        super(user.getName(), user.getPassword(), user.getRoles());
        this.user = user;
    }
}

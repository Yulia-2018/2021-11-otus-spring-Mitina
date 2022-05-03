package ru.otus.project.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.project.domain.User;
import ru.otus.project.repository.UserRepository;

import java.util.Optional;

import static org.springframework.security.core.userdetails.User.builder;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.getByName(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        User user = optionalUser.get();
        return builder().username(username).password(user.getPassword()).authorities(user.getRoles()).build();
    }
}

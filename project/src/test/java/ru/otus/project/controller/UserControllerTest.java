package ru.otus.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.project.domain.Role;
import ru.otus.project.domain.User;
import ru.otus.project.service.CustomUserDetailsService;
import ru.otus.project.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Test
    void register() throws Exception {
        mvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @WithMockUser(
            username = "User",
            authorities = {"ROLE_USER"}
    )
    @Test
    void registerUserForbidden() throws Exception {
        mvc.perform(get("/register"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(
            username = "Admin",
            authorities = {"ROLE_USER", "ROLE_ADMIN"}
    )
    @Test
    void registerAdminForbidden() throws Exception {
        mvc.perform(get("/register"))
                .andExpect(status().isForbidden());
    }

    @Test
    void insert() throws Exception {

        User createdUser = new User("New user", "New password");

        mvc.perform(post("/register")
                .param("name", createdUser.getName()).param("password", createdUser.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        createdUser.addRole(new Role("ROLE_USER"));
        verify(service, times(1)).insert(createdUser);
    }

    @WithMockUser(
            username = "User",
            authorities = {"ROLE_USER"}
    )
    @Test
    void insertUserForbidden() throws Exception {

        User createdUser = new User("New user", "New password");

        mvc.perform(post("/register")
                .param("name", createdUser.getName()).param("password", createdUser.getPassword()))
                .andExpect(status().isForbidden());

        verify(service, times(0)).insert(any());
    }

    @WithMockUser(
            username = "Admin",
            authorities = {"ROLE_USER", "ROLE_ADMIN"}
    )
    @Test
    void insertAdminForbidden() throws Exception {

        User createdUser = new User("New user", "New password");

        mvc.perform(post("/register")
                .param("name", createdUser.getName()).param("password", createdUser.getPassword()))
                .andExpect(status().isForbidden());

        verify(service, times(0)).insert(any());
    }
}
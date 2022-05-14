package ru.otus.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.project.service.CustomUserDetailsService;
import ru.otus.project.service.UserService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.project.TestData.*;

@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService service;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @WithMockUser(
            username = "Admin",
            authorities = {"ROLE_USER", "ROLE_ADMIN"}
    )
    @Test
    void getAllAdmin() throws Exception {

        when(service.getAll()).thenReturn(List.of(USER, ADMIN));

        mvc.perform(get("/rest/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(List.of(USER, ADMIN))));

        verify(service, times(1)).getAll();
    }

    @WithMockUser(
            username = "User",
            authorities = {"ROLE_USER"}
    )
    @Test
    void getAllUserForbidden() throws Exception {

        mvc.perform(get("/rest/user"))
                .andExpect(status().isForbidden());

        verify(service, times(0)).getAll();
    }

    @Test
    void getAllUnauthorized() throws Exception {

        mvc.perform(get("/rest/user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));

        verify(service, times(0)).getAll();
    }

    @WithMockUser(
            username = "Admin",
            authorities = {"ROLE_USER", "ROLE_ADMIN"}
    )
    @Test
    void getByIdAdmin() throws Exception {

        when(service.getById(USER_ID)).thenReturn(USER);

        mvc.perform(get("/rest/user/" + USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(USER)));

        verify(service, times(1)).getById(USER_ID);
    }

    @WithMockUser(
            username = "User",
            authorities = {"ROLE_USER"}
    )
    @Test
    void getByIdUserForbidden() throws Exception {

        mvc.perform(get("/rest/user/" + USER_ID))
                .andExpect(status().isForbidden());

        verify(service, times(0)).getById(USER_ID);
    }

    @Test
    void getByIdUnauthorized() throws Exception {

        mvc.perform(get("/rest/user/" + USER_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));

        verify(service, times(0)).getById(USER_ID);
    }
}
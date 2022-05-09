package ru.otus.project.controller;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.project.security.SecurityUtil;
import ru.otus.project.service.CustomUserDetailsService;
import ru.otus.project.service.TaskService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.project.TestData.*;

@WebMvcTest(TaskRestController.class)
class TaskRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaskService service;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @WithMockUser(
            username = "Admin",
            authorities = {"ROLE_USER", "ROLE_ADMIN"}
    )
    @Test
    void getAll() throws Exception {

        try (MockedStatic mocked = mockStatic(SecurityUtil.class)) {
            mocked.when(SecurityUtil::authUserId).thenReturn(ADMIN_ID);
            assertEquals(ADMIN_ID, SecurityUtil.authUserId());

            when(service.getAll(ADMIN_ID)).thenReturn(List.of(TASK_2_FOR_ADMIN, TASK_1_FOR_ADMIN));

            mvc.perform(get("/rest/task"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

            verify(service, times(1)).getAll(ADMIN_ID);
        }
    }

    @WithMockUser(
            username = "Admin",
            authorities = {"ROLE_USER", "ROLE_ADMIN"}
    )
    @Test
    void getById() throws Exception {

        try (MockedStatic mocked = mockStatic(SecurityUtil.class)) {
            mocked.when(SecurityUtil::authUserId).thenReturn(ADMIN_ID);
            assertEquals(ADMIN_ID, SecurityUtil.authUserId());

            when(service.getById(TASK_2_ID_FOR_ADMIN, ADMIN_ID)).thenReturn(TASK_2_FOR_ADMIN);

            mvc.perform(get("/rest/task/" + TASK_2_ID_FOR_ADMIN))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

            verify(service, times(1)).getById(TASK_2_ID_FOR_ADMIN, ADMIN_ID);
        }
    }

    @WithMockUser(
            username = "User",
            authorities = {"ROLE_USER"}
    )
    @Test
    void deleteById() throws Exception {

        try (MockedStatic mocked = mockStatic(SecurityUtil.class)) {
            mocked.when(SecurityUtil::authUserId).thenReturn(USER_ID);
            assertEquals(USER_ID, SecurityUtil.authUserId());

            mvc.perform(delete("/rest/task/" + TASK_2_ID_FOR_USER))
                    .andExpect(status().isNoContent());

            verify(service, times(1)).deleteById(TASK_2_ID_FOR_USER, USER_ID);
        }
    }

    @Test
    void getAllUnauthorized() throws Exception {

        mvc.perform(get("/rest/task"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void getByIdUnauthorized() throws Exception {

        mvc.perform(get("/rest/task/" + TASK_2_ID_FOR_ADMIN))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void deleteByIdUnauthorized() throws Exception {

        mvc.perform(delete("/rest/task/" + TASK_2_ID_FOR_USER))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }
}
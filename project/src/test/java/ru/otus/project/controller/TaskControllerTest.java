package ru.otus.project.controller;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.project.domain.Task;
import ru.otus.project.security.SecurityUtil;
import ru.otus.project.service.CustomUserDetailsService;
import ru.otus.project.service.TaskService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.project.TestData.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaskService service;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @WithMockUser(
            username = "User",
            authorities = {"ROLE_USER"}
    )
    @Test
    void listPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"));
    }

    @WithMockUser(
            username = "Admin",
            authorities = {"ROLE_USER", "ROLE_ADMIN"}
    )
    @Test
    void editPage() throws Exception {

        try (MockedStatic mocked = mockStatic(SecurityUtil.class)) {
            mocked.when(SecurityUtil::authUserId).thenReturn(ADMIN_ID);
            assertEquals(ADMIN_ID, SecurityUtil.authUserId());

            when(service.getById(TASK_1_ID_FOR_ADMIN, ADMIN_ID)).thenReturn(TASK_1_FOR_ADMIN);

            mvc.perform(get("/edit").param("id", String.valueOf(TASK_1_ID_FOR_ADMIN)))
                    .andExpect(status().isOk());

            verify(service, times(1)).getById(TASK_1_ID_FOR_ADMIN, ADMIN_ID);
        }
    }

    @WithMockUser(
            username = "User",
            authorities = {"ROLE_USER"}
    )
    @Test
    void update() throws Exception {

        try (MockedStatic mocked = mockStatic(SecurityUtil.class)) {
            mocked.when(SecurityUtil::authUserId).thenReturn(USER_ID);
            assertEquals(USER_ID, SecurityUtil.authUserId());

            Task updatedTask = new Task(TASK_1_ID_FOR_USER, "New description", LocalDate.now(), true);

            mvc.perform(post("/edit")
                    .param("id", String.valueOf(updatedTask.getId())).param("description", updatedTask.getDescription())
                    .param("deadline", String.valueOf(updatedTask.getDeadline())).param("done", String.valueOf(updatedTask.getDone())))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));

            verify(service, times(1)).update(updatedTask, USER_ID);
            verify(service, times(1)).update(any(), anyLong());
            verify(service, times(0)).insert(any(), anyLong());
        }
    }

    @WithMockUser(
            username = "User",
            authorities = {"ROLE_USER"}
    )
    @Test
    void insert() throws Exception {

        try (MockedStatic mocked = mockStatic(SecurityUtil.class)) {
            mocked.when(SecurityUtil::authUserId).thenReturn(USER_ID);
            assertEquals(USER_ID, SecurityUtil.authUserId());

            Task createdTask = new Task(0L, "New description", LocalDate.now(), true);

            mvc.perform(post("/edit")
                    .param("id", String.valueOf(createdTask.getId())).param("description", createdTask.getDescription())
                    .param("deadline", String.valueOf(createdTask.getDeadline())).param("done", String.valueOf(createdTask.getDone())))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));

            verify(service, times(0)).update(any(), anyLong());
            verify(service, times(1)).insert(createdTask, USER_ID);
            verify(service, times(1)).insert(any(), anyLong());
        }
    }

    @WithMockUser(
            username = "Admin",
            authorities = {"ROLE_USER", "ROLE_ADMIN"}
    )
    @Test
    void delete() throws Exception {

        try (MockedStatic mocked = mockStatic(SecurityUtil.class)) {
            mocked.when(SecurityUtil::authUserId).thenReturn(ADMIN_ID);
            assertEquals(ADMIN_ID, SecurityUtil.authUserId());

            mvc.perform(get("/delete").param("id", String.valueOf(TASK_1_ID_FOR_ADMIN)))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));

            verify(service, times(1)).deleteById(TASK_1_ID_FOR_ADMIN, ADMIN_ID);
        }
    }

    @Test
    void listPageUnauthorized() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void editPageUnauthorized() throws Exception {
        mvc.perform(get("/edit").param("id", String.valueOf(TASK_1_ID_FOR_ADMIN)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void saveUnauthorized() throws Exception {
        mvc.perform(post("/edit")
                .param("id", String.valueOf(TASK_1_ID_FOR_ADMIN)).param("description", "New description")
                .param("deadline", String.valueOf(LocalDate.now())).param("done", String.valueOf(true)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    void deleteUnauthorized() throws Exception {
        mvc.perform(get("/delete").param("id", String.valueOf(TASK_1_ID_FOR_ADMIN)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }
}
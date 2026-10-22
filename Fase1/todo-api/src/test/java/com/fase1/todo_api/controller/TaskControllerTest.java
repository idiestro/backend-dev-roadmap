package com.fase1.todo_api.controller;

import com.fase1.todo_api.dto.TaskCreateRequest;
import com.fase1.todo_api.dto.TaskResponse;
import com.fase1.todo_api.dto.TaskUpdateRequest;
import com.fase1.todo_api.exception.TaskNotFoundException;
import com.fase1.todo_api.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;    //Intern Postman
    @MockitoBean
    private TaskService taskService;    //False service: we control it


    // 1 - getTaskById
    @Test
    @DisplayName("GET /api/tasks/{id} -> 200 if task exists")
    void getTaskById_whenExists_returns200() throws Exception {
        //Given: service returns this task
        TaskResponse task = new TaskResponse(1L, "Comprar pan", "Integral", false);
        when(taskService.findById(1L)).thenReturn(task);

        //When + Then = Do petition and validate response
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Comprar pan"))
                .andExpect(jsonPath("$.description").value("Integral"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    @DisplayName("GET /api/tasks/{id} -> 404 ProblemDetail if not exists")
    void getTraskById_whenNotExists_returns404() throws Exception {
        //Given: service launch the petition
        when(taskService.findById(999L)).thenThrow(new TaskNotFoundException(999L));

        //When + Then
        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("Task 999 not found"));
    }

    // 2 - getTasks
    //Always returns 200
    @Test
    @DisplayName("GET /api/tasks -> 200 with all available tasks")
    void getAllTasks_returnsList() throws Exception {
        //Given
        when(taskService.findAll()).thenReturn(List.of(new TaskResponse(1L, "Comprar pan", "Integral", false)));

        //When + Then
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk());
    }


    // 3 - createTask
    @Test
    @DisplayName("POST /api/tasks -> 201 if task created")
    void createTask_whenValid_returrns201() throws Exception {
        //Given
        TaskResponse taskResponse = new TaskResponse(1L, "Comprar pan", "Integral", false);
        when(taskService.createTask(any())).thenReturn(taskResponse);

        //When + Then
        String taskCreatePayload = """
                {"title": "Comprar pan", "description": "Integral"}
                """;
        mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(taskCreatePayload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/tasks/1")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Comprar pan"))
                .andExpect(jsonPath("$.description").value("Integral"));

    }

    @Test
    @DisplayName("POST /api/tasks -> 400 ProblemDetail if invalid parameters")
    void createTask_whenTitleBlank_returns400() throws Exception {
        //Given
        //No hay que preparar nada porque el service no llega a llamarse

        //When + Then
        String taskCreatePayload = """
                {"title": "", "description": "Integral"}
                """;
        mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(taskCreatePayload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.errors.title").exists());
    }

    // 4 - updateTask
    @Test
    @DisplayName("PUT /api/tasks/{id} -> 404 ProblemDetail if not exists")
    void updateTask_whenNotExists_returns404() throws Exception {
        //Given
        when(taskService.updateTask(eq(999L), any())).thenThrow(new TaskNotFoundException(999L));

        //When + Then
        String taskCreatePayload = """
                {"title": "Comprar pan", "description": "Integral", "completed": false}
                """;
        mockMvc.perform(put("/api/tasks/999").contentType(MediaType.APPLICATION_JSON).content(taskCreatePayload))
                .andExpect(status().isNotFound());
    }

    // 5 - deleteTask
    @Test
    @DisplayName("DELETE /api/tasks/{id} -> 204 if task exists")
    void deleteTask_whenExists_return204() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/tasks/{id} -> 404 if task not exists")
    void deleteTask_whenNotExists_return404() throws Exception {
        //Given
        doThrow(new TaskNotFoundException(999L)).when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNotFound());
    }

}
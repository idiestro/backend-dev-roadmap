package com.fase1.todo_api;

import com.fase1.todo_api.dto.TaskCreateRequest;
import com.fase1.todo_api.dto.TaskResponse;
import com.fase1.todo_api.entity.Task;
import com.fase1.todo_api.mapper.TaskMapper;
import com.fase1.todo_api.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest                                 // App completa
@AutoConfigureMockMvc                           // Para usar MockMvc
@Import(TestcontainersConfiguration.class)      //PostgreSql real
public class TaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository repository;      //Preparar y comprobar la DB directamente

    @BeforeEach
    void cleanDatabase() {                   //Aislamiento: cada test empieza con la DB vacía
        repository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/tasks -> La tarea se guarda de verdad en la DB")
    void createTask_persistInDatabase() throws Exception {
        //When: Petición real, sin mocks
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title": "Comprar pan", "description": "Integral"}
                                """))
                .andExpect(status().isCreated());

        //Then: comprobamos la DB
        List<Task> tasks = repository.findAll();
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Comprar pan");
        assertThat(tasks.get(0).isCompleted()).isFalse();
    }

    @Test
    @DisplayName("GET /api/tasks/{id} -> Buscar tarea existente en DB")
    void getTaskById_whenExistsInDB_returns200() throws Exception {
        //Given: creamos tarea
        Task saved = repository.save(new Task("Comprar pan", "Integral"));

        //When: petición GET real
        mockMvc.perform(get("/api/tasks/" + saved.getId()))
                .andExpect(status().isOk())
                //Then: comprobamos tarea obtenida
                .andExpect(jsonPath("$.title").value("Comprar pan"))
                .andExpect(jsonPath("$.description").value("Integral"));

    }

    @Test
    @DisplayName("PUT /api/tasks/{id} -> Modificar tarea existente en DB")
    void updateTask_updatesInDatabase() throws Exception {
        //Given: creamos tarea
        Task saved = repository.save(new Task("Comprar pan", "Integral"));

        //When: modificaamos tarea
        mockMvc.perform(put("/api/tasks/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title": "Comprar pan", "description": "Integral","completed": true}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));

        //Then: validamos bd
        Task task = repository.findById(saved.getId()).orElseThrow();
        assertThat(task.isCompleted()).isTrue();
    }

    @Test
    @DisplayName("DELETE /api/tasks/{id} -> Eliminar tarea existente en DB")
    void deleteTask_removesFromDatabase() throws Exception {
        //Given: creamos tarea
        Task saved = repository.save(new Task("Comprar pan", "Integral"));

        //When: eliminamos tarea
        mockMvc.perform(delete("/api/tasks/" + saved.getId()))
                .andExpect(status().isNoContent());

        //Then: validamos bd
        assertThat(repository.findById(saved.getId())).isEmpty();
    }

    @Test
    @DisplayName("GET /api/tasks/{id} -> Buscar tarea NO existente en DB")
    void getTaskById_whenNotInDb_returns404() throws Exception {
        //Given: DB vacía

        //When: petición GET real
        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound());

        //Then: validamos bd
        Task task = repository.findById(999L).orElse(null);
        assertThat(task).isNull();
    }

    @Test
    @DisplayName("Complete /api/tasks -> POST-GET y comparación en DB")
    void createThenGetViaLocation_returnsSameTask() throws Exception {
        //POST
        String location = mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title": "Comprar pan", "description": "Integral"}
                                """))
                //Validamos response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Comprar pan"))
                .andExpect(jsonPath("$.description").value("Integral"))
                .andExpect(jsonPath("$.completed").value(false))
                .andReturn().getResponse().getHeader("Location");

        List<Task> tasks = repository.findAll();
        assertThat(tasks).hasSize(1);
        //Validamos guardado en DB
        assertThat(tasks.getFirst().getTitle()).isEqualTo("Comprar pan");
        assertThat(tasks.getFirst().getDescription()).isEqualTo("Integral");
        assertThat(tasks.getFirst().isCompleted()).isFalse();

        //GET
        mockMvc.perform(get(location))
                //Validamos response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Comprar pan"))
                .andExpect(jsonPath("$.description").value("Integral"))
                .andExpect(jsonPath("$.completed").value(false));
    }
}

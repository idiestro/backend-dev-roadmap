package com.fase1.todo_api.controller;

import com.fase1.todo_api.entity.Task;
import com.fase1.todo_api.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    //Set POST payload
    public record Payload(String title, String description, boolean completed) {
    }

    //Get all tasks (Always 200 response)
    @GetMapping
    public List<Task> getTasks() {
        return repository.findAll();
    }

    //Get one task by id (200 or 404 response)
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return repository.findById(id)                       //Optional task
                .map(ResponseEntity::ok)                      // 200 if exist
                .orElse(ResponseEntity.notFound().build());   // 404 if doesn't exist
    }

    //Create new task (201 + created task + locatioon)
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Payload task) {
        Task savedTask = repository.save(new Task(task.title(), task.description()));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // /api/tasks
                .path("/{id}")                                          // + /{id}
                .buildAndExpand(savedTask.getId())                      // id = X
                .toUri();                                               // /api/tasks/X

        return ResponseEntity.created(location).body(savedTask);
    }

    //Update task (200 + updatedTask or 404)
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Payload request) {
        return repository.findById(id)
                .map(task -> {
                    task.setTitle(request.title);
                    task.setDescription(request.description);
                    task.setCompleted(request.completed);
                    return ResponseEntity.ok(repository.save(task));
                })
                .orElse(ResponseEntity.notFound().build());

    }

    //Delete task (204 or  404)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}

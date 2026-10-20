package com.fase1.todo_api.controller;

import com.fase1.todo_api.dto.TaskCreateRequest;
import com.fase1.todo_api.dto.TaskResponse;
import com.fase1.todo_api.dto.TaskUpdateRequest;
import com.fase1.todo_api.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService service) {
        this.taskService = service;
    }


    //Get all tasks
    @GetMapping
    public List<TaskResponse> getTasks() {
        return taskService.findAll();
    }

    //Get one task by id
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    //Create new task (201 + created task + locatioon)
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskCreateRequest request) {
        TaskResponse savedTask = taskService.createTask(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // /api/tasks
                .path("/{id}")                                          // + /{id}
                .buildAndExpand(savedTask.id())                         // id = X
                .toUri();                                               // /api/tasks/X

        return ResponseEntity.created(location).body(savedTask);
    }

    //Update task (200 + updatedTask or 404)
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @RequestBody TaskUpdateRequest request) {
        return taskService.updateTask(id, request);
    }

    //Delete task (204 or  404)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}

package com.fase1.todo_api.controller;

import com.fase1.todo_api.dto.TaskCreateRequest;
import com.fase1.todo_api.dto.TaskResponse;
import com.fase1.todo_api.dto.TaskUpdateRequest;
import com.fase1.todo_api.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@Tag(name = "Tasks", description = "CRUD operations for tasks")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService service) {
        this.taskService = service;
    }


    //Get all tasks
    @Operation(summary = "Get list of tasks", description = "Get all tasks available")
    @GetMapping
    public List<TaskResponse> getTasks() {
        return taskService.findAll();
    }

    //Get one task by id
    @Operation(summary = "Get task", description = "Get a single task by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))),

            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@Parameter(description = "Task Id", example = "1") @PathVariable Long id) {
        return taskService.findById(id);
    }

    //Create new task (201 + created task + location)
    @Operation(summary = "Create task",
            description = "Create a new task. New task always starts as not completed")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task created",
                    headers = @Header(name = "Location", description = "URL of the created task"),
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))),

            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskCreateRequest request) {
        TaskResponse savedTask = taskService.createTask(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // /api/tasks
                .path("/{id}")                                          // + /{id}
                .buildAndExpand(savedTask.id())                         // id = X
                .toUri();                                               // /api/tasks/X

        return ResponseEntity.created(location).body(savedTask);
    }

    //Update task (200 + updatedTask or 404)
    @Operation(summary = "Update task", description = "Update a task identified by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))),

            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),

            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequest request) {
        return taskService.updateTask(id, request);
    }

    //Delete task (204 or  404)
    @Operation(summary = "Delete task", description = "Delete a task identified by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Task deleted"),

            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}

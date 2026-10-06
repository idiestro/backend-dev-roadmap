package com.fase1.todo_api.controller;

import com.fase1.todo_api.entity.Task;
import com.fase1.todo_api.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    //Set POST payload
    public record Payload(Long id, String name, String description){}

    //Get all tasks
    @GetMapping
    public List<Task> getTasks(){
        return repository.findAll();
    }

    //Get one task by id
    @GetMapping("/{id}")
    @ResponseStatus()
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return repository.findById(id)                        //Optional task
                .map(ResponseEntity::ok)                      // 200 if exist
                .orElse(ResponseEntity.notFound().build());   // 404 if doesn't exist
    }

    //Create new task
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewTask(@RequestBody Payload task){
        repository.save(new Task(task.name, task.description));
    }

    //Update task
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void updateTask(@RequestBody Payload task){
        Task existedTask = repository.findById(task.id).orElseThrow();
        existedTask.setCompleted(true);
        repository.save(existedTask);
    }

    //Delete task
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Task> deleteTask(@PathVariable Long id){
        return repository.deleteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}

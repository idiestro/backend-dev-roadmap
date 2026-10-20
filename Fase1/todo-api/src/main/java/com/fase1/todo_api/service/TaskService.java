package com.fase1.todo_api.service;

import com.fase1.todo_api.dto.TaskCreateRequest;
import com.fase1.todo_api.dto.TaskResponse;
import com.fase1.todo_api.dto.TaskUpdateRequest;
import com.fase1.todo_api.entity.Task;
import com.fase1.todo_api.exception.TaskNotFoundException;
import com.fase1.todo_api.mapper.TaskMapper;
import com.fase1.todo_api.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;

    public TaskService(TaskRepository repository, TaskMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    //Get one task by Id
    public TaskResponse findById(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return mapper.toResponse(task);
    }

    //Get tasks list
    public List<TaskResponse> findAll() {
        return repository.findAll().stream()        //Task list
                .map(mapper::toResponse)            //Every task -> TaskResponse
                .toList();
    }

    //Create new task
    public TaskResponse createTask(TaskCreateRequest request) {
        Task task = repository.save(mapper.toEntity(request));
        return mapper.toResponse(task);
    }

    //Update Task
    public TaskResponse updateTask(Long id, TaskUpdateRequest request) {
        return repository.findById(id)
                .map(task -> {
                    task.setTitle(request.title());
                    task.setDescription(request.description());
                    task.setCompleted(request.completed());
                    return mapper.toResponse(repository.save(task));
                })
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    //Delete task
    public void deleteTask(Long id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }


}

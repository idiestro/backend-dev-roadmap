package com.fase1.todo_api.mapper;

import com.fase1.todo_api.dto.TaskCreateRequest;
import com.fase1.todo_api.dto.TaskResponse;
import com.fase1.todo_api.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    //Map Api request to Db object
    public Task toEntity(TaskCreateRequest request) {
        return new Task(request.title(), request.description());
    }

    //Map Db object to Api response
    public TaskResponse toResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted());
    }

}

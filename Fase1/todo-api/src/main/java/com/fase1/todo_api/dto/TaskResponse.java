package com.fase1.todo_api.dto;

public record TaskResponse(Long id, String title, String description, boolean completed) {
}

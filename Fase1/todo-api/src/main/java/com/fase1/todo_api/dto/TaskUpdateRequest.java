package com.fase1.todo_api.dto;

public record TaskUpdateRequest (String title, String description, boolean completed) {
}

package com.fase1.todo_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateRequest(
        @NotBlank @Size(min=1, max=100) String title,
        @Size(max = 500) String description) {
}
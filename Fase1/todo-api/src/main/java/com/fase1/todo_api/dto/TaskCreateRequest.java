package com.fase1.todo_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateRequest(
        @Schema(description = "Task title", example = "Pan")
        @NotBlank @Size(min=1, max=100) String title,

        @Schema(description = "Task description", example = "Integral")
        @Size(max = 500) String description) {
}
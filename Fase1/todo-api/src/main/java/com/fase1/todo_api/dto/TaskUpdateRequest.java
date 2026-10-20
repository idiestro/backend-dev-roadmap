package com.fase1.todo_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TaskUpdateRequest (
        @Schema(description = "Task Title", example = "Pan")
        @NotBlank @Size(max = 100) String title,

        @Schema(description = "Task Description", example = "Integral")
        @Size(max = 500) String description,

        @Schema(description = "Task status", example = "false")
        @NotNull Boolean completed) {
}

package com.fase1.todo_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TaskResponse(
        @Schema(description = "Task Id", example = "1")
        Long id,

        @Schema(description = "Task Title", example = "Pan")
        String title,

        @Schema(description = "Task Description", example = "Integral")
        String description,

        @Schema(description = "Task Status", example = "false")
        boolean completed) {
}

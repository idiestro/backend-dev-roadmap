package com.fase1.todo_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TaskUpdateRequest (
        @NotBlank @Size(max = 100) String title,
        @Size(max = 500) String description,
        @NotNull Boolean completed) {
}

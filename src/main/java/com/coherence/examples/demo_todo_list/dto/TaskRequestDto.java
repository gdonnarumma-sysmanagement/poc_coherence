package com.coherence.examples.demo_todo_list.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskRequestDto(
        @NotBlank(message = "Description must not be blank")
        String description,
        Boolean completed
) {
    public TaskRequestDto(String description) {
        this(description, false); // completed default a false
    }
}

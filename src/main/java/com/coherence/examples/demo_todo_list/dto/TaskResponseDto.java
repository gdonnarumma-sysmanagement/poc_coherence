package com.coherence.examples.demo_todo_list.dto;

public record TaskResponseDto(
        String id,
        Long createdAt,
        Boolean completed,
        String description
) { }

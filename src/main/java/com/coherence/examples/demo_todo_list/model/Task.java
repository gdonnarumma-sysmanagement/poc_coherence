package com.coherence.examples.demo_todo_list.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class Task implements Serializable {
    @Id
    private String id;

    private Long createdAt;

    private Boolean completed;

    private String description;

    public Task(String description) {
        this.id = UUID.randomUUID().toString().substring(0, 12);
        this.createdAt = Instant.now().toEpochMilli();
        this.description = description;
        this.completed = false;
    }
}

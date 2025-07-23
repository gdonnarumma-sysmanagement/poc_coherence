package com.coherence.examples.demo_todo_list.repository;

import com.oracle.coherence.spring.data.config.CoherenceMap;
import com.oracle.coherence.spring.data.repository.CoherenceRepository;
import com.coherence.examples.demo_todo_list.model.Task;

import java.util.Collection;

@CoherenceMap("tasks")
public interface TaskRepository extends CoherenceRepository<Task, String> {
    Collection<Task> findByCompleted(boolean completed);
}

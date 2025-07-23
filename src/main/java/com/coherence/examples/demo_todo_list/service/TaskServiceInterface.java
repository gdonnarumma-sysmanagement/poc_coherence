package com.coherence.examples.demo_todo_list.service;

import com.coherence.examples.demo_todo_list.dto.TaskRequestDto;
import com.coherence.examples.demo_todo_list.dto.TaskResponseDto;
import com.coherence.examples.demo_todo_list.model.Task;

import java.util.Collection;

public interface TaskServiceInterface {
    Collection<TaskResponseDto> findAll(boolean completed);
    TaskResponseDto find(String id);
    void save(TaskRequestDto request);
    void removeById(String id);
    void deleteCompletedTasks();
    Collection<TaskResponseDto> deleteCompletedTasksAndReturnRemainingTasks();
    TaskResponseDto update(String id, TaskRequestDto task);
    void save(Collection<TaskRequestDto> request);
    Collection<TaskResponseDto> completedAllTask();
}

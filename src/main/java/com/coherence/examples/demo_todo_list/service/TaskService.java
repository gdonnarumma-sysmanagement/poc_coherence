package com.coherence.examples.demo_todo_list.service;

import com.coherence.examples.demo_todo_list.dto.TaskRequestDto;
import com.coherence.examples.demo_todo_list.dto.TaskResponseDto;
import com.coherence.examples.demo_todo_list.processor.CompleteTaskProcessor;
import com.tangosol.net.NamedCache;
import com.tangosol.net.NamedMap;
import com.tangosol.net.Session;
import com.tangosol.util.Filter;
import com.tangosol.util.Filters;
import com.coherence.examples.demo_todo_list.exception.TaskNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import com.coherence.examples.demo_todo_list.model.Task;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.coherence.examples.demo_todo_list.repository.TaskRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService implements TaskServiceInterface {
    private final TaskRepository taskRepository;
    private NamedMap<String, Task> taskMap;
    private final Session session; //Gestisce la connessione al cluster Coherence e permette di accedere alle NamedMap

    private static final String TASK_NOT_FOUND = "Il task con id '%s' non esiste";

    @PostConstruct
    public void init() {
        this.taskMap = session.getMap("tasks");
    }

    @Override
    public Collection<TaskResponseDto> findAll(boolean completed) {
        final Filter<Task> filter = !completed
                ? Filters.always()
                : Filters.equal(Task::getCompleted, true);
        return taskRepository.getAllOrderedBy(filter, Task::getCreatedAt)
                .stream()
                .map(task -> new TaskResponseDto(task.getId(), task.getCreatedAt(), task.getCompleted(), task.getDescription()))
                .toList();
    }

    @Override
    public TaskResponseDto find(String id) {
        Assert.hasText(id, "Id non può essere nullo o vuoto");
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format(TASK_NOT_FOUND, id)));
        return new TaskResponseDto(task.getId(), task.getCreatedAt(), task.getCompleted(), task.getDescription());
    }

    @Override
    public void save(TaskRequestDto request) {
        Assert.notNull(request, "The task must not be null.");
        taskRepository.save(new Task(request.description()));
    }

    @Override
    public void removeById(String id) {
        Assert.hasText(id, "Id non può essere nullo o vuoto");
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteCompletedTasks() {
        taskRepository.deleteAll(Filters.equal(Task::getCompleted, true), false);
    }

    @Override
    public Collection<TaskResponseDto> deleteCompletedTasksAndReturnRemainingTasks() {
        this.deleteCompletedTasks();
        return this.findAll(false);
    }

    @Override
    public TaskResponseDto update(String id, TaskRequestDto task) {
        Assert.hasText(id, "Id non può essere nullo o vuoto");
        Assert.notNull(task, "Task non può essere nullo");

        final String description = task.description();
        final Boolean completed = task.completed();

        try {
            Task taskUpdate = this.taskRepository.update(id, tsk -> {
                if (description != null) {
                    tsk.setDescription(description);
                }
                if (completed != null) {
                    tsk.setCompleted(completed);
                }
                return tsk;
            });

            return new TaskResponseDto(taskUpdate.getId(), taskUpdate.getCreatedAt(), taskUpdate.getCompleted(), taskUpdate.getDescription());
        }
        catch (Exception ex) {
            throw new TaskNotFoundException(String.format(TASK_NOT_FOUND, id));
        }
    }

    @Override
    public void save(Collection<TaskRequestDto> request) {
        taskRepository.saveAll(
                request.stream()
                        .map(task -> new Task(task.description()))
                        .toList());
    }

    @Override
    public Collection<TaskResponseDto> completedAllTask() {
        // Filtra per task non completati
        Filter<Task> filter = Filters.equal(Task::getCompleted, false);
        // Applica il processor solo a quelli filtrati
        taskMap.invokeAll(filter, new CompleteTaskProcessor());

        return this.findAll(false);
    }
}

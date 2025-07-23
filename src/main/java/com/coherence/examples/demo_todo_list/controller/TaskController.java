package com.coherence.examples.demo_todo_list.controller;

import com.coherence.examples.demo_todo_list.dto.TaskRequestDto;
import com.coherence.examples.demo_todo_list.dto.TaskResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.coherence.examples.demo_todo_list.service.TaskService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Task API", description = "Gestione delle attività TODO")
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Recupera un task per ID", description = "Restituisce i dettagli di un singolo task dato il suo ID")
    @ApiResponse(responseCode = "200", description = "Task trovato con successo")
    @ApiResponse(responseCode = "404", description = "Task non trovato")
    @GetMapping("/{id}")
    public TaskResponseDto getTask(
            @Parameter(description = "ID del task da recuperare", required = true)
            @PathVariable String id) {
        return taskService.find(id);
    }

    @Operation(summary = "Recupera tutti i task", description = "Restituisce tutti i task, filtrabili per stato completato")
    @ApiResponse(responseCode = "200", description = "Lista di task recuperata con successo")
    @GetMapping
    public Collection<TaskResponseDto> getTasks(
            @Parameter(description = "Filtra per task completati o no", required = false)
            @RequestParam(defaultValue = "false") boolean completed) {
        return taskService.findAll(completed);
    }

    @Operation(summary = "Crea un nuovo task", description = "Crea un task singolo")
    @ApiResponse(responseCode = "200", description = "Task creato con successo")
    @PostMapping
    public void createTask(
            @Parameter(description = "Dati del task da creare", required = true)
            @RequestBody @Valid TaskRequestDto request) {
        taskService.save(request);
    }

    @Operation(summary = "Crea più task in bulk", description = "Crea una lista di task")
    @ApiResponse(responseCode = "200", description = "Task creati con successo")
    @PostMapping("/bulk")
    public void createAllTask(
            @Parameter(description = "Lista di task da creare", required = true)
            @RequestBody @Valid List<TaskRequestDto> request) {
        taskService.save(request);
    }

    @Operation(summary = "Elimina un task per ID", description = "Rimuove un task dato il suo ID")
    @ApiResponse(responseCode = "200", description = "Task eliminato con successo")
    @ApiResponse(responseCode = "404", description = "Task non trovato")
    @DeleteMapping("/{id}")
    public void deleteTask(
            @Parameter(description = "ID del task da eliminare", required = true)
            @PathVariable String id) {
        taskService.removeById(id);
    }

    @Operation(summary = "Elimina tutti i task completati", description = "Rimuove tutti i task che sono stati completati")
    @ApiResponse(responseCode = "200", description = "Task completati eliminati con successo")
    @DeleteMapping
    public void deleteCompletedTasks() {
        taskService.deleteCompletedTasks();
    }

    @Operation(summary = "Aggiorna un task", description = "Aggiorna i dati di un task esistente dato l'ID")
    @ApiResponse(responseCode = "200", description = "Task aggiornato con successo")
    @ApiResponse(responseCode = "404", description = "Task non trovato")
    @PutMapping("/{id}")
    public TaskResponseDto updateTask(
            @Parameter(description = "ID del task da aggiornare", required = true)
            @PathVariable String id,
            @Parameter(description = "Nuovi dati del task", required = true)
            @RequestBody TaskRequestDto task) {
        return taskService.update(id, task);
    }

    @Operation(summary = "Completa tutti i task non completati", description = "Segna come completati tutti i task che non lo sono")
    @ApiResponse(responseCode = "200", description = "Tutti i task non completati sono stati completati")
    @PostMapping("/complete-all")
    public Collection<TaskResponseDto> completedAllTask() {
        return taskService.completedAllTask();
    }
}
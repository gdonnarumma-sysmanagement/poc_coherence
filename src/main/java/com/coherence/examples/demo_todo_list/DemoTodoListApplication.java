package com.coherence.examples.demo_todo_list;

import com.coherence.examples.demo_todo_list.model.Task;
import com.coherence.examples.demo_todo_list.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class DemoTodoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoTodoListApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadInitialData(TaskRepository repository) {
		return args -> {
			if (repository.count() == 0) {
				System.out.println("🚀 Carico 1000 task iniziali...");

				List<Task> tasks = IntStream.range(0, 1000)
						.mapToObj(i -> new Task("Task #" + i))
						.toList();

				repository.saveAll(tasks);

				System.out.println("✅ Task caricati.");
			} else {
				System.out.println("ℹ️ Task già presenti in cache. Skip inizializzazione.");
			}
		};
	}
}

package com.cesar.task_manager_api;

import com.cesar.task_manager_api.entity.Task;
import com.cesar.task_manager_api.enums.Priority;
import com.cesar.task_manager_api.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(TaskRepository taskRepository) {
        return args -> {

            Task task1 = new Task();
            task1.setTitle("Estudar Spring Boot");
            task1.setDescription("Praticar os endpoints REST");
            task1.setPriority(Priority.HIGH);
            task1.setCompleted(false);
            taskRepository.save(task1);

            Task task2 = new Task();
            task2.setTitle("Estudar JPA");
            task2.setDescription("Entender mapeamento de entidades");
            task2.setPriority(Priority.MEDIUM);
            task2.setCompleted(false);
            taskRepository.save(task2);

            Task task3 = new Task();
            task3.setTitle("Revisar DTOs");
            task3.setDescription("Entender a diferença entre Request e Response");
            task3.setPriority(Priority.LOW);
            task3.setCompleted(true);
            taskRepository.save(task3);

            System.out.println(">>> Dados carregados com sucesso!");
        };
    }
}
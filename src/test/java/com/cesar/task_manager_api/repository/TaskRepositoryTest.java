package com.cesar.task_manager_api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cesar.task_manager_api.entity.Task;
import com.cesar.task_manager_api.enums.Priority;

@DataJpaTest // sobe só o JPA com H2 em memória
class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();

        taskRepository.save(new Task(null, "Tarefa A", "Desc", false, Priority.HIGH));
        taskRepository.save(new Task(null, "Tarefa B", "Desc", true,  Priority.LOW));
        taskRepository.save(new Task(null, "Estudar JPA", "Desc", false, Priority.MEDIUM));
    }

    @Test
    void deveRetornarTarefasPorPrioridade() {
        List<Task> result = taskRepository.findByPriority(Priority.HIGH);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Tarefa A");
    }

    @Test
    void deveRetornarTarefasPendentes() {
        List<Task> result = taskRepository.findByCompleted(false);
        assertThat(result).hasSize(2);
    }

    @Test
    void deveBuscarPorTituloIgnorandoCase() {
        List<Task> result = taskRepository.findByTitleContainingIgnoreCase("estudar");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Estudar JPA");
    }
}
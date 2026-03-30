package com.cesar.task_manager_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cesar.task_manager_api.dto.TaskRequestDto;
import com.cesar.task_manager_api.dto.TaskResponseDto;
import com.cesar.task_manager_api.entity.Task;
import com.cesar.task_manager_api.enums.Priority;
import com.cesar.task_manager_api.exception.BusinessException;
import com.cesar.task_manager_api.exception.TaskNotFoundException;
import com.cesar.task_manager_api.repository.TaskRepository;

@ExtendWith(MockitoExtension.class) // diz ao JUnit para usar o Mockito
class TaskServiceTest {

	@Mock
	TaskRepository taskRepository; // dublê — não usa banco de verdade

	@InjectMocks
	TaskService taskService; // injeta o mock acima automaticamente

	private Task task;
	private TaskRequestDto requestDto;

	@BeforeEach // roda antes de cada teste, monta os objetos base
	void setUp() {
		task = new Task(1L, "Estudar Spring", "Praticar REST", false, Priority.HIGH);

		requestDto = new TaskRequestDto();
		requestDto.setTitle("Estudar Spring");
		requestDto.setDescription("Praticar REST");
		requestDto.setPriority(Priority.HIGH);
	}

	@Test
	void deveCriarTarefaComSucesso() {
		// Arrange — configura o que o mock deve fazer
		when(taskRepository.save(any(Task.class))).thenReturn(task);

		// Act — executa o método que está sendo testado
		TaskResponseDto response = taskService.create(requestDto);

		// Assert — verifica o resultado
		assertThat(response.getTitle()).isEqualTo("Estudar Spring");
		assertThat(response.getCompleted()).isFalse();
		verify(taskRepository, times(1)).save(any(Task.class));
	}

	@Test
	void deveLancarExcecaoQuandoTarefaNaoEncontrada() {
		// Arrange
		when(taskRepository.findById(99L)).thenReturn(Optional.empty());

		// Assert + Act — verifica que a exceção é lançada
		assertThatThrownBy(() -> taskService.findById(99L)).isInstanceOf(TaskNotFoundException.class)
				.hasMessageContaining("99");
	}

	@Test
	void deveLancarExcecaoAoCompletarTarefaJaConcluida() {
		// Arrange — task já está completa
		task.setCompleted(true);
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

		// Assert + Act
		assertThatThrownBy(() -> taskService.complete(1L)).isInstanceOf(BusinessException.class)
				.hasMessageContaining("already completed");
	}

	@Test
	void deveCompletarTarefaComSucesso() {
		// Arrange
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		when(taskRepository.save(any(Task.class))).thenReturn(task);

		// Act
		TaskResponseDto response = taskService.complete(1L);

		// Assert
		assertThat(response).isNotNull();
		verify(taskRepository).save(any(Task.class));
	}

	@Test
	void deveLancarExcecaoAoDeletarTarefaInexistente() {
		// Arrange
		when(taskRepository.existsById(99L)).thenReturn(false);

		// Assert + Act
		assertThatThrownBy(() -> taskService.delete(99L)).isInstanceOf(TaskNotFoundException.class);
	}

	@Test
	void deveAtualizarTarefaComSucesso() {
		// Arrange
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		when(taskRepository.save(any(Task.class))).thenReturn(task);

		requestDto.setTitle("Título atualizado");

		// Act
		TaskResponseDto response = taskService.update(1L, requestDto);

		// Assert
		assertThat(response).isNotNull();
		verify(taskRepository).save(any(Task.class));
	}

	@Test
	void deveLancarExcecaoAoAtualizarTarefaInexistente() {
		// Arrange
		when(taskRepository.findById(99L)).thenReturn(Optional.empty());

		// Assert + Act
		assertThatThrownBy(() -> taskService.update(99L, requestDto)).isInstanceOf(TaskNotFoundException.class)
				.hasMessageContaining("99");
	}
}
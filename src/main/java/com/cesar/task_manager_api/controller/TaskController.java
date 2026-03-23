package com.cesar.task_manager_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cesar.task_manager_api.DTO.TaskRequestDTO;
import com.cesar.task_manager_api.DTO.TaskResponseDTO;
import com.cesar.task_manager_api.enums.Priority;
import com.cesar.task_manager_api.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	// POST /tasks
	@PostMapping
	public ResponseEntity<TaskResponseDTO> create(@RequestBody @Valid TaskRequestDTO dto) {
		TaskResponseDTO response = taskService.create(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// GET /tasks
	@GetMapping
	public ResponseEntity<List<TaskResponseDTO>> findAll() {
		return ResponseEntity.ok(taskService.findAll());
	}

	// GET /tasks/{id}
	@GetMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(taskService.findById(id));
	}

	// PUT /tasks/{id}
	@PutMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> update(@PathVariable Long id, @RequestBody @Valid TaskRequestDTO dto) {
		return ResponseEntity.ok(taskService.update(id, dto));
	}

	// PATCH /tasks/{id}/complete
	@PatchMapping("/{id}/complete")
	public ResponseEntity<TaskResponseDTO> complete(@PathVariable Long id) {
		return ResponseEntity.ok(taskService.complete(id));
	}

	// DELETE /tasks/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		taskService.delete(id);
		return ResponseEntity.noContent().build();
	}

	// GET /tasks/priority?level=HIGH
	@GetMapping("/priority")
	public ResponseEntity<List<TaskResponseDTO>> findByPriority(@RequestParam Priority level) {
		return ResponseEntity.ok(taskService.findByPriority(level));
	}

	// GET /tasks/pending
	@GetMapping("/pending")
	public ResponseEntity<List<TaskResponseDTO>> findPending() {
		return ResponseEntity.ok(taskService.findPending());
	}

	// GET /tasks/search?title=study
	@GetMapping("/search")
	public ResponseEntity<List<TaskResponseDTO>> searchByTitle(@RequestParam String title) {
		return ResponseEntity.ok(taskService.searchByTitle(title));
	}

}
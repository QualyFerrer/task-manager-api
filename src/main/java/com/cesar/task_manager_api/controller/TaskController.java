package com.cesar.task_manager_api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.cesar.task_manager_api.dto.TaskRequestDto;
import com.cesar.task_manager_api.dto.TaskResponseDto;
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
	public ResponseEntity<TaskResponseDto> create(@RequestBody @Valid TaskRequestDto dto) {
		TaskResponseDto response = taskService.create(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// GET /tasks
	@GetMapping
	public ResponseEntity<Page<TaskResponseDto>> findAll(Pageable pageable) {
	    return ResponseEntity.ok(taskService.findAll(pageable));
	}
	// GET /tasks/{id}
	@GetMapping("/{id}")
	public ResponseEntity<TaskResponseDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok(taskService.findById(id));
	}

	// PUT /tasks/{id}
	@PutMapping("/{id}")
	public ResponseEntity<TaskResponseDto> update(@PathVariable Long id, @RequestBody @Valid TaskRequestDto dto) {
		return ResponseEntity.ok(taskService.update(id, dto));
	}

	// PATCH /tasks/{id}/complete
	@PatchMapping("/{id}/complete")
	public ResponseEntity<TaskResponseDto> complete(@PathVariable Long id) {
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
	public ResponseEntity<List<TaskResponseDto>> findByPriority(@RequestParam Priority level) {
		return ResponseEntity.ok(taskService.findByPriority(level));
	}

	// GET /tasks/pending
	@GetMapping("/pending")
	public ResponseEntity<List<TaskResponseDto>> findPending() {
		return ResponseEntity.ok(taskService.findPending());
	}

	// GET /tasks/search?title=study
	@GetMapping("/search")
	public ResponseEntity<List<TaskResponseDto>> searchByTitle(@RequestParam String title) {
		return ResponseEntity.ok(taskService.searchByTitle(title));
	}

}
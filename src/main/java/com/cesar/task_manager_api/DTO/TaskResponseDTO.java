package com.cesar.task_manager_api.DTO;

import com.cesar.task_manager_api.entity.Task;
import com.cesar.task_manager_api.enums.Priority;

import lombok.Data;

public class TaskResponseDTO {

	private Long id;
	private String title;
	private String description;
	private Boolean completed;
	private Priority priority;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public static TaskResponseDTO fromEntity(Task task) {
		TaskResponseDTO dto = new TaskResponseDTO();
		dto.setId(task.getId());
		dto.setTitle(task.getTitle());
		dto.setDescription(task.getDescription());
		dto.setCompleted(task.getCompleted());
		dto.setPriority(task.getPriority());
		return dto;
	}
}

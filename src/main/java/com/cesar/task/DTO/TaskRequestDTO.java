package com.cesar.task.DTO;

import com.cesar.task.enums.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TaskRequestDTO {

	@NotBlank(message = "Title is required")
	@Size(max = 100)
	private String title;

	@Size(max = 500)
	private String description;

	@NotNull(message = "Priority is required")
	private Priority priority;

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

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

}

package com.cesar.task_manager_api.entity;

import java.util.Objects;

import com.cesar.task_manager_api.enums.Priority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_tasks")
public class Task {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 100)
	private String title;
	@Column(nullable = false, length = 500)
	private String description;
	@Column(nullable = false)
	private Boolean completed;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private Priority priority;
	
	public Task() {
	}
	
	
	public Task(Long id, String title, String description, Boolean completed, Priority priority) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.completed = completed;
		this.priority = priority;
	}

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


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(id, other.id);
	}


	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", completed=" + completed
				+ ", priority=" + priority + "]";
	}
	
	
	
}

package com.cesar.task_manager_api.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

	private String message;
	private Integer status;
	private LocalDateTime timestamp;
	
	public ErrorResponse() {}
	
	public ErrorResponse(String message, Integer status, LocalDateTime timestamp) {
		this.message = message;
		this.status = status;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	
}

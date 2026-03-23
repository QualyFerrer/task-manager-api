package com.cesar.task_manager_api.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

	private String message;
	private Integer status;
	private List<String> errors;
	private LocalDateTime timestamp;
	
	public ErrorResponse(String message, Integer status, List<String> errors) {
		this.message = message;
		this.status = status;
		this.errors = errors;
		this.timestamp = LocalDateTime.now();
	}
	
	public ErrorResponse(String message, Integer status) {
		this.message = message;
		this.status = status;
		this.timestamp = LocalDateTime.now();
	}

    public String getMessage() { return message; }
    public int getStatus() { return status; }
    public List<String> getErrors() { return errors; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

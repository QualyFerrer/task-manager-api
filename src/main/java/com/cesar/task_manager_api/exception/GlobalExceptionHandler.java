package com.cesar.task_manager_api.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	LocalDateTime localDateTime;

	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleTaskNotFound(TaskNotFoundException ex){
		ErrorResponse error = new ErrorResponse(ex.getMessage(), 404, localDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);	
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex){
		ErrorResponse error = new ErrorResponse(ex.getMessage(), 400, localDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	public ResponseEntity<ErrorResponse> handleGeneric(Exception ex){
		ErrorResponse error = new ErrorResponse("Internal Server Error", 500, localDateTime.now());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}

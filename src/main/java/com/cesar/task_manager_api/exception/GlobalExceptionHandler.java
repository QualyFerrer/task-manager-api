package com.cesar.task_manager_api.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	

	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleTaskNotFound(TaskNotFoundException ex){
		ErrorResponse error = new ErrorResponse(ex.getMessage(), 404);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);	
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex){
		ErrorResponse error = new ErrorResponse(ex.getMessage(), 400);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

	    List<String> errors = ex.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .map(error -> error.getDefaultMessage())
	            .toList();
	    
	    ErrorResponse errorResponse = new ErrorResponse(
	            "Validation error",
	            400,
	            errors
	    );

	    return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleGeneric(Exception ex){
		ErrorResponse error = new ErrorResponse("Internal Server Error", 500);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}

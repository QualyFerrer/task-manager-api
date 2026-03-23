package com.cesar.task_manager_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cesar.task_manager_api.dto.TaskRequestDto;
import com.cesar.task_manager_api.dto.TaskResponseDto;
import com.cesar.task_manager_api.entity.Task;
import com.cesar.task_manager_api.enums.Priority;
import com.cesar.task_manager_api.exception.BusinessException;
import com.cesar.task_manager_api.exception.TaskNotFoundException;
import com.cesar.task_manager_api.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    public TaskResponseDto create (TaskRequestDto dto ) {
    	Task task = new Task();
    	task.setTitle(dto.getTitle());
    	task.setDescription(dto.getDescription());
    	task.setPriority(dto.getPriority());
    	task.setCompleted(false);
    	
    	Task saved = taskRepository.save(task);
    	return TaskResponseDto.fromEntity(saved);
    }
    
    public List<TaskResponseDto> findAll(){
    	return taskRepository.findAll()
    			.stream()
    			.map(TaskResponseDto::fromEntity)
    			.collect(Collectors.toList());
    }
    
    public TaskResponseDto findById(Long id) {
    	Task task = taskRepository.findById(id)
    			.orElseThrow(() -> new TaskNotFoundException(id));
    	  		return TaskResponseDto.fromEntity(task);
    }
    
    public TaskResponseDto update(Long id, TaskRequestDto dto) {
    	Task task = taskRepository.findById(id)
    			.orElseThrow(() -> new TaskNotFoundException(id));
    	
    	task.setTitle(dto.getTitle());
    	task.setDescription(dto.getDescription());
    	task.setPriority(dto.getPriority());
    	
    	Task updated = taskRepository.save(task);
    	return TaskResponseDto.fromEntity(updated);
    }
    
    public TaskResponseDto complete (Long id) {
    	Task task = taskRepository.findById(id)
    			.orElseThrow(()  -> new TaskNotFoundException(id));
    	
    	if(task.getCompleted()) {
    		throw new BusinessException("Task is already completed");
    	}
    	
    	task.setCompleted(true);
    	
    	return TaskResponseDto.fromEntity(taskRepository.save(task));	
    }
    
    public void delete(Long id) {
    	if(!taskRepository.existsById(id)) {
    		throw new  TaskNotFoundException(id);
    	}
    	taskRepository.deleteById(id);
    }
    
    public List<TaskResponseDto> findByPriority(Priority priority){
    	return taskRepository.findByPriority(priority)
    			.stream()
    			.map(TaskResponseDto::fromEntity)
    			.collect(Collectors.toList());
    }
    
    public List<TaskResponseDto> findPending(){
    	return taskRepository.findByCompleted(false)
    			.stream()
    			.map(TaskResponseDto::fromEntity)
    			.collect(Collectors.toList()); 	
    }
    
    public List<TaskResponseDto> searchByTitle(String title){
    	return taskRepository.findByTitleContainingIgnoreCase(title)
    			.stream()
    			.map(TaskResponseDto::fromEntity)
    			.collect(Collectors.toList());
    	}
}
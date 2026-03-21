package com.cesar.task.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cesar.task.DTO.TaskRequestDTO;
import com.cesar.task.DTO.TaskResponseDTO;
import com.cesar.task.entity.Task;
import com.cesar.task.enums.Priority;
import com.cesar.task.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    public TaskResponseDTO create (TaskResponseDTO dto ) {
    	Task task = new Task();
    	task.setTitle(dto.getTitle());
    	task.setDescription(dto.getDescription());
    	task.setPriority(dto.getPriority());
    	task.setCompleted(false);
    	
    	Task saved = taskRepository.save(task);
    	return TaskResponseDTO.fromEntity(saved);
    }
    
    public List<TaskResponseDTO> findAll(){
    	return taskRepository.findAll()
    			.stream()
    			.map(TaskResponseDTO::fromEntity)
    			.collect(Collectors.toList());
    }
    
    public TaskResponseDTO findById(Long id) {
    	Task task = taskRepository.findById(id)
    			.orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    	  		return TaskResponseDTO.fromEntity(task);
    }
    
    public TaskResponseDTO update(Long id, TaskRequestDTO dto) {
    	Task task = taskRepository.findById(id)
    			.orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    	
    	task.setTitle(dto.getTitle());
    	task.setDescription(dto.getDescription());
    	task.setPriority(dto.getPriority());
    	
    	Task updated = taskRepository.save(task);
    	return TaskResponseDTO.fromEntity(updated);
    }
    
    public TaskResponseDTO complete (Long id) {
    	Task task = taskRepository.findById(id)
    			.orElseThrow(()  -> new RuntimeException("Task not found with id: " + id));
    	
    	if(task.getCompleted()) {
    		throw new RuntimeException("Task is already completed");
    	}
    	
    	task.getCompleted();
    	
    	return TaskResponseDTO.fromEntity(taskRepository.save(task));	
    }
    
    public void delete(Long id) {
    	if(!taskRepository.existsById(id)) {
    		throw new RuntimeException("Task not found with id: " + id);
    	}
    	taskRepository.deleteById(id);
    }
    
    public List<TaskResponseDTO> findByPriority(Priority priority){
    	return taskRepository.findByPriority(priority)
    			.stream()
    			.map(TaskResponseDTO::fromEntity)
    			.collect(Collectors.toList());
    }
    
    public List<TaskResponseDTO> findPending(){
    	return taskRepository.findByCompleted(false)
    			.stream()
    			.map(TaskResponseDTO::fromEntity)
    			.collect(Collectors.toList()); 	
    }
    
    public List<TaskResponseDTO> searchByTitle(String title){
    	return taskRepository.findByTitleContainingIgnoreCase(title)
    			.stream()
    			.map(TaskResponseDTO::fromEntity)
    			.collect(Collectors.toList());
    	}
}
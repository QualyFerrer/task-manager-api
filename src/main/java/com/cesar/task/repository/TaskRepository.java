package com.cesar.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cesar.task.entity.Task;
import com.cesar.task.enums.Priority;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

	List<Task> findByPriority(Priority priority);
	
	List<Task> findByCompleted(Boolean completed);
	
	List<Task> findByTitleContainingIgnoreCase(String title);
	
	List<Task> findAllByOrderByPriorityDesc();
}

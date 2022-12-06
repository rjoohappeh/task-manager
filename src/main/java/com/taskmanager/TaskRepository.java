package com.taskmanager;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
	
	@Modifying
	@Query("UPDATE Task t SET t.isComplete = :isComplete WHERE id = :id")
	void updateIsComplete(boolean isComplete, int id);
	
	@Query("SELECT t FROM Task t WHERE lower(t.description) LIKE lower(concat('%', :description, '%'))")
	List<Task> findAllByDescription(String description);
	
	@Query("SELECT t FROM Task t WHERE lower(t.dueDate) LIKE lower(concat('%', :dueDate, '%'))")
	List<Task> findAllByDueDate(String dueDate);
	
	List<Task> findAllByIsComplete(boolean isComplete);
	
	List<Task> findByOrderByDueDateDesc();
	
	List<Task> findByOrderByIsCompleteDesc();
}

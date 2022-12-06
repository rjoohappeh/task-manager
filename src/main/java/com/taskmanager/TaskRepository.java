package com.taskmanager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
	
	@Modifying
	@Query("UPDATE Task t SET t.isComplete = :isComplete WHERE id = :id")
	void updateIsComplete(boolean isComplete, int id);
}

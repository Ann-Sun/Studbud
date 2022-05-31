
  package com.example.project.repository;
  
  import com.example.project.model.Task;
  import org.springframework.data.jpa.repository.JpaRepository;
  
  public interface TaskRep extends JpaRepository<Task, Long> {
  
  }
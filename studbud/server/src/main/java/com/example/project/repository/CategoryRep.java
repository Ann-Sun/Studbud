
  package com.example.project.repository;
  
  import com.example.project.model.Category;
  import org.springframework.data.jpa.repository.JpaRepository;
  
  public interface CategoryRep extends JpaRepository<Category, Long> {
  
  }
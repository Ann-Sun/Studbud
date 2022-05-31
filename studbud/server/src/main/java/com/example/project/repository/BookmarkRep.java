
  package com.example.project.repository;
  
  import com.example.project.model.Bookmark;
  import org.springframework.data.jpa.repository.JpaRepository;
  
  public interface BookmarkRep extends JpaRepository<Bookmark, Long> {
  
  }
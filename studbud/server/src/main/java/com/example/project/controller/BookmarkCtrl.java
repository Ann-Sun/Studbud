package com.example.project.controller;

  import com.example.project.model.Bookmark;
  import com.example.project.service.BookmarkService;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.web.bind.annotation.*;
  
  import java.util.List;
  
  @RestController
  @RequestMapping("/api")
  public class BookmarkCtrl {
      @Autowired
      BookmarkService bookmarkService;
  
      @RequestMapping(value="/bookmark", method=RequestMethod.POST)
      public Bookmark create(@RequestBody Bookmark bookmark) {
          return bookmarkService.create(bookmark);
      }
  
      @RequestMapping(value="/bookmark", method=RequestMethod.GET)
      public List<Bookmark> read() {
          return bookmarkService.read();
      }

      @RequestMapping(value="/bookmark/{bookmarkId}", method=RequestMethod.GET)
      public Bookmark read(@PathVariable(value = "bookmarkId") Long id) {
          return bookmarkService.findOne(id);
      }
  
      @RequestMapping(value="/bookmark/{bookmarkId}", method=RequestMethod.PUT)
      public Bookmark update(@PathVariable(value = "bookmarkId") Long id, @RequestBody Bookmark bookmarkDetails) {
          return bookmarkService.update(id, bookmarkDetails);
      }
  
      @RequestMapping(value="/bookmark/{bookmarkId}", method=RequestMethod.DELETE)
      public void delete(@PathVariable(value = "bookmarkId") Long id) {
          bookmarkService.delete(id);
      }
  }
  
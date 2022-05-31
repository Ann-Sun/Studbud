package com.example.project.controller;

  import com.example.project.model.Category;
  import com.example.project.service.CategoryService;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.web.bind.annotation.*;
  
  import java.util.List;
  
  @RestController
  @RequestMapping("/api")
  public class CategoryCtrl {
      @Autowired
      CategoryService categoryService;
  
      @RequestMapping(value="/category", method=RequestMethod.POST)
      public Category create(@RequestBody Category category) {
          return categoryService.create(category);
      }
  
      @RequestMapping(value="/category", method=RequestMethod.GET)
      public List<Category> read() {
          return categoryService.read();
      }

      @RequestMapping(value="/category/{categoryId}", method=RequestMethod.GET)
      public Category read(@PathVariable(value = "categoryId") Long id) {
          return categoryService.findOne(id);
      }
  
      @RequestMapping(value="/category/{categoryId}", method=RequestMethod.PUT)
      public Category update(@PathVariable(value = "categoryId") Long id, @RequestBody Category categoryDetails) {
          return categoryService.update(id, categoryDetails);
      }
  
      @RequestMapping(value="/category/{categoryId}", method=RequestMethod.DELETE)
      public void delete(@PathVariable(value = "categoryId") Long id) {
          categoryService.delete(id);
      }
  }
  
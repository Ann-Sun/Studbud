package com.example.project.controller;

  import com.example.project.model.Task;
  import com.example.project.service.TaskService;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.web.bind.annotation.*;
  
  import java.util.List;
  
  @RestController
  @RequestMapping("/api")
  public class TaskCtrl {
      @Autowired
      TaskService taskService;
  
      @RequestMapping(value="/task", method=RequestMethod.POST)
      public Task create(@RequestBody Task task) {
          return taskService.create(task);
      }
  
      @RequestMapping(value="/task", method=RequestMethod.GET)
      public List<Task> read() {
          return taskService.read();
      }

      @RequestMapping(value="/task/{taskId}", method=RequestMethod.GET)
      public Task read(@PathVariable(value = "taskId") Long id) {
          return taskService.findOne(id);
      }
  
      @RequestMapping(value="/task/{taskId}", method=RequestMethod.PUT)
      public Task update(@PathVariable(value = "taskId") Long id, @RequestBody Task taskDetails) {
          return taskService.update(id, taskDetails);
      }
  
      @RequestMapping(value="/task/{taskId}", method=RequestMethod.DELETE)
      public void delete(@PathVariable(value = "taskId") Long id) {
          taskService.delete(id);
      }
  }
  
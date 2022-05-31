package com.example.project.service;

    import java.util.List;
    import org.springframework.beans.BeanUtils;
    import org.springframework.beans.BeanUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Example;
    import org.springframework.stereotype.Service;
    
    import com.example.project.model.Task;
    import com.example.project.repository.TaskRep;
    @Service
    public class TaskService {
        @Autowired
        TaskRep taskRep;
    
        //CREATE
        public Task create(Task task) {
            return taskRep.save(task);
        }
    
        //READ
        public List<Task> read() {
            return taskRep.findAll();
        }

        public Task findOne(Long taskId) {
            return taskRep.findById(taskId).get();
        }
    
        //UPDATE
        public Task update(Long taskId, Task taskDetails) {
            Task task = taskRep.findById(taskId).get();
            BeanUtils.copyProperties(taskDetails, task);
            return taskRep.save(task);
        }
    
        //DELETE
        public void delete(Long taskId) {
            taskRep.deleteById(taskId);
        }
    }
    
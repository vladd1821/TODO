package com.davidenko.TODO.controller;

import com.davidenko.TODO.model.DTO.Status;
import com.davidenko.TODO.model.DTO.TaskDTO;
import com.davidenko.TODO.service.TaskService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks(){
        List<TaskDTO> taskDTOList = taskService.getAllTasks();
        return ResponseEntity.ok(taskDTOList);
    }

    @GetMapping("/status")
    public ResponseEntity<List<TaskDTO>> getAllTasksByStatus(@RequestParam Status status){
        List<TaskDTO> taskDTOList = taskService.getAllTasksByStatus(status);
        return ResponseEntity.ok(taskDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id){
        taskService.deleteTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

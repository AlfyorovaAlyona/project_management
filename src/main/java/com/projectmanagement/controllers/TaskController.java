package com.projectmanagement.controllers;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tasks")
public class TaskController {
    public static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private TaskService taskService;

     public TaskController(TaskService taskService) {
         this.taskService = taskService;
     }

     @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<List<TaskDto>> getAllTasks() throws ValidationException {
         return ResponseEntity.ok(taskService.getAll());
     }

     @GetMapping("{taskId}")
     public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId) throws ValidationException {
         return ResponseEntity.ok(taskService.getTask(taskId));
     }

     @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
     public void createTask(@RequestBody TaskDto taskDto) throws ValidationException {
         taskService.create(taskDto);
     }

    @DeleteMapping(value = "delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTask(@RequestBody TaskDto taskDto) throws ValidationException {
        taskService.delete(taskDto.getId());
    }

    @PostMapping (value = "removeTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeTask(@RequestBody TaskDto taskDto) throws ValidationException {
        taskService.removeTaskFromUser(taskDto);
    }
}

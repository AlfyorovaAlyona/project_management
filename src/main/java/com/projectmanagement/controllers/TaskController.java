package com.projectmanagement.controllers;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.services.TaskService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("task")
public class TaskController {

    private TaskService taskService;

     public TaskController(TaskService taskService) {
         this.taskService = taskService;
     }

     @GetMapping("{taskId}")
     public TaskDto getTask(@PathVariable Long taskId) throws ValidationException {
         return taskService.getTask(taskId);
     }

     @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
     public void createTask(TaskDto taskDto) throws ValidationException {
         taskService.create(taskDto);
     }
}

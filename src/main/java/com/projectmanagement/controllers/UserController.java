package com.projectmanagement.controllers;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.dtos.UserDto;
import com.projectmanagement.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws ValidationException {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("email/{userEmail}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String userEmail) throws ValidationException {
        return ResponseEntity.ok(userService.getUserByEmail(userEmail));
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<TaskDto>> getTasksOfUserByUserId(@PathVariable Long userId)
            throws ValidationException {
        return ResponseEntity.ok(userService.getTasksOfUserBYUserId(userId));
    }

    @PostMapping(value = "addTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addTask(@RequestBody TaskDto taskDto) throws ValidationException {
        userService.addTaskToUser(taskDto);
    }

    @DeleteMapping(value = "removeTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeTask(@RequestBody TaskDto taskDto) throws ValidationException {
        userService.removeTaskFromUser(taskDto);
    }
}

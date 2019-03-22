package com.projectmanagement.controllers;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.dtos.UserDto;
import com.projectmanagement.entities.enums.TaskStatus;
import com.projectmanagement.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userId}")
    public UserDto getUser(@PathVariable Long userId) throws ValidationException {
        return userService.getUser(userId);
    }

    @GetMapping("email/{userEmail}")
    public UserDto getUserByEmail(@PathVariable String userEmail) throws ValidationException {
        return userService.getUserByEmail(userEmail);
    }

    @PostMapping(value = "create")
    public void createUser(UserDto userDto) throws ValidationException {
        userService.createUser(userDto);
    }

    @PutMapping(value = "addTask")
    public void addTask(Long userId) throws ValidationException {
        TaskDto taskDto = new TaskDto(2L,"not do", TaskStatus.NOT_STARTED, null,
                BigDecimal.ONE, null, 1L);
        userService.addTaskToUser(taskDto, getUser(userId));

    }
}

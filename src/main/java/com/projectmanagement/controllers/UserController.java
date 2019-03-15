package com.projectmanagement.controllers;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.dtos.UserDto;
import com.projectmanagement.services.UserService;
import org.springframework.web.bind.annotation.*;

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
}

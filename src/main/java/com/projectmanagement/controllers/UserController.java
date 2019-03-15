package com.projectmanagement.controllers;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.dtos.UserDto;
import com.projectmanagement.services.UserService;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public UserDto getUser(@PathVariable Long id) throws ValidationException {
        return userService.getUser(id);
    }

    @GetMapping({"email"})
    public UserDto getUserByEmail(@PathVariable String email) throws ValidationException {
        return userService.getUserByEmail(email);
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody UserDto userDto) throws ValidationException {
        userService.createUser(userDto);
    }
}

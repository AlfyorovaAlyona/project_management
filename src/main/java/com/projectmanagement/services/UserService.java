package com.projectmanagement.services;

import com.projectmanagement.daos.UserDao;
import com.projectmanagement.dtos.UserDto;
import com.projectmanagement.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserDao userDao;

    private UserDto buildUserDtoFromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setSurname(user.getSurname());

        return userDto;
    }

    public UserDto getUser(Long userId) {
        User user = userDao.findOne(userId);

        return buildUserDtoFromUser(user);
    }

    public UserDto getUserByEmail(String userEmail) {
        User user = userDao.findByEmail(userEmail);
        return buildUserDtoFromUser(user);
    }
}

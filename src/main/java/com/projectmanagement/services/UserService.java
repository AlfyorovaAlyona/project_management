package com.projectmanagement.services;

import com.projectmanagement.daos.UserDao;
import com.projectmanagement.dtos.ProjectDto;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.dtos.UserDto;
import com.projectmanagement.entities.Project;
import com.projectmanagement.entities.Task;
import com.projectmanagement.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDto getUser(Long userId) {
        User user = userDao.findOne(userId);

        return buildUserDtoFromUser(user);
    }

    public UserDto getUserByEmail(String userEmail) {
        User user = userDao.findByEmail(userEmail);
        return buildUserDtoFromUser(user);
    }

    private UserDto buildUserDtoFromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setSurname(user.getSurname());
        userDto.setTasks(buildTaskDtoListFromTaskList(user.getTasks()));
        userDto.setProjects(buildProjectDtoListFromProjectList(user.getProjects()));
        return userDto;
    }

    private List<TaskDto> buildTaskDtoListFromTaskList(List<Task> tasks) {
        return tasks.stream().map(task -> new TaskDto(task.getId(), task.getName(), task.getStatus(),
                task.getDescription(), task.getSalary(), task.getDeadline(), task.getProjectId()))
                .collect(Collectors.toList());
    }

    private List<ProjectDto> buildProjectDtoListFromProjectList(List<Project> projects) {
        return projects.stream().map(project -> new ProjectDto(project.getId(), project.getCreatorId(),
                project.getName(), project.getDeadline(), project.getDescription(), project.getStatus(),
                buildTaskDtoListFromTaskList(project.getTasks())))
                .collect(Collectors.toList());
    }
}

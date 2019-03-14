package com.projectmanagement.services;

import com.projectmanagement.common.utils.ValidationException;
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

import static com.projectmanagement.common.utils.ValidationUtils.validateIsNotNull;

@Service
public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDto getUser(Long userId) throws ValidationException {
        /**
         * Creating project with null id is unacceptable
         */
        validateIsNotNull(userId, "userId == NULL!!!");

        User user = userDao.findOne(userId);

        /**If the user with id = userId exists
         * then we build a userDto from this User
         */
        validateIsNotNull(user, "No project with id = " + userId);

        return buildUserDtoFromUser(user);
    }

    public UserDto getUserByEmail(String userEmail) throws ValidationException {
        /**
         * Creating user with null email is unacceptable
         */
        validateIsNotNull(userEmail, "userEmail == NULL!!!");

        User user = userDao.findByEmail(userEmail);

        validateIsNotNull(user, "No user with userEmail" + userEmail);

        return buildUserDtoFromUser(user);
    }

    private UserDto buildUserDtoFromUser(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName(), user.getSurname(),
                buildTaskDtoListFromTaskList(user.getTasks()),
                buildProjectDtoListFromProjectList(user.getProjects()));
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

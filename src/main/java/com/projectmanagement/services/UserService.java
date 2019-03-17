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

import java.util.ArrayList;
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

        //Creating user with null id is unacceptable
        validateIsNotNull(userId, "userId == NULL!!!");

        User user = userDao.findOne(userId);

        /*If the user with id = userId exists
         * then we build a userDto from this User
         */
        validateIsNotNull(user, "No user with id = " + userId);

        return buildUserDtoFromUser(user);
    }

    public UserDto getUserByEmail(String userEmail) throws ValidationException {

         // Creating user with null email is unacceptable

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
                project.getName(), project.getDeadline(), project.getDescription(),
                project.getStatus(), buildTaskDtoListFromTaskList(project.getTasks())))
                .collect(Collectors.toList());
    }

    //todo написать addTask()

    //todo пока не нужно
    /*public User createUser(UserDto userDto) throws ValidationException {
        validateIsNotNull(userDto, "userDto == NULL!!!");

        validateIsNotNull(userDto.getId(), "UserId == NULL!!!");
        validateIsNotNull(userDto.getEmail(), "UserEmail == NULL!!!");
        validateIsNotNull(userDto.getName(), "UserName == NULL!!!");
        validateIsNotNull(userDto.getSurname(), "UserSurname == NULL!!!");

        if (userDto.getTasks() == null) {
            userDto.setTasks(new ArrayList<>());
        }

        User user = buildUserFromUserDto(userDto);
        userDao.save(user);
        return user;
    }

    private User buildUserFromUserDto(UserDto userDto) {
        return new User(userDto.getId(), userDto.getEmail(), userDto.getName(), userDto.getSurname(),
                buildTaskListFromTaskDtoList(userDto.getTasks()),
                buildProjectListFromProjectDtoList(userDto.getProjects()));
    }

    private List<Project> buildProjectListFromProjectDtoList(List<ProjectDto> projectDtos) {
        return projectDtos.stream().map(projectDto -> new Project(projectDto.getId(),
                projectDto.getCreatorId(), projectDto.getName(), projectDto.getDeadline(),
                projectDto.getDescription(), projectDto.getStatus(),
                buildTaskListFromTaskDtoList(projectDto.getTasks()))).collect(Collectors.toList());
    }

    private List<Task> buildTaskListFromTaskDtoList(List<TaskDto> taskDtos) {
        return taskDtos.stream().map(taskDto -> new Task(taskDto.getId(), taskDto.getName(),
                taskDto.getStatus(), taskDto.getDescription(), taskDto.getSalary(),
                taskDto.getDeadline(), taskDto.getProjectId())).collect(Collectors.toList());
    }
*/
}

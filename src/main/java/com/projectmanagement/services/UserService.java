package com.projectmanagement.services;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.daos.UserDao;
import com.projectmanagement.dtos.ProjectDto;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.dtos.UserDto;
import com.projectmanagement.entities.Project;
import com.projectmanagement.entities.Task;
import com.projectmanagement.entities.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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

    private List<UserDto> buildUserDtoListFromUserList(List<User> users) {
        return users.stream().map(user -> new UserDto(user.getId(), user.getEmail(), user.getName(),
                user.getSurname())).collect(Collectors.toList());
    }

    private List<ProjectDto> buildProjectDtoListFromProjectList(List<Project> projects) {
        return projects.stream().map(project -> new ProjectDto(project.getId(), project.getCreatorId(),
                project.getName(), project.getDeadline(), project.getDescription(),
                project.getStatus(), buildTaskDtoListFromTaskList(project.getTasks())))
                .collect(Collectors.toList());
    }

    private void taskAndUserAreValid(TaskDto taskDto, UserDto userDto) throws ValidationException {
        validateIsNotNull(userDto, "userId is NULL!!!");
        validateIsNotNull(userDto.getId(), "userId is NULL!!!");
        validateIsNotNull(taskDto, "Impossible to add null task");
        validateIsNotNull(taskDto.getId(), "Impossible to add null task with null id");
    }

    private void ifTaskOrProjectListIsNull(User user) {
        if (user.getTasks() == null) {
            user.setTasks(new ArrayList<>());
        }

        if (user.getProjects() == null) {
            user.setProjects(new ArrayList<>());
        }
        userDao.save(user);
    }

    public User addTaskToUser(TaskDto taskDto, UserDto userDto) throws ValidationException {
        taskAndUserAreValid(taskDto, userDto);

        User user = userDao.findOne(userDto.getId());
        validateIsNotNull(user, "No user with id");

        ifTaskOrProjectListIsNull(user);

        List<Task> tasks = user.getTasks();
        user.setTasks(buildTaskListWithAddedTask(tasks, taskDto));

        Task newTask = buildTaskFromTaskDto(taskDto);
        if (newTask.getUsers() == null) {
            newTask.setUsers(new ArrayList<>());
        }
        userDao.save(user);
        return user;
    }

    private List<Task> buildTaskListWithAddedTask(List<Task> tasks, TaskDto taskDto) {
        List<Task> list = new ArrayList<>(tasks);
        list.add(buildTaskFromTaskDto(taskDto));
        return list;
    }

    private Task buildTaskFromTaskDto(TaskDto taskDto) {
        return new Task(taskDto.getId(), taskDto.getName(), taskDto.getStatus(), taskDto.getDescription(),
                taskDto.getSalary(), taskDto.getDeadline(), taskDto.getProjectId());
    }

    public User removeTaskFromUser(TaskDto taskDto, UserDto userDto) throws ValidationException {
        taskAndUserAreValid(taskDto, userDto);

        User user = userDao.findOne(userDto.getId());
        validateIsNotNull(user, "No user with id");

        //todo do better
        if (userDto.getTasks() == null) {
            userDto.setTasks(new ArrayList<>());
        }

        if (userDto.getProjects() == null) {
            userDto.setProjects(new ArrayList<>());
        }

        List<TaskDto> taskDtos = userDto.getTasks();

        if (taskDtos.contains(taskDto)) {
            userDto.setTasks(buildTaskListWithRemovedTask(taskDtos, taskDto));
            if (taskDto.getUsers() != null)
                taskDto.getUsers().remove(userDto);
            userDao.save(buildUserFromUserDto(userDto));
        } else {
            throw new ValidationException("removeTaskFromUser: User with id: " + user.getId()
                    + " has not such task");
        }
        return user;
    }

    private List<TaskDto> buildTaskListWithRemovedTask(List<TaskDto> taskDtos, TaskDto taskDto) {
        List<TaskDto> list = new ArrayList<>(taskDtos);
        list.remove(taskDto);
        return list;
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
                taskDto.getDeadline(), taskDto.getProjectId()))
                .collect(Collectors.toList());
    }

    //todo пока не нужно
  /*  public User createUser(UserDto userDto) throws ValidationException {
        validateIsNotNull(userDto, "userDto == NULL!!!");

        validateIsNotNull(userDto.getId(), "UserId == NULL!!!");
        validateIsNotNull(userDto.getEmail(), "UserEmail == NULL!!!");
        validateIsNotNull(userDto.getName(), "UserName == NULL!!!");
        validateIsNotNull(userDto.getSurname(), "UserSurname == NULL!!!");

        if (userDto.getTasks() == null) {
            userDto.setTasks(new ArrayList<>());
        }

        if (userDto.getProjects() == null) {
            userDto.setProjects(new ArrayList<>());
        }

        User user = buildUserFromUserDto(userDto);
        userDao.save(user);
        return user;
    }

    private List<User> buildUserListFromUserDtoList(List<UserDto> userDtos) {
        return userDtos.stream().map(userDto -> new User(userDto.getId(), userDto.getEmail(),
                userDto.getName(), userDto.getSurname())).collect(Collectors.toList());
    }
*/
}

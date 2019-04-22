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
        validateIsNotNull(userId, "getUser: userId == NULL!!!");

        User user = userDao.findOne(userId);
        /*If the user with id = userId exists
         * then we build a userDto from this User
         */
        validateIsNotNull(user, "getUser: No user with id: " + userId);

        return buildUserDtoFromUser(user);
    }

    public UserDto getUserByEmail(String userEmail) throws ValidationException {
        // Creating user with null email is unacceptable
        validateIsNotNull(userEmail, "getUserByEmail: userEmail == NULL!!!");

        User user = userDao.findByEmail(userEmail);
        validateIsNotNull(user, "getUserByEmail: No user with userEmail: " + userEmail);

        return buildUserDtoFromUser(user);
    }

    private UserDto buildUserDtoFromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setTasks(buildTaskDtoListFromTaskList(user.getTasks()));
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setProjects(buildProjectDtoListFromProjectList(user.getProjects()));
        return userDto;
    }

    private List<TaskDto> buildTaskDtoListFromTaskList(List<Task> tasks) {
        return tasks.stream()
                .map(task -> new TaskDto(task.getId(),          task.getName(),   task.getStatus(),
                                         task.getDescription(), task.getSalary(), task.getDeadline(),
                                         task.getProjectId()))
                .collect(Collectors.toList());
    }

    private List<ProjectDto> buildProjectDtoListFromProjectList(List<Project> projects) {
        return projects.stream()
                .map(project -> new ProjectDto(project.getId(),          project.getCreatorId(),
                                               project.getName(),        project.getDeadline(),
                                               project.getDescription(), project.getStatus(),
                                               buildTaskDtoListFromTaskList(project.getTasks())))
                .collect(Collectors.toList());
    }


    public List<User> addTaskToUser(TaskDto taskDto) throws ValidationException {
        taskDtoIsValid(taskDto);

        if (taskDto.getUsers() == null) {
            taskDto.setUsers(new ArrayList<>());
        }

        List<User> users = buildUserListFromUserIdList(taskDto);
        Task newTask = buildTaskFromTaskDto(taskDto);

        for (User user : users) {

            setEmptyTasksOrProjectAreNull(user);

            List<Task> tasks = user.getTasks();
            user.setTasks(buildTaskListWithAddedTask(tasks, newTask));
            taskDto.getUsers().add(buildUserDtoFromUser(user));
            userDao.save(user);
        }
        return users;
    }

    private void taskDtoIsValid(TaskDto taskDto) throws ValidationException {
        validateIsNotNull(taskDto, "Impossible to add null task");
        validateIsNotNull(taskDto.getId(), "Impossible to add null task with null id");
        validateIsNotNull(taskDto.getUserIds(), "No users specified for the task");
    }

    private void setEmptyTasksOrProjectAreNull(User user) {
        if (user.getTasks() == null) {
            user.setTasks(new ArrayList<>());
        }

        if (user.getProjects() == null) {
            user.setProjects(new ArrayList<>());
        }
    }

    private List<User> buildUserListFromUserIdList(TaskDto taskDto) throws ValidationException {
        List<User> users = new ArrayList<>();
        //taskDto.setUserIds(List.of(1L));
        List<Long> userIds = taskDto.getUserIds();
        for (Long userId : userIds) {
            User user = userDao.findOne(userId);
            validateIsNotNull(user, "No user with id: " + userId);
            users.add(user);
        }
        return users;
    }

    private List<Task> buildTaskListWithAddedTask(List<Task> tasks, Task task) {
        List<Task> list = new ArrayList<>(tasks);
        list.add(task);
        return list;
    }

    private Task buildTaskFromTaskDto(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setName(taskDto.getName());
        task.setStatus(taskDto.getStatus());
        task.setDescription(taskDto.getDescription());
        task.setSalary(taskDto.getSalary());
        task.setDeadline(taskDto.getDeadline());
        task.setProjectId(taskDto.getProjectId());
        return task;
    }

    public List<TaskDto> getTasksOfUserByUserId(Long userId) throws ValidationException {
        validateIsNotNull(userId, "getTasksOfUserBYUserId: userId == NULL!!");
        Long userId1 = 1L;

        User user = userDao.findOne(userId1);
        validateIsNotNull(user, "getTasksOfUserBYUserId: No user with id: " + userId);
        validateIsNotNull(user.getTasks(), "getTasksOfUserBYUserId: User with id: "
                + userId + "has no tasks");

        List<Task> tasks = user.getTasks();
        return tasks.stream()
                .map(this::buildTaskDtoFromTask)
                .collect(Collectors.toList());
    }

    private TaskDto buildTaskDtoFromTask(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setStatus(task.getStatus());
        taskDto.setDescription(task.getDescription());
        taskDto.setDeadline(task.getDeadline());
        taskDto.setName(task.getName());
        taskDto.setSalary(task.getSalary());
        taskDto.setProjectId(task.getProjectId());
        return taskDto;
    }
}

package com.projectmanagement.services;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.daos.TaskDao;
import com.projectmanagement.daos.UserDao;
import com.projectmanagement.dtos.ProjectDto;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.dtos.UserDto;
import com.projectmanagement.entities.Project;
import com.projectmanagement.entities.Task;
import com.projectmanagement.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.projectmanagement.common.utils.ValidationUtils.validateIsNotNull;

@Service
public class TaskService {
    private final TaskDao taskDao;
    private final UserDao userDao;

    public TaskService(TaskDao taskDao, UserDao userDao) {
        this.taskDao = taskDao;
        this.userDao = userDao;
    }

    public TaskDto getTask(Long taskId) throws ValidationException {
        // Creating task with null id is unacceptable
        validateIsNotNull(taskId, "taskId == NULL!!!");

        Task task = taskDao.findOne(taskId);

        /*If the task with id = taskId exists
         * then we build a taskDto from this Task
         */
        validateIsNotNull(task, "No task with id = " + taskId);

        //create empty users list
        if (task.getUsers() == null) {
            task.setUsers(new ArrayList<>());
        }

        return buildTaskDtoFromTask(task);
    }

    public List<TaskDto> getAll() throws ValidationException {
        List<Task> tasks = taskDao.findAllBy();
        validateIsNotNull(tasks, "None of tasks was found");

        return tasks.stream()
                .map(this::buildTaskDtoFromTask)
                .collect(Collectors.toList());
    }

    private TaskDto buildTaskDtoFromTask(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setStatus(task.getStatus());
        taskDto.setDescription(task.getDescription());
        taskDto.setDeadline(task.getDeadline());
        taskDto.setSalary(task.getSalary());
        taskDto.setProjectId(task.getProjectId());
        taskDto.setUsers(buildUserDtoListFromUserList(task.getUsers()));
        return taskDto;
    }

    private List<UserDto> buildUserDtoListFromUserList(List<User> users) {
        return users.stream()
                .map(user -> new UserDto(user.getId(),   user.getEmail(),
                                         user.getName(), user.getSurname()))
                .collect(Collectors.toList());
    }

    private void taskDtoIsValid(TaskDto taskDto) throws ValidationException {
        validateIsNotNull(taskDto, "taskDto is NULL!!!");
        //Creating Task with null Id, projectId and name is unacceptable
        validateIsNotNull(taskDto.getId(), "taskDto ID is NULL!!!");
        validateIsNotNull(taskDto.getProjectId(), "projectId of that task is NULL!!!");
        validateIsNotNull(taskDto.getName(), "Name of that task is NULL!!!");
    }


    public Task create(TaskDto taskDto) throws ValidationException {
        taskDtoIsValid(taskDto);

        if (taskDto.getUsers() == null) {
            taskDto.setUsers(new ArrayList<>());
        }

        if (taskDto.getUsers() == null) {
            taskDto.setUsers(new ArrayList<>());
        }

        Task task = buildTaskFromTaskDto(taskDto);
        taskDao.save(task);
        return task;
    }

    private Task buildTaskFromTaskDto(TaskDto taskDto) {
        Task task = new Task();
        task.setStatus(taskDto.getStatus());
        task.setDescription(taskDto.getDescription());
        task.setId(taskDto.getId());
        task.setName(taskDto.getName());
        task.setDeadline(taskDto.getDeadline());
        task.setSalary(taskDto.getSalary());
        task.setProjectId(taskDto.getProjectId());
        task.setUsers(buildUserListFromUserDtoList(taskDto.getUsers()));
        return task;
    }

    private List<User> buildUserListFromUserDtoList(List<UserDto> userDtos) {
        return userDtos.stream()
                .map(userDto -> new User(userDto.getId(),   userDto.getEmail(),
                                         userDto.getName(), userDto.getSurname()))
                .collect(Collectors.toList());
    }

    public void delete(Long taskId) throws ValidationException {
        validateIsNotNull(taskId, "delete: taskId == NULL!!!");
        taskDao.delete(taskId);
    }

    public List<User> removeTaskFromUser(TaskDto taskDto) throws ValidationException {
        taskDtoIsValid(taskDto);
        System.out.println(taskDto);
        validateIsNotNull(taskDto.getUsers(), "No user doing this task!");

        List<UserDto> userDtos = buildUserDtoListFromUserIdList(taskDto);
        List<User> users = new ArrayList<>();

        for (UserDto userDto : userDtos) {
            validateIsNotNull(userDto.getTasks(), "userDto has no task");
            if (userDto.getProjects() == null) {
                userDto.setProjects(new ArrayList<>());
            }

            List<TaskDto> taskDtos = userDto.getTasks();
            System.out.println(taskDtos);
            if (taskDtos.contains(taskDto)) {
                userDto.setTasks(buildTaskListWithRemovedTask(taskDtos, taskDto));
            }
            else {
                throw new ValidationException("userDto has no such taskDto");
            }
            List<UserDto> userDtoList = taskDto.getUsers();
            if (userDtoList.contains(userDto)) {
                taskDto.setUsers(buildUserDtoListWithRemovedUser(userDtoList, userDto));
            } else {
                throw new ValidationException("taskDto has no such userDto");
            }

            User user = buildUserFromUserDto(userDto);
            users.add(user);
            //userDao.save(user);
        }
        userDao.save(users);
        return users;
    }

    private List<UserDto> buildUserDtoListFromUserIdList(TaskDto taskDto) throws ValidationException {
        List<UserDto> userDtos = new ArrayList<>();
        for (Long userId : taskDto.getUserIds()) {
            User user = userDao.findOne(userId);
            UserDto userDto = buildUserDtoFromUser(user);
            validateIsNotNull(user, "No user with id: " + userId);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    private User buildUserFromUserDto(UserDto userDto) {
        return new User(userDto.getId(),   userDto.getEmail(),
                        userDto.getName(), userDto.getSurname(),
                buildTaskListFromTaskDtoList(userDto.getTasks()),
                buildProjectListFromProjectDtoList(userDto.getProjects()));
    }

    private List<Task> buildTaskListFromTaskDtoList(List<TaskDto> taskDtos) {
        return taskDtos.stream()
                .map(taskDto -> new Task(taskDto.getId(),     taskDto.getName(),
                                         taskDto.getStatus(), taskDto.getDescription(),
                                         taskDto.getSalary(), taskDto.getDeadline(),
                                         taskDto.getProjectId()))
                .collect(Collectors.toList());
    }

    private List<Project> buildProjectListFromProjectDtoList(List<ProjectDto> projectDtos) {
        return projectDtos.stream()
                .map(projectDto -> new Project(projectDto.getId(),          projectDto.getCreatorId(),
                                               projectDto.getName(),        projectDto.getDeadline(),
                                               projectDto.getDescription(), projectDto.getStatus(),
                                                buildTaskListFromTaskDtoList(projectDto.getTasks())))
                .collect(Collectors.toList());
    }

    private List<UserDto> buildUserDtoListWithRemovedUser(List<UserDto> userDtos,
                                                            UserDto userDto) {
        List<UserDto> list = new ArrayList<>(userDtos);
        list.remove(userDto);
        return list;
    }

    private List<TaskDto> buildTaskListWithRemovedTask(List<TaskDto> tasks,
                                                        TaskDto removedTask) {
        List<TaskDto> list = new ArrayList<>(tasks);
        list.remove(removedTask);
        return list;
    }

    private UserDto buildUserDtoFromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setTasks(buildTaskDtoListFromTaskList(user.getTasks()));
        userDto.setProjects(buildProjectDtoListFromProjectList(user.getProjects()));
        return userDto;
    }

    private List<TaskDto> buildTaskDtoListFromTaskList(List<Task> tasks) {
        return tasks.stream()
                .map(task -> new TaskDto(task.getId(),          task.getName(),
                                         task.getStatus(),      task.getDescription(),
                                         task.getSalary(),      task.getDeadline(),
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

}
package com.projectmanagement.services;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.daos.TaskDao;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.dtos.UserDto;
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
    private TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
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

}
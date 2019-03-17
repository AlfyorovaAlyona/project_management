package com.projectmanagement.services;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.daos.TaskDao;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        return buildTaskDtoFromTask(task);
    }

    private TaskDto buildTaskDtoFromTask(Task task){
        return new TaskDto(task.getId(), task.getName() , task.getStatus(), task.getDescription(),
                task.getSalary(), task.getDeadline(), task.getProjectId());
    }

    public Task create(TaskDto taskDto) throws ValidationException {
        validateIsNotNull(taskDto, "taskDto is NULL!!!");

        //Creating Task with null Id, projectId and name is unacceptable
        validateIsNotNull(taskDto.getId(), "taskDto ID is NULL!!!");
        validateIsNotNull(taskDto.getProjectId(), "projectId of that task is NULL!!!");
        validateIsNotNull(taskDto.getName(), "Name of that task is NULL!!!");

        Task task = buildTaskFromTaskDto(taskDto);
        taskDao.save(task);
        return task;
    }

    private Task buildTaskFromTaskDto(TaskDto taskDto) {
        return new Task(taskDto.getId(), taskDto.getName(), taskDto.getStatus(), taskDto.getDescription(),
                taskDto.getSalary(), taskDto.getDeadline(), taskDto.getProjectId());
    }

}
package com.projectmanagement.services;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.daos.TaskDao;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.projectmanagement.common.utils.ValidationUtils.validateIsNotNull;

@Service
@RequiredArgsConstructor
public class TaskService {
    private TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public TaskDto get(Long taskId) throws ValidationException {

        validateIsNotNull(taskId, "taskId == NULL!!!");

        Task task = taskDao.findOne(taskId);

        validateIsNotNull(task, "No task with id = " + taskId);

        return buildTaskDtoFromTask(task);
    }

    private TaskDto buildTaskDtoFromTask(Task task){
        return new TaskDto(task.getId(), task.getName() , task.getStatus(), task.getDescription(),
                task.getSalary(), task.getDeadline(), task.getProjectId());
    }

    private Task buildProjectFromProjectDto(TaskDto taskDto) {
        return new Task(taskDto.getId(), taskDto.getName(), taskDto.getStatus(), taskDto.getDescription(),
                taskDto.getSalary(), taskDto.getDeadline(), taskDto.getProjectId());

    }

}
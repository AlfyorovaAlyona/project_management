package com.projectmanagement.services;

import com.projectmanagement.common.utils.*;
import com.projectmanagement.daos.ProjectDao;
import com.projectmanagement.dtos.ProjectDto;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.dtos.UserDto;
import com.projectmanagement.entities.Project;
import com.projectmanagement.entities.Task;
import com.projectmanagement.entities.User;
import org.springframework.stereotype.Service;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.projectmanagement.common.utils.ValidationUtils.validateIsNotNull;

@Service
public class ProjectService {

    private ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public ProjectDto getProject(Long projectId) throws ValidationException {
        // Creating project with null id is unacceptable
        validateIsNotNull(projectId, "projectId == NULL!!!");

        Project project = projectDao.findOne(projectId);

        /*If the project with id = projectId exists
         * then we build a projectDto from this Project
         */
        validateIsNotNull(project, "No project with id = " + projectId);

        return buildProjectDtoFromProject(project);
    }

    private ProjectDto buildProjectDtoFromProject(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setCreatorId(project.getCreatorId());
        projectDto.setDeadline(project.getDeadline());
        project.setDescription(project.getDescription());
        projectDto.setStatus(project.getStatus());
        projectDto.setTasks(buildTaskDtoListFromTaskList(project.getTasks()));
        return projectDto;
    }

    private List<TaskDto> buildTaskDtoListFromTaskList(List<Task> tasks) {
        return tasks.stream()
                .map(task -> new TaskDto(task.getId(),     task.getName(),
                                         task.getStatus(), task.getDescription(),
                                         task.getSalary(), task.getDeadline(),
                                         task.getProjectId()))
                .collect(Collectors.toList());
    }


    public Project create(ProjectDto projectDto) throws ValidationException {
        projectDtoIsValid(projectDto);

        if (projectDto.getTasks() == null) {
            projectDto.setTasks(new ArrayList<>());
        }

        Project project = buildProjectFromProjectDto(projectDto);
        projectDao.save(project);

        return project;
    }

    private void projectDtoIsValid(ProjectDto projectDto) throws ValidationException {
        validateIsNotNull(projectDto, "projectDto is NULL!!!");
        // Creating Project with null id, creatorId and name  is unacceptable
        validateIsNotNull(projectDto.getId(), "projectDto ID is NULL!!!");
        validateIsNotNull(projectDto.getCreatorId(), "CreatorId of that project is NULL!!!");
        validateIsNotNull(projectDto.getName(), "Name of that project is NULL!!!");
    }

    private Project buildProjectFromProjectDto(ProjectDto projectDto) {
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setDeadline(projectDto.getDeadline());
        project.setStatus(projectDto.getStatus());
        project.setCreatorId(projectDto.getCreatorId());
        project.setTasks(buildTaskListFromTaskDtoList(projectDto.getTasks()));
        return project;
    }

    private List<Task> buildTaskListFromTaskDtoList(List<TaskDto> taskDtos) {
        return taskDtos.stream()
                .map(taskDto -> new Task(taskDto.getId(),     taskDto.getName(),
                                         taskDto.getStatus(), taskDto.getDescription(),
                                         taskDto.getSalary(), taskDto.getDeadline(),
                                         taskDto.getProjectId()))
                .collect(Collectors.toList());
    }
}

















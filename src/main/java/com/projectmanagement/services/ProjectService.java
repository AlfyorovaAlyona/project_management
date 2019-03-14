package com.projectmanagement.services;

import com.projectmanagement.common.utils.*;
import com.projectmanagement.daos.ProjectDao;
import com.projectmanagement.dtos.ProjectDto;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.entities.Project;
import com.projectmanagement.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.projectmanagement.common.utils.ValidationUtils.validateIsNotNull;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public ProjectDto get(Long projectId) throws ValidationException {
        /**
         * Creating project with null id is unacceptable
         */
        validateIsNotNull(projectId, "projectId == NULL!!!");

        Project project = projectDao.findOne(projectId);

        /**If the project with id = projectId exists
         * then we build a projectDto from this Project
         */
        validateIsNotNull(project, "No project with id = " + projectId);

        return buildProjectDtoFromProject(project);
    }

    private ProjectDto buildProjectDtoFromProject(Project project) {
        return new ProjectDto(project.getId(), project.getCreatorId(), project.getName(),
                project.getDeadline(), project.getDescription(), project.getStatus(),
                buildTaskDtoListFromTaskList(project.getTasks()));

    }

    private List<TaskDto> buildTaskDtoListFromTaskList(List<Task> tasks) {
        return tasks.stream().map(task -> new TaskDto(task.getId(), task.getName(), task.getStatus(),
                task.getDescription(), task.getSalary(), task.getDeadline(), task.getProjectId()))
                .collect(Collectors.toList());
    }

    public Project create(ProjectDto projectDto) throws ValidationException {
        /**
         * Creating Dto with null id is unacceptable
         */
        validateIsNotNull(projectDto, "projectDto is NULL!!!");
        validateIsNotNull(projectDto.getId(), "projectDto ID is NULL!!!");

        validateIsNotNull(projectDto.getCreatorId(), "No creator of that project!");

        if (projectDto.getTasks() == null) {
            projectDto.setTasks(new ArrayList<>());
        }

        Project project = buildProjectFromProjectDto(projectDto);
        projectDao.save(project); //нужно ли 2 раз сохранять

        return project;
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

    private List<Task> buildTaskListFromTaskDtoList(List<TaskDto> tasksDto) {
        return tasksDto.stream().map(task -> new Task(task.getId(), task.getName(),
                task.getStatus(), task.getDescription(), task.getSalary(), task.getDeadline(),
                task.getProjectId())).collect(Collectors.toList());
    }

}

package com.projectmanagement.services;

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

@Service
@RequiredArgsConstructor
public class ProjectService {

    private ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public ProjectDto get(Long projectId) {
        Project project = projectDao.findOne(projectId);

        return buildProjectDtoFromProject(project);
    }

    private ProjectDto buildProjectDtoFromProject(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setCreatorId(project.getCreatorId());
        projectDto.setDeadline(project.getDeadline());
        projectDto.setDescription(project.getDescription());
        projectDto.setStatus(project.getStatus());
        projectDto.setTasks(buildTaskDtoListFromTaskList(project.getTasks()));
        return projectDto;
    }

    private List<TaskDto> buildTaskDtoListFromTaskList(List<Task> tasks) {
        return tasks.stream().map(task -> new TaskDto(task.getId(), task.getName(), task.getStatus(),
                task.getDescription(), task.getSalary(), task.getDeadline(), task.getProjectId()))
                .collect(Collectors.toList());
    }

    public Project create(ProjectDto projectDto) {
        if (projectDto.getTasks() == null) {
            projectDto.setTasks(new ArrayList<>());
        }

        Project project = buildProjectFromProjectDto(projectDto);
        projectDao.save(project); //нужно ли 2 раз сохранять

        return project;
    }

    private Project buildProjectFromProjectDto(ProjectDto projectDto) {
        Project project = new Project();
        project.setStatus(projectDto.getStatus());
        project.setCreatorId(projectDto.getCreatorId());
        project.setTasks(buildTaskListFromTaskDtoList(projectDto.getTasks()));
        return project;
    }

    private List<Task> buildTaskListFromTaskDtoList(List<TaskDto> tasksDto) {
        return tasksDto.stream().map(task -> new Task(task.getId(), task.getDescription(),
                task.getName(), task.getSalary(), task.getDeadline(), task.getProjectId(),
                task.getStatus())).collect(Collectors.toList());
    }

}

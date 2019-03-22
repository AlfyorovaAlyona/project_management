package com.projectmanagement.controllers;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.dtos.ProjectDto;
import com.projectmanagement.dtos.TaskDto;
import com.projectmanagement.entities.enums.TaskStatus;
import com.projectmanagement.services.ProjectService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RestController
@RequestMapping("project")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("{projectId}")
    public ProjectDto getProject(@PathVariable Long projectId) throws ValidationException {
       return  projectService.getProject(projectId);
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createProject(ProjectDto projectDto) throws ValidationException {
        projectService.create(projectDto);
    }

}

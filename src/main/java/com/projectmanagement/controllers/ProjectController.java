package com.projectmanagement.controllers;

import com.projectmanagement.common.utils.ValidationException;
import com.projectmanagement.dtos.ProjectDto;
import com.projectmanagement.services.ProjectService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("user/{creatorId}")
    public ResponseEntity<List<ProjectDto>> getProjectListByCreatorId(@PathVariable Long creatorId)
                                                                        throws ValidationException {
        return ResponseEntity.ok(projectService.getListByCreatorId(creatorId));
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createProject(@RequestBody ProjectDto projectDto) throws ValidationException {
        projectService.create(projectDto);
    }

}

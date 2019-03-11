package com.myproject.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.myproject.entities.enums.ProjectStatus;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ProjectDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long creatorId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Date deadline;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private ProjectStatus projectStatus;

    @Setter
    @Getter
    private List<TaskDto> taskDto;

    public ProjectDto(Long id,       Long creatorId,     String name,
                      Date deadline, String description, ProjectStatus projectStatus) {
        this.creatorId = creatorId;
        this.deadline = deadline;
        this.description = description;
        this.id = id;
        this.name = name;
        this.projectStatus = projectStatus;
    }

    public ProjectDto(Long creatorId,     String name,  Date deadline,
                      String description, ProjectStatus projectStatus) {
        this.creatorId = creatorId;
        this.deadline = deadline;
        this.description = description;
        this.name = name;
        this.projectStatus = projectStatus;
    }
    @JsonIgnore
    public ProjectStatus getStatus() {
        return this.projectStatus;
    }

    @JsonIgnore
    public void setStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

}

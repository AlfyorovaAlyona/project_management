package com.projectmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projectmanagement.entities.enums.ProjectStatus;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
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
    private short statusCode;

    @Setter
    @Getter
    private List<TaskDto> tasks;

    public ProjectDto(Long id,       Long creatorId,     String name,
                      Date deadline, String description, ProjectStatus projectStatus) {
        this.creatorId = creatorId;
        this.deadline = deadline;
        this.description = description;
        this.id = id;
        this.name = name;
        this.statusCode = projectStatus.getValue();
    }

    public ProjectDto(Long creatorId,     String name,  Date deadline,
                      String description, ProjectStatus projectStatus) {
        this.creatorId = creatorId;
        this.deadline = deadline;
        this.description = description;
        this.name = name;
        this.statusCode = projectStatus.getValue();
    }

    @JsonIgnore
    public ProjectStatus getStatus() {
        return ProjectStatus.parse(this.statusCode);
    }

    @JsonIgnore
    public void setStatus(ProjectStatus projectStatus) {
        this.statusCode = projectStatus.getValue();
    }

}

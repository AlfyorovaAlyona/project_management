package com.projectmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@EqualsAndHashCode (exclude = {"projects", "tasks"})
@ToString
public class UserDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String surname;

    @Getter
    @Setter
    private List<TaskDto> tasks;

    @Getter
    @Setter
    private List<ProjectDto> projects; //предложить

    public UserDto(Long id, String email, String name, String surname,
                    List<TaskDto> tasks, List<ProjectDto> projects) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.projects = projects;
        this.tasks = tasks;
    }

    public UserDto(Long id, String email, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

}

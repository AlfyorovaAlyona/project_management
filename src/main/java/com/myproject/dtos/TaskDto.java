package com.myproject.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.myproject.entities.Enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class TaskDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private double salary;

    @Getter
    @Setter
    private Date deadline;

    @Getter
    @Setter
    private Long projectId;

    @Getter
    @Setter
    private TaskStatus statusTask;

    public TaskDto(TaskStatus taskStatus, Long id, String description, double salary, Date deadline, Long projectId){
        this.deadline = deadline;
        this.description = description;
        this.id = id;
        this.projectId = projectId;
        this.salary = salary;
        this.statusTask = taskStatus;
    }

    public TaskDto(TaskStatus taskStatus, String description, Date deadline, Long projectId){
        this.deadline = deadline;
        this.description = description;
        this.projectId = projectId;
        this.statusTask = taskStatus;
    }

    @JsonIgnore
    public TaskStatus getStatus() {
        return this.statusTask;
    }

    @JsonIgnore
    public void setStatus(TaskStatus taskStatus) {
        this.statusTask = taskStatus;
    }

    //public TaskDto(Long id, )

}

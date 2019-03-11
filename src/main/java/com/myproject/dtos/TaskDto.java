package com.myproject.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.myproject.entities.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
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
    private BigDecimal salary;

    @Getter
    @Setter
    private Date deadline;

    @Getter
    @Setter
    private Long projectId;

    @Getter
    @Setter
    private TaskStatus taskStatus;

    public TaskDto(Long id,   TaskStatus taskStatus, String description,
                   BigDecimal salary, Date deadline, Long projectId) {
        this.id = id;
        this.deadline = deadline;
        this.description = description;
        this.projectId = projectId;
        this.salary = salary;
        this.taskStatus = taskStatus;
    }

    public TaskDto(Long projectId,     TaskStatus taskStatus,
                   String description, Date deadline) {
        this.projectId = projectId;
        this.taskStatus = taskStatus;
        this.deadline = deadline;
        this.description = description;
    }

    @JsonIgnore
    public TaskStatus getStatus() {
        return this.taskStatus;
    }

    @JsonIgnore
    public void setStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    //public TaskDto(Long id, )

}

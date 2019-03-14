package com.projectmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projectmanagement.entities.enums.TaskStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
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
    private Calendar deadline;

    @Getter
    @Setter
    private Long projectId;

    @Getter
    @Setter
    private short statusCode;

    @Getter
    @Setter
    private String name;


    //private List<UserDto> users; //предложить

    public TaskDto(Long id, String name,  TaskStatus taskStatus, String description,
                   BigDecimal salary, Calendar deadline, Long projectId) {
        this.id = id;
        this.deadline = deadline;
        this.name = name;
        this.description = description;
        this.projectId = projectId;
        this.salary = salary;
        this.statusCode = taskStatus.getValue();
    }

    public TaskDto(Long id, String name,  TaskStatus taskStatus, String description,
                   BigDecimal salary, Calendar deadline) {
        this.id = id;
        this.statusCode = taskStatus.getValue();
        this.deadline = deadline;
        this.name = name;
        this.salary = salary;
        this.description = description;
    }

    @JsonIgnore
    public TaskStatus getStatus() {
        return TaskStatus.parse(this.statusCode);
    }

    @JsonIgnore
    public void setStatus(TaskStatus taskStatus) {
        this.statusCode = taskStatus.getValue();
    }
}

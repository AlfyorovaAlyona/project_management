package com.projectmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projectmanagement.entities.User;
import com.projectmanagement.entities.enums.TaskStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode (exclude = {"users", "userIds"})
@NoArgsConstructor
@ToString (exclude = {"users", "userIds"})
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
    private short statusCode;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private List<UserDto> users;

    //list long userId;
    @Getter
    @Setter
    private List<Long> userIds;

    public TaskDto(Long id, String name,  TaskStatus taskStatus, String description,
                   BigDecimal salary, Date deadline, Long projectId) {
        this.id = id;
        this.deadline = deadline;
        this.name = name;
        this.description = description;
        this.projectId = projectId;
        this.salary = salary;
        this.statusCode = taskStatus.getValue();
    }

    public TaskDto(Long id, String name, TaskStatus taskStatus, String description,
                   BigDecimal salary, Date deadline, Long projectId, List<UserDto> users) {
        this.id = id;
        this.statusCode = taskStatus.getValue();
        this.deadline = deadline;
        this.name = name;
        this.salary = salary;
        this.description = description;
        this.projectId = projectId;
        this.users = users;
    }

    public TaskDto(Long id, List<Long> userIds, String name, TaskStatus taskStatus, String description,
                   BigDecimal salary, Date deadline, Long projectId) {
        this.id = id;
        this.statusCode = taskStatus.getValue();
        this.deadline = deadline;
        this.name = name;
        this.salary = salary;
        this.description = description;
        this.projectId = projectId;
        this.userIds = userIds;
    }

    public TaskDto(Long id, List<Long> userIds, String name, TaskStatus taskStatus, String description,
                   BigDecimal salary, Date deadline, Long projectId, List<UserDto> users) {
        this.id = id;
        this.statusCode = taskStatus.getValue();
        this.deadline = deadline;
        this.name = name;
        this.salary = salary;
        this.description = description;
        this.projectId = projectId;
        this.userIds = userIds;
        this.users = users;
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

package com.projectmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.projectmanagement.common.serialization.LocalDateTimeDeserializer;
import com.projectmanagement.common.serialization.ZonedDateTimeDeserializer;
import com.projectmanagement.entities.User;
import com.projectmanagement.entities.enums.TaskStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode (exclude = {"users", "userIds", "deadline"})
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

    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @JsonSerialize(using = ToStringSerializer.class)
    @Getter
    @Setter
    private ZonedDateTime deadline;

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
                   BigDecimal salary, ZonedDateTime deadline, Long projectId) {
        this.id = id;
        this.deadline = deadline;
        this.name = name;
        this.description = description;
        this.projectId = projectId;
        this.salary = salary;
        this.statusCode = taskStatus.getValue();
    }

    public TaskDto(Long id, String name, TaskStatus taskStatus, String description,
                   BigDecimal salary, ZonedDateTime deadline, Long projectId, List<UserDto> users) {
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
                   BigDecimal salary, ZonedDateTime deadline, Long projectId) {
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
                   BigDecimal salary, ZonedDateTime deadline, Long projectId, List<UserDto> users) {
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

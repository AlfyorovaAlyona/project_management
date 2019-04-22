package com.projectmanagement.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import com.projectmanagement.entities.enums.TaskStatus;
import lombok.*;

@Entity
@EqualsAndHashCode(exclude = {"users", "project"})
@NoArgsConstructor
@Table (name = "tasks")
@ToString (exclude = {"project", "users"})
public class Task {

    @Id
    @Column(name = "id")
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq_gen")
    @SequenceGenerator(name = "task_seq_gen", sequenceName = "task_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Column(name = "name")
    @NotNull
    @Getter
    @Setter
    private String name;

    @Column(name = "salary")
    @Getter
    @Setter
    private BigDecimal salary;

    @Column(name = "deadline")
    @Getter
    @Setter
    private ZonedDateTime deadline;

    @Column(name = "project_id")
    @NotNull
    @Getter
    @Setter
    private Long projectId;

    @Column(name = "status_code")
    private short statusCode;

    public void setStatus(TaskStatus taskStatus) {
        this.statusCode = taskStatus.getValue();
    }

    public TaskStatus getStatus() {
        return TaskStatus.parse(this.statusCode);
    }

    public Task(Long id, String name,  TaskStatus taskStatus, String description,
                BigDecimal salary, ZonedDateTime deadline, Long projectId) {
        this.id = id;
        this.description = description;
        this.deadline = deadline;
        this.projectId = projectId;
        this.name = name;
        this.salary = salary;
        this.statusCode = taskStatus.getValue();
    }

    public Task(Long id, String name,  TaskStatus taskStatus, String description,
                BigDecimal salary, ZonedDateTime deadline, Long projectId, List<User> users) {
        this.id = id;
        this.description = description;
        this.deadline = deadline;
        this.projectId = projectId;
        this.name = name;
        this.salary = salary;
        this.statusCode = taskStatus.getValue();
        this.users = users;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    // JoinColumn indicates that this entity is the owner of the relationship
    // (that is: the corresponding table has a column with a foreign key to the
    // referenced table), whereas the attribute mappedBy indicates that the
    // entity in this side is the inverse of the relationship
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    @Getter
    @Setter
    private Project project;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //?
    @JoinTable(name = "tasks_users",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @Getter
    @Setter
    private List<User> users;

}
















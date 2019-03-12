package com.projectmanagement.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

import com.projectmanagement.entities.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
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
    private Date deadline;

    @Column(name = "project_id")
    @NotNull
    @Getter
    @Setter
    private Long projectId;

    @Column(name = "status_code")
    private short statusCode;

    public void setStatus(TaskStatus projectStatus) {
        this.statusCode = projectStatus.getValue();
    }

    public TaskStatus getStatus() {
        return TaskStatus.parse(this.statusCode);
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
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "task_id")})
    @Getter
    @Setter
    private List<User> users;

}
















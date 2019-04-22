package com.projectmanagement.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import com.projectmanagement.entities.enums.ProjectStatus;
import lombok.*;

@Entity
@EqualsAndHashCode (exclude = {"tasks"}) //id?
@NoArgsConstructor
@Table (name = "projects")
@ToString (exclude = {"creator", "tasks"})
public class Project {
    @Id
    @Column(name = "id")
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq_gen")
    @SequenceGenerator(name = "project_seq_gen", sequenceName = "project_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "creator_id")
    @NotNull
    @Getter
    @Setter
    private Long creatorId;

    @Column(name = "name")
    @NotNull
    @Getter
    @Setter
    private String name;

    @Column(name = "deadline")
    @Getter
    @Setter
    private ZonedDateTime deadline;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Column(name = "status_code")
    private short statusCode;

    public void setStatus(ProjectStatus projectStatus) {
        this.statusCode = projectStatus.getValue();
    }

    public ProjectStatus getStatus() {
        return ProjectStatus.parse(this.statusCode);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project") //lazy
    @Getter
    @Setter
    private List<Task> tasks;

    public Project(Long id, Long creatorId, String name, ZonedDateTime deadline, String description,
                   ProjectStatus projectStatus, List<Task> tasks) {
        this.id = id;
        this.creatorId = creatorId;
        this.name = name;
        this.deadline = deadline;
        this.description = description;
        this.statusCode = projectStatus.getValue();
        this.tasks = tasks;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    // JoinColumn indicates that this entity is the owner of the relationship
    // (that is: the corresponding table has a column with a foreign key to the
    // referenced table), whereas the attribute mappedBy indicates that the
    // entity in this side is the inverse of the relationship
    @JoinColumn(name = "creator_id", insertable = false, updatable = false)
    private User creator;
}

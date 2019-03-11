package com.myproject.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table (name = "projects")
@ToString (exclude = "users")
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
    @Getter
    @Setter
    private String name;

    @Column(name = "deadline")
    @Getter
    @Setter
    private Date deadline;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project") //lazy
    private List<Task> tasks;

    @ManyToOne(fetch = FetchType.LAZY)
    // JoinColumn indicates that this entity is the owner of the relationship
    // (that is: the corresponding table has a column with a foreign key to the
    // referenced table), whereas the attribute mappedBy indicates that the
    // entity in this side is the inverse of the relationship
    @JoinColumn(name = "creator_id", insertable = false, updatable = false)
    private User creator;
}

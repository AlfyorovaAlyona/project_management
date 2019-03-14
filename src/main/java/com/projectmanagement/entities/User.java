package com.projectmanagement.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@EqualsAndHashCode
@Table (name = "users")
@ToString(exclude = {"tasks", "projects"})
public class User {

    @Id
    @Column(name = "id")
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_id_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "email")
    @NotNull
    @Getter
    @Setter
    private String email;

    @Column(name = "name")
    @NotNull
    @Getter
    @Setter
    private String name;

    @Column(name = "surname")
    @NotNull
    @Getter
    @Setter
    private String surname;

    @Column(name = "password_hash")
    @Getter
    @Setter
    private String passwordHash;

    @Column(name = "password_salt")
    @Getter
    @Setter
    private String passwordSalt;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //?
    @JoinTable(name = "tasks_users",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @Getter
    @Setter
    private List<Task> tasks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    @Getter
    @Setter
    private List<Project> projects;

    public User(Long id, String email, String name, String surname,
                List<Task> tasks, List<Project> projects) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.tasks = tasks;
        this.projects = projects;
    }

}

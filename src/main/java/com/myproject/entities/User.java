package com.myproject.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@ToString
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

}

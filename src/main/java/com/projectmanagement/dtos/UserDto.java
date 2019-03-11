package com.projectmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String surname;

    @Getter
    @Setter
    private String passwordHash;

    @Getter
    @Setter
    private String passwordSalt;

    @Getter
    @Setter
    private List<TaskDto> taskDto;

    public UserDto(Long id, String email, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}

package com.myproject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.tools.javac.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
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


}

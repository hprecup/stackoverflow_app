package com.stackoverflow.project.Stackoverflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {

    @NotNull(message = "first name must not be null")
    @NotBlank(message = "first name must not be blank")
    private String firstName;

    @NotNull(message = "last name must not be null")
    @NotBlank(message = "last name must not be blank")
    private String lastName;

    @NotNull(message = "username must not be null")
    @NotBlank(message = "username must not be blank")
    private String username;

    @NotNull(message = "email must not be null")
    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be a well-formed address")
    private String email;

    @NotNull(message = "password must not be null")
    @NotBlank(message = "password must not be blank")
    private String password;

    @NotNull(message = "score must not be null")
    private Long score;
}

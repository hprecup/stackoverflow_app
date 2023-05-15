package com.stackoverflow.project.Stackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private Float score;

    private List<String> roleNames;

//    private Boolean canBeBannedByLoggedUser;
}

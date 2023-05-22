package com.stackoverflow.project.Stackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String username;

    private List<String> userRoles;

    private Boolean banned;

    private String accessToken;
}

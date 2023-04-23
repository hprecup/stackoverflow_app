package com.stackoverflow.project.Stackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String username;

    private String accessToken;
}

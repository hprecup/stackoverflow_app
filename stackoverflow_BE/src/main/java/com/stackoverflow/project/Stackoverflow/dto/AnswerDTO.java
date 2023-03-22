package com.stackoverflow.project.Stackoverflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerDTO {

    @NotNull(message = "text must not be null")
    @NotBlank(message = "text must not be blank")
    private String text;

    @NotNull(message = "picture must not be null")
    @NotBlank(message = "picture must not be blank")
    private String picture;

    @NotNull(message = "user must not be null")
    private Long userId;

    @NotNull(message = "question must not be null")
    private Long questionId;
}

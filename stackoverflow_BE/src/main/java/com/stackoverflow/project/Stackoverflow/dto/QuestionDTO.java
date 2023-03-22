package com.stackoverflow.project.Stackoverflow.dto;

import com.stackoverflow.project.Stackoverflow.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class QuestionDTO {

    @NotNull(message = "title must not be null")
    @NotBlank(message = "title must not be blank")
    private String title;

    @NotNull(message = "text must not be null")
    @NotBlank(message = "text must not be blank")
    private String text;

    @NotNull(message = "picture must not be null")
    @NotBlank(message = "picture must not be blank")
    private String picture;

    @NotNull(message = "user must not be null")
    private Long userId;
}

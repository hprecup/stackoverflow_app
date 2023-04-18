package com.stackoverflow.project.Stackoverflow.dto;

import com.stackoverflow.project.Stackoverflow.model.Answer;
import com.stackoverflow.project.Stackoverflow.model.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestQuestionDTO {
    private Long id;
    private String text;
    private String picture;
    private LocalDateTime creationDateTime;
    private String title;
    //private List<Answer> answers;
    //private User user;
    private List<String> tagNames;

    private RequestUserDTO user;

    private Long voteCount;
}

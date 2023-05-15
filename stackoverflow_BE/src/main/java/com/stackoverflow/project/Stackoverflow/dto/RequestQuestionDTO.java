package com.stackoverflow.project.Stackoverflow.dto;

import com.stackoverflow.project.Stackoverflow.model.Answer;
import com.stackoverflow.project.Stackoverflow.model.User;
import com.stackoverflow.project.Stackoverflow.model.VoteType;
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
    private List<String> tagNames;

    private RequestUserDTO user;

    private Long voteCount;

    private Boolean canBeModified; // by the logged user

    private Boolean canBeVoted;

    private VoteType voteType;
}

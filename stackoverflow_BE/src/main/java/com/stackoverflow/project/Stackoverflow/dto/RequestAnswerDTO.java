package com.stackoverflow.project.Stackoverflow.dto;

import com.stackoverflow.project.Stackoverflow.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAnswerDTO {
    private Long id;

    private String text;

    private RequestUserDTO user;

    private String picture;

    private LocalDateTime creationDateTime;

    private Long voteCount;

    private Boolean canBeModified;

    private Boolean canBeVoted;

    private VoteType voteType;

}

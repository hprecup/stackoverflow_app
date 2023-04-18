package com.stackoverflow.project.Stackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertQuestionDTO {
    private Long authorId;
    private String title;
    private String text;
    private String picture;
    private List<Long> tagIds;

}

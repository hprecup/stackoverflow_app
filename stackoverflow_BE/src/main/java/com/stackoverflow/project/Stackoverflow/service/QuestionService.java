package com.stackoverflow.project.Stackoverflow.service;

import com.stackoverflow.project.Stackoverflow.dto.QuestionDTO;
import com.stackoverflow.project.Stackoverflow.model.Question;
import com.stackoverflow.project.Stackoverflow.model.UserContent;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService{

    ResponseEntity<Question> getQuestion(Long id);

    List<Question> getAllQuestions();

    ResponseEntity<Question> createQuestion(QuestionDTO newQuestion);

    ResponseEntity<Question> updateQuestion(Long id, QuestionDTO updatedQuestion);

    ResponseEntity<Question> deleteQuestion(Long id);
}

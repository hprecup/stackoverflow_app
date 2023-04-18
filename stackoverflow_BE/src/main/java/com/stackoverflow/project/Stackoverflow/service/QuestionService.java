package com.stackoverflow.project.Stackoverflow.service;

import com.stackoverflow.project.Stackoverflow.dto.InsertQuestionDTO;
import com.stackoverflow.project.Stackoverflow.dto.QuestionDTO;
import com.stackoverflow.project.Stackoverflow.dto.RequestAnswerDTO;
import com.stackoverflow.project.Stackoverflow.dto.RequestQuestionDTO;
import com.stackoverflow.project.Stackoverflow.model.Question;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService{

    ResponseEntity<Question> getQuestion(Long id);

    List<Question> getQuestions();

    ResponseEntity<Question> createQuestion(QuestionDTO newQuestion);

    ResponseEntity<Question> updateQuestion(Long id, QuestionDTO updatedQuestion);

    ResponseEntity<Question> deleteQuestion(Long id);

    List<RequestQuestionDTO> getRequestQuestions();

    RequestQuestionDTO getRequestQuestion(Long id);

    RequestQuestionDTO insertQuestion(InsertQuestionDTO insertQuestionDTO);

    RequestQuestionDTO editQuestion(Long id, String editedQuestionText);

    RequestQuestionDTO deleteRequestQuestion(Long id);

    List<RequestQuestionDTO> searchQuestions(String searchString);

    List<RequestAnswerDTO> getQuestionAnswers(Long id);
}

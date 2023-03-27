package com.stackoverflow.project.Stackoverflow.service.impl;

import com.stackoverflow.project.Stackoverflow.dto.QuestionDTO;
import com.stackoverflow.project.Stackoverflow.mapper.QuestionMapper;
import com.stackoverflow.project.Stackoverflow.model.Question;
import com.stackoverflow.project.Stackoverflow.model.User;
import com.stackoverflow.project.Stackoverflow.model.UserContent;
import com.stackoverflow.project.Stackoverflow.repository.QuestionRepository;
import com.stackoverflow.project.Stackoverflow.repository.UserRepository;
import com.stackoverflow.project.Stackoverflow.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private QuestionMapper questionMapper;

    public ResponseEntity<Question> getQuestion(Long id){
        Optional<Question> retrievedQuestion = questionRepository.findById(id);
        if(!retrievedQuestion.isEmpty()){
            return new ResponseEntity(retrievedQuestion.get(), HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public ResponseEntity<Question> createQuestion(QuestionDTO questionDTO) {
        Question newQuestion = new Question();
        questionMapper.questionDTOtoQuestion(newQuestion, questionDTO);
        Optional<User> givenUser = userRepository.findById(questionDTO.getUserId());
        if(!givenUser.isEmpty()){
            newQuestion.setUser(givenUser.get());
            newQuestion.setCreationDateTime(LocalDateTime.now());
            questionRepository.save(newQuestion);
            return new ResponseEntity(newQuestion, HttpStatus.CREATED);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Question> updateQuestion(Long id, QuestionDTO updatedQuestion) {
        Optional<Question> questionToUpdate = questionRepository.findById(id);
        if(!questionToUpdate.isEmpty()){
            Question retrievedQuestion = questionToUpdate.get();
            questionMapper.questionDTOtoQuestion(retrievedQuestion, updatedQuestion);
            questionRepository.save(retrievedQuestion);
            return new ResponseEntity(retrievedQuestion, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Question> deleteQuestion(Long id) {
        Optional<Question> retrievedQuestion = questionRepository.findById(id);
        if(!retrievedQuestion.isEmpty()) {
            questionRepository.deleteById(id);
            return new ResponseEntity(retrievedQuestion.get(), HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
}

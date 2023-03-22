package com.stackoverflow.project.Stackoverflow.service.impl;

import com.stackoverflow.project.Stackoverflow.dto.AnswerDTO;
import com.stackoverflow.project.Stackoverflow.mapper.AnswerMapper;
import com.stackoverflow.project.Stackoverflow.model.Answer;
import com.stackoverflow.project.Stackoverflow.model.Question;
import com.stackoverflow.project.Stackoverflow.model.User;
import com.stackoverflow.project.Stackoverflow.repository.AnswerRepository;
import com.stackoverflow.project.Stackoverflow.repository.QuestionRepository;
import com.stackoverflow.project.Stackoverflow.repository.UserRepository;
import com.stackoverflow.project.Stackoverflow.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private AnswerMapper answerMapper;

    public List<Answer> getAnswers() {
        return answerRepository.findAll();
    }

    public ResponseEntity<Answer> createAnswer(AnswerDTO answerDTO) {
        Answer newAnswer = new Answer();
        Optional<Question> existingQuestion = questionRepository.findById(answerDTO.getQuestionId());
        if(!existingQuestion.isEmpty()){
            Optional<User> existingUser = userRepository.findById(answerDTO.getUserId());
            if(!existingUser.isEmpty()){
                answerMapper.answerDTOtoAnswer(newAnswer, answerDTO);
                newAnswer.setCreationDateTime(LocalDateTime.now());
                newAnswer.setQuestion(existingQuestion.get());
                newAnswer.setUser(existingUser.get());
                answerRepository.save(newAnswer);
                return new ResponseEntity(newAnswer, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Answer> updateAnswer(Long id, AnswerDTO updatedAnswer) {
        Optional<Answer> answerToUpdate = answerRepository.findById(id);
        if(!answerToUpdate.isEmpty()){
            Answer retrievedAnswer = answerToUpdate.get();
            answerMapper.answerDTOtoAnswer(retrievedAnswer, updatedAnswer);
            answerRepository.save(retrievedAnswer);
            return new ResponseEntity(retrievedAnswer, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> deleteAnswer(Long id) {
        answerRepository.deleteById(id);
        return new ResponseEntity("The answer with id: "+ id +"has been deleted if existed", HttpStatus.OK);
    }
}

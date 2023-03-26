package com.stackoverflow.project.Stackoverflow.controller;

import com.stackoverflow.project.Stackoverflow.dto.QuestionDTO;
import com.stackoverflow.project.Stackoverflow.model.Question;
import com.stackoverflow.project.Stackoverflow.model.User;
import com.stackoverflow.project.Stackoverflow.model.UserContent;
import com.stackoverflow.project.Stackoverflow.service.QuestionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private QuestionService questionService;

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable Long id){
        return questionService.getQuestion(id);
    }

    @GetMapping
    public List<Question> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody QuestionDTO newQuestion){
        return questionService.createQuestion(newQuestion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id,
                                                   @Valid @RequestBody QuestionDTO updatedQuestion){
        return questionService.updateQuestion(id, updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable Long id){
        return questionService.deleteQuestion(id);
    }
}

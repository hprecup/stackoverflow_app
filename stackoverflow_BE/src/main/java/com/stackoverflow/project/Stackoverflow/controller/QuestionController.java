package com.stackoverflow.project.Stackoverflow.controller;

import com.stackoverflow.project.Stackoverflow.dto.InsertQuestionDTO;
import com.stackoverflow.project.Stackoverflow.dto.QuestionDTO;
import com.stackoverflow.project.Stackoverflow.dto.RequestAnswerDTO;
import com.stackoverflow.project.Stackoverflow.dto.RequestQuestionDTO;
import com.stackoverflow.project.Stackoverflow.model.Question;
import com.stackoverflow.project.Stackoverflow.service.QuestionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/question")
public class QuestionController {
    private QuestionService questionService;

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable Long id) {
        return questionService.getQuestion(id);
    }

    @GetMapping("/get/{id}")
    public RequestQuestionDTO getRequestQuestion(@PathVariable Long id) {
        return questionService.getRequestQuestion(id);
    }

    @GetMapping("/getAll")
    public List<RequestQuestionDTO> getRequestQuestions() {
        return questionService.getRequestQuestions();
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getQuestions();
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody QuestionDTO newQuestion) {
        return questionService.createQuestion(newQuestion);
    }

    @PostMapping("/insert")
    public RequestQuestionDTO insertQuestion(@RequestBody InsertQuestionDTO insertQuestionDTO) {
        return questionService.insertQuestion(insertQuestionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id,
                                                   @Valid @RequestBody QuestionDTO updatedQuestion) {
        return questionService.updateQuestion(id, updatedQuestion);
    }

    @PutMapping("/edit/{id}")
    public RequestQuestionDTO editQuestion(@PathVariable Long id, @RequestBody String editedQuestionText) {
        return questionService.editQuestion(id, editedQuestionText);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable Long id) {
        return questionService.deleteQuestion(id);
    }

    @DeleteMapping("/delete/{id}")
    public RequestQuestionDTO deleteRequestQuestion(@PathVariable Long id) {
        return questionService.deleteRequestQuestion(id);
    }

    @GetMapping("/search/{searchString}")
    public List<RequestQuestionDTO> getSearchedQuestion(@PathVariable String searchString) {
        return questionService.searchQuestions(searchString);
    }

    @GetMapping("/answers/{id}")
    public List<RequestAnswerDTO> getQuestionAnswers(@PathVariable Long id) {
        return questionService.getQuestionAnswers(id);
    }
}

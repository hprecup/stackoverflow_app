package com.stackoverflow.project.Stackoverflow.controller;

import com.stackoverflow.project.Stackoverflow.dto.AnswerDTO;
import com.stackoverflow.project.Stackoverflow.model.Answer;
import com.stackoverflow.project.Stackoverflow.service.AnswerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private AnswerService answerService;

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswer(@PathVariable Long id){
        return answerService.getAnswer(id);
    }

    @GetMapping
    public List<Answer> getAnswers(){
        return answerService.getAnswers();
    }

    @PostMapping
    public ResponseEntity<Answer> createAnswer(@Valid @RequestBody AnswerDTO newAnswer){
        return answerService.createAnswer(newAnswer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable Long id, @Valid @RequestBody AnswerDTO updatedAnswer){
        return answerService.updateAnswer(id, updatedAnswer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Answer> deleteAnswer(@PathVariable Long id){
        return answerService.deleteAnswer(id);
    }
}

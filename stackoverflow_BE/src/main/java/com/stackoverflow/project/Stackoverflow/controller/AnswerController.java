package com.stackoverflow.project.Stackoverflow.controller;

import com.stackoverflow.project.Stackoverflow.dto.AnswerDTO;
import com.stackoverflow.project.Stackoverflow.dto.InsertAnswerDTO;
import com.stackoverflow.project.Stackoverflow.dto.RequestAnswerDTO;
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
@CrossOrigin(origins = "http://localhost:4200")
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RequestAnswerDTO> deleteAnswer(@PathVariable Long id){
        return answerService.deleteAnswer(id);
    }

    @PostMapping("/insert/{questionId}")
    public RequestAnswerDTO insertAnswer(@PathVariable Long questionId, @RequestBody InsertAnswerDTO insertAnswerDTO) {
        return answerService.insertAnswer(questionId, insertAnswerDTO);
    }

    @PutMapping("/edit/{id}")
    public RequestAnswerDTO editAnswer(@PathVariable Long id, @RequestBody String newText) {
        return answerService.editAnswer(id, newText);
    }
}

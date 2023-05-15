package com.stackoverflow.project.Stackoverflow.controller;

import com.stackoverflow.project.Stackoverflow.dto.RequestAnswerDTO;
import com.stackoverflow.project.Stackoverflow.dto.RequestQuestionDTO;
import com.stackoverflow.project.Stackoverflow.model.VoteType;
import com.stackoverflow.project.Stackoverflow.repository.AnswerVoteRepository;
import com.stackoverflow.project.Stackoverflow.repository.QuestionVoteRepository;
import com.stackoverflow.project.Stackoverflow.service.VotesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/vote")
public class VotesController {
    private VotesService votesService;


    @GetMapping("/question/upvote/{questionId}")
    public RequestQuestionDTO upvoteQuestion(@PathVariable Long questionId) {
        return votesService.voteQuestion(questionId, VoteType.UPVOTE);
    }

    @GetMapping("/question/downvote/{questionId}")
    public RequestQuestionDTO downvoteQuestion(@PathVariable Long questionId) {
        return votesService.voteQuestion(questionId, VoteType.DOWNVOTE);
    }

    @GetMapping("/answer/upvote/{answerId}")
    public RequestAnswerDTO upvoteAnswer(@PathVariable Long answerId) {
        return votesService.voteAnswer(answerId, VoteType.UPVOTE);
    }

    @GetMapping("/answer/downvote/{answerId}")
    public RequestAnswerDTO downvoteAnswer(@PathVariable Long answerId) {
        return votesService.voteAnswer(answerId, VoteType.DOWNVOTE);
    }
}

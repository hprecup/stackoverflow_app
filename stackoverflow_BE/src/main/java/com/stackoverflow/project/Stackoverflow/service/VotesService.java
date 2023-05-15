package com.stackoverflow.project.Stackoverflow.service;

import com.stackoverflow.project.Stackoverflow.dto.RequestAnswerDTO;
import com.stackoverflow.project.Stackoverflow.dto.RequestQuestionDTO;
import com.stackoverflow.project.Stackoverflow.model.VoteType;

public interface VotesService {
    RequestQuestionDTO voteQuestion(Long questionId, VoteType voteType);

    RequestAnswerDTO voteAnswer(Long answerId, VoteType voteType);
}

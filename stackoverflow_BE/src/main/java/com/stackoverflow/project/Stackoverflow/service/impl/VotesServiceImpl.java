package com.stackoverflow.project.Stackoverflow.service.impl;

import com.stackoverflow.project.Stackoverflow.dto.RequestAnswerDTO;
import com.stackoverflow.project.Stackoverflow.dto.RequestQuestionDTO;
import com.stackoverflow.project.Stackoverflow.mapper.AnswerMapper;
import com.stackoverflow.project.Stackoverflow.mapper.QuestionMapper;
import com.stackoverflow.project.Stackoverflow.model.*;
import com.stackoverflow.project.Stackoverflow.repository.*;
import com.stackoverflow.project.Stackoverflow.security.SecurityUtils;
import com.stackoverflow.project.Stackoverflow.service.VotesService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VotesServiceImpl implements VotesService {
    private QuestionVoteRepository questionVoteRepository;
    private AnswerVoteRepository answerVoteRepository;

    private QuestionRepository questionRepository;

    private AnswerRepository answerRepository;

    private UserRepository userRepository;

    private QuestionMapper questionMapper;

    private AnswerMapper answerMapper;

    public RequestQuestionDTO voteQuestion(Long questionId, VoteType voteType) {
        Question retrievedQuestion = questionRepository.findById(questionId).get();
        User loggedUser = SecurityUtils.getLoggedUser();
        RequestQuestionDTO requestQuestion = new RequestQuestionDTO();
        questionVoteRepository.findQuestionVoteByLoggedUser(loggedUser, retrievedQuestion).ifPresentOrElse(
                questionVote -> {
                    // question is UPVOTED in DB
                    if (questionVote.getVoteType() == VoteType.UPVOTE) {
                        // user presses like button
                        if (voteType == VoteType.UPVOTE) {
                            setUserScore(retrievedQuestion.getUser(), -VotePoints.QUESTION_UPVOTE);
                            setQuestionVoteCount(retrievedQuestion, -1);
                            questionVoteRepository.delete(questionVote);
                        } else {
                            // user presses dislike button
                            setUserScore(retrievedQuestion.getUser(), -VotePoints.QUESTION_UPVOTE + VotePoints.QUESTION_DOWNVOTE);
                            setQuestionVoteCount(retrievedQuestion, -2);
                            questionVote.setVoteType(VoteType.DOWNVOTE);
                            questionVoteRepository.save(questionVote);
                            requestQuestion.setVoteType(VoteType.DOWNVOTE);
                        }
                    } else {
                        // question is DOWNVOTED in DB
                        if (voteType == VoteType.UPVOTE) {
                            // user presses like button
                            setUserScore(retrievedQuestion.getUser(), VotePoints.QUESTION_UPVOTE - VotePoints.QUESTION_DOWNVOTE);
                            setQuestionVoteCount(retrievedQuestion, 2);
                            questionVote.setVoteType(VoteType.UPVOTE);
                            questionVoteRepository.save(questionVote);
                            requestQuestion.setVoteType(VoteType.UPVOTE);
                        } else {
                            // user presses dislike button
                            setUserScore(retrievedQuestion.getUser(), -VotePoints.QUESTION_DOWNVOTE);
                            setQuestionVoteCount(retrievedQuestion, 1);
                            questionVoteRepository.delete(questionVote);
                        }
                    }
                },
                () -> {
                    QuestionVote questionVote = new QuestionVote();
                    questionVote.setUser(loggedUser);
                    questionVote.setQuestion(retrievedQuestion);
                    if (voteType == VoteType.UPVOTE) {
                        questionVote.setVoteType(VoteType.UPVOTE);
                        setUserScore(retrievedQuestion.getUser(), VotePoints.QUESTION_UPVOTE);
                        setQuestionVoteCount(retrievedQuestion, 1);
                        requestQuestion.setVoteType(VoteType.UPVOTE);
                    } else {
                        questionVote.setVoteType(VoteType.DOWNVOTE);
                        setUserScore(retrievedQuestion.getUser(), VotePoints.QUESTION_DOWNVOTE);
                        setQuestionVoteCount(retrievedQuestion, -1);
                        requestQuestion.setVoteType(VoteType.DOWNVOTE);
                    }
                    questionVoteRepository.save(questionVote);
                }
        );
        questionMapper.questionToRequestQuestionDTO(requestQuestion, retrievedQuestion);
        requestQuestion.setCanBeVoted(true);
        requestQuestion.setCanBeModified(false);
        return requestQuestion;
    }

    public RequestAnswerDTO voteAnswer(Long answerId, VoteType voteType) {
        Answer retrievedAnswer = answerRepository.findById(answerId).get();
        User loggedUser = SecurityUtils.getLoggedUser();
        RequestAnswerDTO requestAnswer = new RequestAnswerDTO();
        answerVoteRepository.findAnswerVoteByLoggedUser(loggedUser, retrievedAnswer).ifPresentOrElse(
                answerVote -> {
                    if (answerVote.getVoteType() == VoteType.UPVOTE) {
                        if(voteType == VoteType.UPVOTE) {
                            setUserScore(retrievedAnswer.getUser(), -VotePoints.ANSWER_UPVOTE);
                            setAnswerVoteCount(retrievedAnswer, -1);
                            answerVoteRepository.delete(answerVote);
                        } else {
                            setUserScore(retrievedAnswer.getUser(), VotePoints.ANSWER_DOWNVOTE - VotePoints.ANSWER_UPVOTE);
                            setUserScore(loggedUser, VotePoints.ANSWER_DOWNVOTE_USER);
                            setAnswerVoteCount(retrievedAnswer, -2);
                            answerVote.setVoteType(VoteType.DOWNVOTE);
                            answerVoteRepository.save(answerVote);
                            requestAnswer.setVoteType(VoteType.DOWNVOTE);
                        }
                    } else {
                        if(voteType == VoteType.UPVOTE) {
                            setUserScore(retrievedAnswer.getUser(), VotePoints.ANSWER_UPVOTE - VotePoints.ANSWER_DOWNVOTE);
                            setUserScore(loggedUser, -VotePoints.ANSWER_DOWNVOTE_USER);
                            setAnswerVoteCount(retrievedAnswer, 2);
                            answerVote.setVoteType(VoteType.UPVOTE);
                            answerVoteRepository.save(answerVote);
                            requestAnswer.setVoteType(VoteType.UPVOTE);
                        } else {
                            setUserScore(retrievedAnswer.getUser(), -VotePoints.ANSWER_DOWNVOTE);
                            setUserScore(loggedUser, VotePoints.ANSWER_DOWNVOTE_USER);
                            setAnswerVoteCount(retrievedAnswer, 1);
                            answerVoteRepository.delete(answerVote);
                        }
                    }
                },
                () -> {
                    AnswerVote answerVote = new AnswerVote();
                    answerVote.setUser(loggedUser);
                    answerVote.setAnswer(retrievedAnswer);
                    if(voteType == VoteType.UPVOTE) {
                        answerVote.setVoteType(VoteType.UPVOTE);
                        setUserScore(retrievedAnswer.getUser(), VotePoints.ANSWER_UPVOTE);
                        setAnswerVoteCount(retrievedAnswer, 1);
                        requestAnswer.setVoteType(VoteType.UPVOTE);
                    } else {
                        answerVote.setVoteType(VoteType.DOWNVOTE);
                        setUserScore(retrievedAnswer.getUser(), VotePoints.ANSWER_DOWNVOTE);
                        setUserScore(loggedUser, VotePoints.ANSWER_DOWNVOTE_USER);
                        setAnswerVoteCount(retrievedAnswer, -1);
                        requestAnswer.setVoteType(VoteType.DOWNVOTE);
                    }
                    answerVoteRepository.save(answerVote);
                }
        );
        answerMapper.answerToRequestAnswerDTO(requestAnswer, retrievedAnswer);
        requestAnswer.setCanBeVoted(true);
        requestAnswer.setCanBeModified(false);
        return requestAnswer;
    }

    private void setQuestionVoteCount(Question retrievedQuestion, Integer constant) {
        retrievedQuestion.setVoteCount(retrievedQuestion.getVoteCount() + constant);
        questionRepository.save(retrievedQuestion);
    }

    private void setAnswerVoteCount(Answer retrievedAnswer, Integer constant) {
        retrievedAnswer.setVoteCount(retrievedAnswer.getVoteCount() + constant);
        answerRepository.save(retrievedAnswer);
    }

    private void setUserScore(User user, Float constant) {
        user.setScore(user.getScore() + constant);
        userRepository.save(user);
    }
}

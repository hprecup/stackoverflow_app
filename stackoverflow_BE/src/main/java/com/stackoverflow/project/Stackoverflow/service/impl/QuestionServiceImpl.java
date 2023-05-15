package com.stackoverflow.project.Stackoverflow.service.impl;

import com.stackoverflow.project.Stackoverflow.dto.*;
import com.stackoverflow.project.Stackoverflow.mapper.AnswerMapper;
import com.stackoverflow.project.Stackoverflow.mapper.QuestionMapper;
import com.stackoverflow.project.Stackoverflow.model.*;
import com.stackoverflow.project.Stackoverflow.repository.*;
import com.stackoverflow.project.Stackoverflow.security.SecurityUtils;
import com.stackoverflow.project.Stackoverflow.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;

    private TagRepository tagRepository;

    private QuestionVoteRepository questionVoteRepository;

    private AnswerVoteRepository answerVoteRepository;
    private QuestionMapper questionMapper;

    private AnswerMapper answerMapper;

    public ResponseEntity<Question> getQuestion(Long id) {
        Optional<Question> retrievedQuestion = questionRepository.findById(id);
        if (!retrievedQuestion.isEmpty()) {
            return new ResponseEntity(retrievedQuestion.get(), HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

    public ResponseEntity<Question> createQuestion(QuestionDTO questionDTO) {
        Question newQuestion = new Question();
        questionMapper.questionDTOtoQuestion(newQuestion, questionDTO);
        Optional<User> givenUser = userRepository.findById(questionDTO.getUserId());
        if (!givenUser.isEmpty()) {
            newQuestion.setUser(givenUser.get());
            newQuestion.setCreationDateTime(LocalDateTime.now());
            questionRepository.save(newQuestion);
            return new ResponseEntity(newQuestion, HttpStatus.CREATED);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Question> updateQuestion(Long id, QuestionDTO updatedQuestion) {
        Optional<Question> questionToUpdate = questionRepository.findById(id);
        if (!questionToUpdate.isEmpty()) {
            Question retrievedQuestion = questionToUpdate.get();
            questionMapper.questionDTOtoQuestion(retrievedQuestion, updatedQuestion);
            questionRepository.save(retrievedQuestion);
            return new ResponseEntity(retrievedQuestion, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Question> deleteQuestion(Long id) {
        Optional<Question> retrievedQuestion = questionRepository.findById(id);
        if (!retrievedQuestion.isEmpty()) {
            questionRepository.deleteById(id);
            return new ResponseEntity(retrievedQuestion.get(), HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    public RequestQuestionDTO deleteRequestQuestion(Long id) {
        Optional<Question> retrievedQuestion = questionRepository.findById(id);
        if(!retrievedQuestion.isEmpty()) {
            RequestQuestionDTO requestQuestionDTO = new RequestQuestionDTO();
            questionMapper.questionToRequestQuestionDTO(requestQuestionDTO, retrievedQuestion.get());
            questionRepository.deleteById(id);
        }
        return null;
    }

    public List<RequestQuestionDTO> searchQuestions(String searchString) {
        List<Question> questions = questionRepository.findAllSortedByDate();
        List<RequestQuestionDTO> searchedQuestions = new ArrayList<>();
        String lowerCaseString= searchString.toLowerCase();
        for(Question question : questions) {
            if(question.getTitle().toLowerCase().contains(lowerCaseString)) {
                RequestQuestionDTO requestQuestionDTO = new RequestQuestionDTO();
                questionMapper.questionToRequestQuestionDTO(requestQuestionDTO, question);
                searchedQuestions.add(requestQuestionDTO);
            }
        }
        return searchedQuestions;
    }

    public List<RequestAnswerDTO> getQuestionAnswers(Long id) {
        User loggedUser = SecurityUtils.getLoggedUser();
        Optional<Question> question = questionRepository.findById(id);
        if(!question.isEmpty()) {
            Question retrievedQuestion = question.get();
            List<RequestAnswerDTO> requestAnswers = new ArrayList<>();
            for(Answer answer : retrievedQuestion.getAnswers()) {
                RequestAnswerDTO requestAnswerDTO = new RequestAnswerDTO();
                answerMapper.answerToRequestAnswerDTO(requestAnswerDTO, answer);
                requestAnswerDTO.setCanBeModified(loggedUser.getUsername().equals(answer.getUser().getUsername()) ||
                        SecurityUtils.isUserAdmin(loggedUser));
                requestAnswerDTO.setCanBeVoted(!loggedUser.getId().equals(answer.getUser().getId()));
                answerVoteRepository.findAnswerVoteByLoggedUser(loggedUser, answer).ifPresent(
                        answerVote -> requestAnswerDTO.setVoteType(answerVote.getVoteType())
                );
                requestAnswers.add(requestAnswerDTO);
            }
            Comparator<RequestAnswerDTO> compareByVoteCount = Comparator.comparing(RequestAnswerDTO::getVoteCount).reversed();
            Collections.sort(requestAnswers, compareByVoteCount);
            return requestAnswers;
        }
        return null;
    }

    public List<RequestQuestionDTO> getRequestQuestions() {
        List<Question> questions = questionRepository.findAllSortedByDate();
        List<RequestQuestionDTO> requestQuestions = new ArrayList<>();
        for (Question question : questions) {
            RequestQuestionDTO requestQuestion = new RequestQuestionDTO();
            questionMapper.questionToRequestQuestionDTO(requestQuestion, question);
            requestQuestion.setTagNames(getTagNames(question));
            requestQuestions.add(requestQuestion);
        }
        return requestQuestions;
    }

    public RequestQuestionDTO getRequestQuestion(Long id) {
        User loggedUser = SecurityUtils.getLoggedUser();
        Optional<Question> question = questionRepository.findById(id);
        if (!question.isEmpty()) {
            Question retrievedQuestion = question.get();
            RequestQuestionDTO requestQuestion = new RequestQuestionDTO();
            questionMapper.questionToRequestQuestionDTO(requestQuestion, retrievedQuestion);
            requestQuestion.setTagNames(getTagNames(retrievedQuestion));
            requestQuestion.setCanBeModified(loggedUser.getId().equals(retrievedQuestion.getUser().getId()) ||
                    SecurityUtils.isUserAdmin(loggedUser));
            requestQuestion.setCanBeVoted(!loggedUser.getId().equals(retrievedQuestion.getUser().getId()));
            questionVoteRepository.findQuestionVoteByLoggedUser(loggedUser, retrievedQuestion).ifPresent(
                    questionVote ->  requestQuestion.setVoteType(questionVote.getVoteType()));
            return requestQuestion;
        }
        return null;
    }

    public RequestQuestionDTO insertQuestion(InsertQuestionDTO insertQuestionDTO) {
        User loggedUser = SecurityUtils.getLoggedUser();
        Question question = new Question();
        questionMapper.insertQuestionDTOtoQuestion(question, insertQuestionDTO);
        question.setVoteCount(Long.valueOf(0));
        question.setUser(loggedUser);
        List<String> tagNames = new ArrayList<>();
        for(Long tagId : insertQuestionDTO.getTagIds()){
            Tag retrievedTag = tagRepository.findById(tagId).get();
            QuestionTag questionTag = new QuestionTag(question, retrievedTag);
            question.getTags().add(questionTag);
            tagNames.add(retrievedTag.getTagName());
        }
        question.setCreationDateTime(LocalDateTime.now());
        questionRepository.save(question);
        RequestQuestionDTO requestQuestionDTO = new RequestQuestionDTO();
        questionMapper.questionToRequestQuestionDTO(requestQuestionDTO, question);
        requestQuestionDTO.setTagNames(tagNames);
        requestQuestionDTO.setCanBeVoted(false);
        requestQuestionDTO.setCanBeModified(true);
        return requestQuestionDTO;
    }

    public RequestQuestionDTO editQuestion(Long id, String editedQuestionText) {
        User loggedUser = SecurityUtils.getLoggedUser();
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if(! optionalQuestion.isEmpty()) {
            Question question = optionalQuestion.get();
            question.setText(editedQuestionText);
            questionRepository.save(question);
            RequestQuestionDTO requestQuestionDTO = new RequestQuestionDTO();
            questionMapper.questionToRequestQuestionDTO(requestQuestionDTO, question);
            requestQuestionDTO.setCanBeModified(loggedUser.getId() == question.getUser().getId());
            requestQuestionDTO.setCanBeVoted(!requestQuestionDTO.getCanBeModified());
            return requestQuestionDTO;
        }
        return null;
    }

    private List<String> getTagNames(Question question) {
        List<String> tagNames = new ArrayList<>();
        for (QuestionTag questionTag : question.getTags()) {
            tagNames.add(questionTag.getTag().getTagName());
        }
        return tagNames;
    }
}

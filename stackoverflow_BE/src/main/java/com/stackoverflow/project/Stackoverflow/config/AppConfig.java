package com.stackoverflow.project.Stackoverflow.config;

import com.stackoverflow.project.Stackoverflow.mapper.AnswerMapper;
import com.stackoverflow.project.Stackoverflow.mapper.QuestionMapper;
import com.stackoverflow.project.Stackoverflow.mapper.TagMapper;
import com.stackoverflow.project.Stackoverflow.mapper.UserMapper;
import com.stackoverflow.project.Stackoverflow.repository.*;
import com.stackoverflow.project.Stackoverflow.service.*;
import com.stackoverflow.project.Stackoverflow.service.impl.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class AppConfig {

    @Bean
    public UserService userService(UserRepository userRepository, UserMapper userMapper, MailService mailService) {
        return new UserServiceImpl(userRepository, userMapper, mailService);
    }

    @Bean
    public QuestionService questionService(QuestionRepository questionRepository, UserRepository userRepository, TagRepository tagRepository, QuestionVoteRepository questionVoteRepository,
                                           AnswerVoteRepository answerVoteRepository, QuestionMapper questionMapper, AnswerMapper answerMapper) {
        return new QuestionServiceImpl(questionRepository, userRepository, tagRepository, questionVoteRepository, answerVoteRepository, questionMapper, answerMapper);
    }

    @Bean
    public AnswerService answerService(AnswerRepository answerRepository, QuestionRepository questionRepository,
                                       UserRepository userRepository, AnswerMapper answerMapper) {
        return new AnswerServiceImpl(answerRepository, questionRepository, userRepository, answerMapper);
    }

    @Bean
    public TagService tagService(TagRepository tagRepository, TagMapper tagMapper) {
        return new TagServiceImpl(tagRepository, tagMapper);
    }

    @Bean
    public VotesService votesService(QuestionVoteRepository questionVoteRepository, AnswerVoteRepository answerVoteRepository, QuestionRepository questionRepository,
                                     AnswerRepository answerRepository, UserRepository userRepository, QuestionMapper questionMapper, AnswerMapper answerMapper) {
        return new VotesServiceImpl(questionVoteRepository, answerVoteRepository, questionRepository, answerRepository, userRepository, questionMapper, answerMapper);
    }

    @Bean
    public MailService mailService(JavaMailSender mailSender){
        return new MailServiceImpl(mailSender);
    }
}

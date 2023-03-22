package com.stackoverflow.project.Stackoverflow.config;

import com.stackoverflow.project.Stackoverflow.mapper.AnswerMapper;
import com.stackoverflow.project.Stackoverflow.mapper.QuestionMapper;
import com.stackoverflow.project.Stackoverflow.mapper.UserMapper;
import com.stackoverflow.project.Stackoverflow.repository.AnswerRepository;
import com.stackoverflow.project.Stackoverflow.repository.QuestionRepository;
import com.stackoverflow.project.Stackoverflow.service.AnswerService;
import com.stackoverflow.project.Stackoverflow.service.QuestionService;
import com.stackoverflow.project.Stackoverflow.service.impl.AnswerServiceImpl;
import com.stackoverflow.project.Stackoverflow.service.impl.QuestionServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.stackoverflow.project.Stackoverflow.repository.UserRepository;
import com.stackoverflow.project.Stackoverflow.service.UserService;
import com.stackoverflow.project.Stackoverflow.service.impl.UserServiceImpl;

@Configuration
public class AppConfig {

    @Bean
    public UserService userService(UserRepository userRepository, UserMapper userMapper){
        return new UserServiceImpl(userRepository, userMapper);
    }

    @Bean
    public QuestionService questionService(QuestionRepository questionRepository, UserRepository userRepository, QuestionMapper questionMapper){
        return new QuestionServiceImpl(questionRepository, userRepository, questionMapper);
    }

    @Bean
    public AnswerService answerService(AnswerRepository answerRepository, QuestionRepository questionRepository,
                                       UserRepository userRepository, AnswerMapper answerMapper){
        return new AnswerServiceImpl(answerRepository, questionRepository, userRepository, answerMapper);
    }
}

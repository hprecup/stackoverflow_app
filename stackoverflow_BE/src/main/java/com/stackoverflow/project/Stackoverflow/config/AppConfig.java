package com.stackoverflow.project.Stackoverflow.config;

import com.stackoverflow.project.Stackoverflow.mapper.AnswerMapper;
import com.stackoverflow.project.Stackoverflow.mapper.QuestionMapper;
import com.stackoverflow.project.Stackoverflow.mapper.TagMapper;
import com.stackoverflow.project.Stackoverflow.mapper.UserMapper;
import com.stackoverflow.project.Stackoverflow.repository.*;
import com.stackoverflow.project.Stackoverflow.service.AnswerService;
import com.stackoverflow.project.Stackoverflow.service.QuestionService;
import com.stackoverflow.project.Stackoverflow.service.TagService;
import com.stackoverflow.project.Stackoverflow.service.impl.AnswerServiceImpl;
import com.stackoverflow.project.Stackoverflow.service.impl.QuestionServiceImpl;

import com.stackoverflow.project.Stackoverflow.service.impl.TagServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.stackoverflow.project.Stackoverflow.service.UserService;
import com.stackoverflow.project.Stackoverflow.service.impl.UserServiceImpl;

@Configuration
public class AppConfig {

    @Bean
    public UserService userService(UserRepository userRepository, UserMapper userMapper) {
        return new UserServiceImpl(userRepository, userMapper);
    }

    @Bean
    public QuestionService questionService(QuestionRepository questionRepository, UserRepository userRepository,
                                           TagRepository tagRepository, QuestionTagRepository questionTagRepository, QuestionMapper questionMapper, AnswerMapper answerMapper) {
        return new QuestionServiceImpl(questionRepository, userRepository, tagRepository, questionTagRepository, questionMapper, answerMapper);
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
}

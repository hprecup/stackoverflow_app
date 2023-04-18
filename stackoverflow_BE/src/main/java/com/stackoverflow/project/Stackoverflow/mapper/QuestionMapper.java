package com.stackoverflow.project.Stackoverflow.mapper;

import com.stackoverflow.project.Stackoverflow.dto.InsertQuestionDTO;
import com.stackoverflow.project.Stackoverflow.dto.QuestionDTO;
import com.stackoverflow.project.Stackoverflow.dto.RequestQuestionDTO;
import com.stackoverflow.project.Stackoverflow.model.Question;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    void questionToQuestionDTO(@MappingTarget QuestionDTO questionDTO, Question question);

    void questionDTOtoQuestion(@MappingTarget Question question, QuestionDTO questionDTO);

    void questionToRequestQuestionDTO(@MappingTarget RequestQuestionDTO requestQuestionDTO, Question question);

    void insertQuestionDTOtoQuestion(@MappingTarget Question question, InsertQuestionDTO insertQuestionDTO);
}

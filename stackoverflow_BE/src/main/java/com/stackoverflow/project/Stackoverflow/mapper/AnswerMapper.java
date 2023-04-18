package com.stackoverflow.project.Stackoverflow.mapper;

import com.stackoverflow.project.Stackoverflow.dto.AnswerDTO;
import com.stackoverflow.project.Stackoverflow.dto.InsertAnswerDTO;
import com.stackoverflow.project.Stackoverflow.dto.RequestAnswerDTO;
import com.stackoverflow.project.Stackoverflow.model.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    void answerToAnswerDTO(@MappingTarget AnswerDTO answerDTO, Answer answer);

    void answerDTOtoAnswer(@MappingTarget Answer answer, AnswerDTO answerDTO);

    void answerToRequestAnswerDTO(@MappingTarget RequestAnswerDTO requestAnswerDTO, Answer answer);

    void insertAnswerDTOtoAnswer(@MappingTarget Answer answer, InsertAnswerDTO insertAnswerDTO);
}

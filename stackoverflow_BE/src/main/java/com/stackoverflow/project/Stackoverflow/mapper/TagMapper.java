package com.stackoverflow.project.Stackoverflow.mapper;

import com.stackoverflow.project.Stackoverflow.dto.RequestTagDTO;
import com.stackoverflow.project.Stackoverflow.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TagMapper {
    void tagToRequestTagDTO(@MappingTarget RequestTagDTO requestTagDTO, Tag tag);
}

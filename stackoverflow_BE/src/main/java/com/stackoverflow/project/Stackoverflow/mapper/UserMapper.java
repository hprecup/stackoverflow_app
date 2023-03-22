package com.stackoverflow.project.Stackoverflow.mapper;

import com.stackoverflow.project.Stackoverflow.dto.UserDTO;
import com.stackoverflow.project.Stackoverflow.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    void userToUserDTO(@MappingTarget UserDTO userDTO, User user);

    @Mapping(target = "id", ignore = true)
    void userDTOtoUser(@MappingTarget User user,UserDTO userDTO);
}

package com.stackoverflow.project.Stackoverflow.service;

import com.stackoverflow.project.Stackoverflow.dto.RequestUserDTO;
import com.stackoverflow.project.Stackoverflow.dto.UserDTO;
import com.stackoverflow.project.Stackoverflow.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<User> getUser(Long id);

    List<User> getAllUsers();

    ResponseEntity<User> createUser(UserDTO newUser);

    ResponseEntity<User> updateUser(Long id, UserDTO UpdatedUser);

    ResponseEntity<User> deleteUser(Long id);

    List<RequestUserDTO> getRequestUsers();

    void banUser(Long id, boolean ban);

    RequestUserDTO getLoggedUser();
}

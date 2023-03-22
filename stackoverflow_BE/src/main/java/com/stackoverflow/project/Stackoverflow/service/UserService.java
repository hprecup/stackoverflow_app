package com.stackoverflow.project.Stackoverflow.service;

import com.stackoverflow.project.Stackoverflow.dto.UserDTO;
import com.stackoverflow.project.Stackoverflow.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    ResponseEntity<User> createUser(UserDTO newUser);

    ResponseEntity<User> updateUser(Long id, UserDTO UpdatedUser);

    ResponseEntity<String> deleteUser(Long id);
}

package com.stackoverflow.project.Stackoverflow.service.impl;

import com.stackoverflow.project.Stackoverflow.dto.UserDTO;
import com.stackoverflow.project.Stackoverflow.mapper.UserMapper;
import lombok.AllArgsConstructor;
import com.stackoverflow.project.Stackoverflow.model.User;
import com.stackoverflow.project.Stackoverflow.repository.UserRepository;
import com.stackoverflow.project.Stackoverflow.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public ResponseEntity<User> getUser(Long id) {
        Optional<User> retrievedUser = userRepository.findById(id);
        if(!retrievedUser.isEmpty()){
            return new ResponseEntity(retrievedUser.get(), HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<User> createUser(UserDTO userDTO){
        User newUser = new User();
        userMapper.userDTOtoUser(newUser, userDTO);
        newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt()));
        userRepository.save(newUser);

        return new ResponseEntity(newUser, HttpStatus.CREATED);
    }

    public ResponseEntity<User> updateUser(Long id, UserDTO updatedUser) {
        Optional<User> userToUpdate = userRepository.findById(id);
        if(!userToUpdate.isEmpty()){
            User retrievedUser = userToUpdate.get();
            userMapper.userDTOtoUser(retrievedUser, updatedUser);
            retrievedUser.setPassword(BCrypt.hashpw(retrievedUser.getPassword(), BCrypt.gensalt()));
            userRepository.save(retrievedUser);
            return new ResponseEntity(retrievedUser, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<User> deleteUser(Long id){
        Optional<User> retrievedUser = userRepository.findById(id);
        if(!retrievedUser.isEmpty()){
            userRepository.deleteById(id);
            return new ResponseEntity(retrievedUser.get(), HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
}

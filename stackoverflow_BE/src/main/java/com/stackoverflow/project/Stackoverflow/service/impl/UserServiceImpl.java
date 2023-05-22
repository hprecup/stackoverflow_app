package com.stackoverflow.project.Stackoverflow.service.impl;

import com.stackoverflow.project.Stackoverflow.dto.RequestUserDTO;
import com.stackoverflow.project.Stackoverflow.dto.UserDTO;
import com.stackoverflow.project.Stackoverflow.mapper.UserMapper;
import com.stackoverflow.project.Stackoverflow.model.MailDetail;
import com.stackoverflow.project.Stackoverflow.model.UserRole;
import com.stackoverflow.project.Stackoverflow.security.SecurityUtils;
import com.stackoverflow.project.Stackoverflow.service.MailService;
import lombok.AllArgsConstructor;
import com.stackoverflow.project.Stackoverflow.model.User;
import com.stackoverflow.project.Stackoverflow.repository.UserRepository;
import com.stackoverflow.project.Stackoverflow.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    private MailService mailService;

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

    public List<RequestUserDTO> getRequestUsers() {
        List<User> users = userRepository.findAll();
        List<RequestUserDTO> requestUsers = new ArrayList<>();
        for(User user : users) {
            RequestUserDTO requestUserDTO = new RequestUserDTO();
            userMapper.userToRequestUserDTO(requestUserDTO, user);
            List<String> requestUserRoleNames = new ArrayList<>();
            for(UserRole userRole : user.getRoles()){
                requestUserRoleNames.add(userRole.getRole().getName().name());
            }
            requestUserDTO.setRoleNames(requestUserRoleNames);
            requestUsers.add(requestUserDTO);
        }
        return requestUsers;
    }

    public void banUser(Long id, boolean ban) {
        userRepository.findById(id).ifPresentOrElse(
                user -> {
                    user.setBanned(ban);
                    userRepository.save(user);
                    if(ban == true){
                        mailService.sendSimpleMail(generateMailDetail(user));
                    }
                },
                () -> {
                    throw new RuntimeException("User not found");
                }
        );
    }

    public RequestUserDTO getLoggedUser() {
        RequestUserDTO requestUser = new RequestUserDTO();
        userMapper.userToRequestUserDTO(requestUser, SecurityUtils.getLoggedUser());
        return requestUser;
    }

    private MailDetail generateMailDetail(User user) {
        return new MailDetail("horeaprecup@yahoo.ro","Info about banned acount",
                "Hello "+user.getUsername()+"!\nWe are very sorry to inform you that your account has " +
                        "been banned by one of our moderators.\nIf you would like to know more details, leave us a message at " +
                        "stackoverflow@customer-support.com.");
    }

}

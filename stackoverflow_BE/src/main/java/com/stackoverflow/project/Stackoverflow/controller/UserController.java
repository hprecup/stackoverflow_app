package com.stackoverflow.project.Stackoverflow.controller;

import com.stackoverflow.project.Stackoverflow.dto.RequestUserDTO;
import com.stackoverflow.project.Stackoverflow.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.stackoverflow.project.Stackoverflow.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stackoverflow.project.Stackoverflow.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @GetMapping()
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO newUser){
        return userService.createUser(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO updatedUser){
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }


    @GetMapping("/getAll")
    public List<RequestUserDTO> getRequestUsers() {
        return userService.getRequestUsers();
    }

    @PutMapping("/ban/{id}")
    public void banUser(@PathVariable Long id){
        userService.banUser(id, true);
    }

    @PutMapping("/unban/{id}")
    public void unbanUser(@PathVariable Long id){
        userService.banUser(id, false);
    }

    @GetMapping("/getLoggedUser")
    public RequestUserDTO getLoggedUser(){
        return userService.getLoggedUser();
    }

}

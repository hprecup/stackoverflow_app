package com.stackoverflow.project.Stackoverflow.controller;

import com.stackoverflow.project.Stackoverflow.dto.AuthLoginDTO;
import com.stackoverflow.project.Stackoverflow.dto.AuthResponse;
import com.stackoverflow.project.Stackoverflow.model.User;
import com.stackoverflow.project.Stackoverflow.model.UserRole;
import com.stackoverflow.project.Stackoverflow.security.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    AuthenticationManager authManager;

    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthLoginDTO request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            List<String> userRoles = new ArrayList<String>();
            for(UserRole userRole : user.getRoles()){
                userRoles.add(userRole.getRole().getName().name());
            }
            String accessToken = jwtTokenUtil.generateToken(user);
            AuthResponse response = new AuthResponse(user.getUsername(), userRoles, user.getBanned(), accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }

}

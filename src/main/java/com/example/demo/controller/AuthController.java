package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.errormsg.BadRequestException;
import com.example.demo.errormsg.ResourceNotFoundException;
import com.example.demo.errormsg.TokenRefreshException;
import com.example.demo.errormsg.UsernameAlreadyExistsException;
import com.example.demo.payload.*;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody @NotNull LoginRequest loginRequest) throws Exception {
        String token = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping(value = "/signin/refresh")
    public ResponseEntity<?> logout(@Valid @RequestBody LogoutRequest logoutRequest){
        boolean logout = userService.logout(logoutRequest.getUsername());
        return ResponseEntity.ok(new LogoutResponse(logout));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody @NotNull SignUpRequest payload) {
        log.info("creating user {}", payload.getUsername());

        Users users = Users.builder().username(payload.getUsername())
                .password(payload.getPassword())
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .email(payload.getEmail())
                .number(payload.getNumber())
                .address(payload.getAddress())
                .city(payload.getCity())
                .roles(payload.getRole())
                .build();

        try {
            userService.saveUser(users);
        } catch (UsernameAlreadyExistsException e) {
            throw new BadRequestException(e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(users.getUsername()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true,"User registered successfully"));
    }


}

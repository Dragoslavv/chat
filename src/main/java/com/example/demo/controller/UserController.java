package com.example.demo.controller;

import com.example.demo.entity.InstaUserDetails;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.errormsg.BadRequestException;
import com.example.demo.errormsg.ResourceNotFoundException;
import com.example.demo.errormsg.TokenRefreshException;
import com.example.demo.errormsg.UsernameAlreadyExistsException;
import com.example.demo.payload.UserSummary;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUser(@PathVariable("username") String username) {
        log.info("retrieving user {}", username);

        return userService.findByUsername(username)
                .map(users -> ResponseEntity.ok(users))
                .orElseThrow(() -> new ResourceNotFoundException(username));
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        log.info("retrieving all users");

        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping(value = "/users/summaries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllUserSummaries(@AuthenticationPrincipal InstaUserDetails userDetails){
        log.info("retrieving all users summaries");

        return ResponseEntity.ok(userService
                .findAll()
                .stream()
                .filter(user -> !user.getUsername().equals(userDetails.getUsername()))
                .map(this::convertTo));
    }

    @GetMapping(value = "/users/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public UserSummary getCurrentUser(@AuthenticationPrincipal InstaUserDetails userDetails) {

        return UserSummary.builder()
                .id(userDetails.getId())
                .name(userDetails.getFirstName() + " " + userDetails.getLastName())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .number(userDetails.getNumber())
                .address(userDetails.getAddress())
                .city(userDetails.getCity())

//                .profilePicture(userDetails.getUploadFile().getFileDownloadUri())
                .build();
    }

    @GetMapping(value = "/users/summary", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserSummary(String username) {
        log.info("search user with username {}",username);

        return userService.findByUsername(username)
                .map(users -> ResponseEntity.ok(convertTo(users)))
                .orElseThrow(() -> new ResourceNotFoundException(username));
    }

    private UserSummary convertTo(Users users) {
        return UserSummary
                .builder()
                .id(users.getId())
                .username(users.getUsername())
                .name(users.getUsername())
                .email(users.getEmail())
                .number(users.getNumber())
                .address(users.getAddress())
                .city(users.getCity())
//                .profilePicture(users.getUploadFile().getFileDownloadUri())
                .build();
    }

}
package com.example.demo.controller;

import com.example.demo.entity.InstaUserDetails;
import com.example.demo.entity.Users;
import com.example.demo.enums.Status;
import com.example.demo.payload.UserSummary;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(produces = "application/json")
    public List<Users> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(produces = "application/json", path = "/user/{id}")
    public Optional<Users> getByUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserSummary getCurrentUser(@AuthenticationPrincipal InstaUserDetails userDetails) {

        return UserSummary.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .build();
    }


}
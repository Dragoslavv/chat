package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.enums.Status;
import com.example.demo.errormsg.EntityNotFoundException;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class AuthController {

    @Autowired
    private AuthService authService;

    @CrossOrigin
    @PostMapping(produces = "application/json", path = "/login")
    public Status authenticate(@Valid @RequestBody Users users) throws EntityNotFoundException {
        return authService.authenticate(users);
    }

    @CrossOrigin
    @PostMapping(produces = "application/json", path = "/logout")
    public Status logUserOut (@Valid @RequestBody Users users) throws EntityNotFoundException{
        return authService.logout(users);
    }
}

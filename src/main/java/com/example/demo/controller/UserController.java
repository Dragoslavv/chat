package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.enums.Status;
import com.example.demo.errormsg.EntityNotFoundException;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping(consumes = "application/json", produces = "application/json", path = "/register")
    public Object saveUser(@Valid @RequestBody Users users) throws EntityNotFoundException  {
        return userService.saveUser(users);
    }

    @CrossOrigin
    @PostMapping(consumes = "application/json", produces = "application/json", path = "/update")
    public Status updateUser(@Valid @RequestBody Users users) throws EntityNotFoundException {
        return userService.update(users);
    }

    @CrossOrigin
    @GetMapping(produces = "application/json")
    public List<Users> getUsers() throws EntityNotFoundException {
        return userService.getAllUsers();
    }

    @CrossOrigin
    @GetMapping(produces = "application/json", path = "/user/{id}")
    public Optional<Users> getByUser(@PathVariable Long id) throws EntityNotFoundException{
        return userService.getUserById(id);
    }

    @CrossOrigin
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) throws EntityNotFoundException{
        userService.deleteUser(id);
    }

}
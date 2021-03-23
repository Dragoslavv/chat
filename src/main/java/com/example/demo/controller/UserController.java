package com.example.demo.controller;

import com.example.demo.dto.JwtRequest;
import com.example.demo.dto.JwtResponse;
import com.example.demo.entity.Users;
import com.example.demo.enums.Status;
import com.example.demo.errormsg.EntityNotFoundException;
import com.example.demo.security.jwt.JwtTokenUtil;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @CrossOrigin
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest users) throws Exception {

        authenticate(users.getUsername(), users.getPassword());

        final UserDetails userDetails = userService.loadUserByUsername(users.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

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
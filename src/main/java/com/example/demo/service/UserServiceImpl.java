package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.enums.Status;
import com.example.demo.errormsg.UsernameAlreadyExistsException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository rolesRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public String loginUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        List<Users> users = (List<Users>) usersRepository.findAll();

        for (Users other : users) {

            if( other.getUsername().equals(username)) {
                other.setActive(true);
                usersRepository.save(other);
                log.info("Message : User is SUCCESS logged ");
            }
        }

        return tokenProvider.generateToken(authentication);
    }

    @Override
    public Users saveUser(Users users) {
        log.info("registering user {}", users.getUsername());

        if (usersRepository.existsByUsername(users.getUsername())) {
            log.warn("registering user {}", users.getUsername());

            throw new UsernameAlreadyExistsException(
                    String.format("username %s already exists", users.getUsername()));
        }

        users.setActive(true);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Set<Role> roles = new HashSet<>();

        users.getRoles().forEach(result -> {
            Role getRole = rolesRepository.findRoleByName(result.getRole());

            if(getRole != null) {
                roles.add(getRole);
            }
        });

        if(!roles.isEmpty()) {
            users.setRoles(roles);
        }

        return usersRepository.save(users);
    }

    @Override
    public Status deleteUser(Long userId) {
        if(usersRepository.existsById(userId)) {
            usersRepository.deleteById(userId);
            log.info("User is " + Status.SUCCESS + " deleted (" + userId + ")");
            return Status.SUCCESS;
        }

        log.warn("ErrorMessage: " + Status.FAILURE + "(" + userId + "), NOT DELETE");
        return Status.FAILURE;
    }

    @Override
    public List<Users> findAll(){
        return (List<Users>) usersRepository.findAll();
    }

    @Override
    public Optional<Users> getUserById(Long userId) {
        return usersRepository.findById(userId);
    }

    public Optional<Users> findByUsername(String username) {
        log.info("retrieving user {}", username);
        return usersRepository.findByUsername(username);
    }
}

package com.example.demo.service;


import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.enums.Status;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String loginUser(String username, String password);
    Users saveUser(Users users);
    Status deleteUser(Long userId);
    List<Users> getAllUsers();
    Optional<Users> getUserById(Long userId);
    Optional<Users> findByUsername(String username);
}

package com.example.demo.service;


import com.example.demo.entity.Users;
import com.example.demo.enums.Status;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {
    @Transactional
    UserDetails loadUserByUsername(String login);

    Status saveUser(Users newUsers);
    Status deleteUser(Long userId);
    Status update(Users existUser);
    List<Users> getAllUsers();
    Optional<Users> getUserById(Long userId);
}

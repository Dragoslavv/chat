package com.example.demo.service;


import com.example.demo.entity.Users;
import com.example.demo.enums.Status;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Status saveUser(Users newUsers);
    Status deleteUser(Long userId);
    Status update(Users existUser);
    List<Users> getAllUsers();
    Optional<Users> getUserById(Long userId);
}

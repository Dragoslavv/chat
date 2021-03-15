package com.example.demo.service;

import com.example.demo.entity.Users;
import com.example.demo.enums.Status;

public interface AuthService {
    Status authenticate(Users users);
    Status logout(Users users);
}

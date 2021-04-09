package com.example.demo.payload;

import com.example.demo.entity.Role;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignUpRequest {

    @NotEmpty(message = "Username may not be empty")
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters long")
    private String username;

    @NotEmpty(message = "Password may not be empty")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters long")
    private String password;

    @NotEmpty(message = "Please enter role")
    private Set<Role> role;
}
